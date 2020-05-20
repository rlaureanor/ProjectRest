package rlaureano.com.store.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rlaureano.com.store.product.entity.Category;
import rlaureano.com.store.product.entity.Product;
import rlaureano.com.store.product.repository.ProductRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreateAt(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productBd=getProduct(product.getId());
        if (null == productBd){
            return  null;
        }
        productBd.setName(product.getName());
        productBd.setDescription(product.getDescription());
        productBd.setCategory(product.getCategory());
        productBd.setPrice(product.getPrice());
        return productRepository.save(productBd);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productBd=getProduct(id);
        if (null == productBd){
            return  null;
        }
        productBd.setStatus("DELETED");
        return productRepository.save(productBd);

    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productBd=getProduct(id);
        if (null == productBd){
            return  null;
        }
        Double stock= productBd.getStock() + quantity;
        productBd.setStock(stock);
        return productRepository.save(productBd);
    }
}
