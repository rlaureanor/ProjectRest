package rlaureano.com.store.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import rlaureano.com.store.product.entity.Category;
import rlaureano.com.store.product.entity.Product;
import rlaureano.com.store.product.service.ProductService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProducts(@RequestParam(name = "categoryId",required = false) Long categoryId){
        List<Product> products = new ArrayList<>();
        if (null == categoryId){
            products = productService.listAllProduct();
            if (products.isEmpty()){
                return ResponseEntity.noContent().build();
            }

        }else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if (products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = productService.getProduct(id);
        if (null == product){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product productCreated= productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        product.setId(id);
        Product productDB= productService.updateProduct(product);
        if (null == productDB){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product productDelete = productService.deleteProduct(id);
        if (null == productDelete){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);

    }

    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStockProduct(@PathVariable("id") Long id,@RequestParam(name = "quantity",required = true) Double quantity){
        Product product = productService.updateStock(id, quantity);
        if (null == product){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    private String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err -> {
                    Map<String, String> error= new HashMap<>();
                    error.put(err.getField(),err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage=ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper objectMapper= new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = objectMapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }
}
