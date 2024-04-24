package com.TMDT.api.Api.springboot.database;

import com.TMDT.api.Api.springboot.models.Product;
import com.TMDT.api.Api.springboot.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Database {

    // Đoạn này sẽ tự động insert vài bản ghi vào database, nếu chưa có bản thì sẽ tự tạo bản, đoạn này dùng trong lúc test
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                ArrayList<String> urls1 = new ArrayList<String>();
                urls1.add("https://www.tmdt.com");
                urls1.add("https://www.tmdt.com");
                Product product1 = new Product("Iphone 15 promax", "Day la iphone 15 promax", "urlMain", urls1, 20000, 0, 30, 2, 0);
                Product product2 = new Product("Iphone 14 promax", "Day la iphone 14 promax", "urlMain", urls1, 20000, 0, 30, 2, 0);
                productRepository.save(product1); // luu doi tuong vao database
                productRepository.save(product2);
            }
        };
    }
}
