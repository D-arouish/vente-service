package org.sid.billingservice;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.entities.ProductItem;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repositories.BillRepository;
import org.sid.billingservice.repositories.ProductItemsRepository;
import org.sid.billingservice.service.CustomerRestClient;
import org.sid.billingservice.service.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients //to enable injection and recognize customer and product rest controller
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemsRepository productItemsRepository,
                            CustomerRestClient customerRestClient,
                            ProductRestClient productRestClient) {
        return  args -> {

            Collection<Product> products = productRestClient.allProducts().getContent();
            Long customerId=1L;
            Customer customer = customerRestClient.findCustomerById(customerId);
            if(customer == null) throw  new RuntimeException("Customer not found");
            Bill bill = new Bill();
            bill.setBillDate(new Date());
            bill.setCustomerId(customerId);
            Bill savedBill = billRepository.save(bill);
            products.forEach(
                    product -> {
                        ProductItem productItem = new ProductItem();
                        productItem.setBill(savedBill);
                        productItem.setQuantity(1+new Random().nextInt(10));
                        productItem.setPrice(product.getPrice());
                        productItem.setDiscount(Math.random());
                        productItem.setProductId(product.getId());
                        productItemsRepository.save(productItem);
                    }
            );
        };
    }
}


























