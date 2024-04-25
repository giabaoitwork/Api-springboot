package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.models.Product;
import com.TMDT.api.Api.springboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "success", productRepository.save(newProduct))
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@PathVariable int id, @RequestBody Product newProduct) {
        Product productUpdate = productRepository.findById(id)
                .map(product -> { // neu tim thay product thi chinh sua thong tin
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setImage(newProduct.getImage());
                    product.setImages(newProduct.getImages());
                    product.setPrice(newProduct.getPrice());
                    product.setDiscount(newProduct.getDiscount());
                    product.setSold(newProduct.getSold());
                    product.setQuantity(newProduct.getQuantity());
                    product.setStatus(newProduct.getStatus());
                    return productRepository.save(product);
                }).orElseGet(() -> {  // neu khong tim thay thi them product moi
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "success", productUpdate)
        );
    }

}
