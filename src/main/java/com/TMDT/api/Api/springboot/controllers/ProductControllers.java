package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.models.Product;
import com.TMDT.api.Api.springboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductControllers {

    //Tạo ra biến productRepository, giống như singleton
    @Autowired
    ProductRepository productRepository;


    // get /api/v1/products/getAll
    @GetMapping("/getAll")
    List<Product> getProducts() {
        return productRepository.findAll(); // hàm này được cung cấp sẵn.
    }


    // /api/v1/products/1
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getProductById(@PathVariable int id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        return foundProduct.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Success", foundProduct)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find product by id = " + id, "")
                );
    }


}
