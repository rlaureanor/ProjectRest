package rlaureano.com.store.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rlaureano.com.store.customer.entity.Customer;
import rlaureano.com.store.customer.entity.Region;
import rlaureano.com.store.customer.repository.CustomerRepository;

import java.util.List;
@Service
public class CustomerServiceImpl implements  CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public List<Customer> findCustomerAll() {

        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomerByRegion(Region region) {

        return customerRepository.findByRegion(region);
    }

    @Override
    public Customer createCustomer(Customer customer) {

        Customer customerDB = customerRepository.findByNumberID(customer.getNumberID());
        if (customerDB != null){
            return customerDB;
        }
        customer.setState("CREATED");
        customerDB = customerRepository.save(customer);
        return customerDB;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer customerDB= getCustomer(customer.getId());
        if(customerDB == null){
            return  null;
        }
        customerDB.setFirstName(customer.getFirstName());
        customerDB.setLastName(customer.getLastName());
        customerDB.setEmail(customer.getEmail());
        customerDB.setPhotoUrl(customer.getPhotoUrl());
        return customerRepository.save(customerDB);
    }

    @Override
    public Customer deleteCustomer(Customer customer) {
        Customer customerDB= getCustomer(customer.getId());
        if (customerDB== null){
            return null;
        }
        customer.setState("DELETED");

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
