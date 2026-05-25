package com.ecom_project.shopify.service;

import com.ecom_project.shopify.dto.CustomerDTO;
import com.ecom_project.shopify.dto.Mapper;
import com.ecom_project.shopify.model.*;
import com.ecom_project.shopify.repository.CartRepo;
import com.ecom_project.shopify.repository.CustomerRepo;
import com.ecom_project.shopify.repository.OrderRepo;
import com.ecom_project.shopify.repository.PaymentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService  {
    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CartRepo cartRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    PaymentRepo paymentRepo;

    @Autowired
    CartService cartService;

    @Autowired
    Mapper mapper;

    @Transactional
    public Orders makeOrder(Cart cart){

        int amount = 0;
        for(Product product : cart.getProductList()){
            amount = amount + product.getPrice();
        }
        UUID custId = cart.getCustomerId();
        String modeOfPayment = cart.getModeOfPayment();
        Customer customer = (Customer) customerRepo.findById(custId).orElse(null);
        Orders orders = Orders.builder().custId(custId).amount(amount).orderDate(LocalDate.now()).customer(customer).build();
        orderRepo.save(orders);

        Payment payment = Payment.builder().type(modeOfPayment).customer(customer).amount(amount).build();
        paymentRepo.save(payment);
        return orders;
    }


    @Cacheable(cacheNames = "customers", key= "#result.name")
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> list = customerRepo.findAll();
        List<CustomerDTO> dtoList = new ArrayList<>();
        for(Customer customer : list){
            CustomerDTO dto = mapper.customerDTO(customer);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Cacheable(cacheNames = "customer" , key = "#id")
    public CustomerDTO getCustomerById(UUID id) {

        Customer customer =  customerRepo.findById(id).orElse(null);
        CustomerDTO dto = mapper.customerDTO(customer);
        return dto;
    }

    public void  addCustomer(Customer customer) {

        customer.setOrdersList(new ArrayList<>());
        customer.setPayments(new ArrayList<>());

        customerRepo.save(customer);

        Customer customer1 = (Customer) customerRepo.findByEmail(customer.getEmail()).orElse(null);
        Cart cart = Cart.builder().customerId(customer1.getId()).modeOfPayment("COD").productList(new ArrayList<>()).build();
        cartService.createCartForCustomer(cart);

    }

    @CacheEvict(cacheNames = "products", key = "#id")
    public void deleteCustomer(UUID id) {

        customerRepo.deleteById(id);
    }

}
