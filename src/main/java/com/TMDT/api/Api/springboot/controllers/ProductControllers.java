package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.models.Category;
import com.TMDT.api.Api.springboot.models.Image;
import com.TMDT.api.Api.springboot.models.PhoneCategory;
import com.TMDT.api.Api.springboot.models.Product;
import com.TMDT.api.Api.springboot.repositories.CategoryRepository;
import com.TMDT.api.Api.springboot.repositories.ImageRepository;
import com.TMDT.api.Api.springboot.repositories.PhoneCategoryRepository;
import com.TMDT.api.Api.springboot.repositories.ProductRepository;
import com.TMDT.api.Api.springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductControllers {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    @GetMapping("/getAll")
    ResponseEntity<ResponseObject> getProducts() {
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(new ResponseObject("ok", "Success", products));
    }

    @GetMapping("/getByCategory")
    ResponseEntity<ResponseObject> getByCategory(@RequestParam String category) {
        List<Product> products = productService.getByCategory(category);
        return ResponseEntity.ok(new ResponseObject("ok", "Success", products));
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getProductById(@PathVariable int id) {
        Product foundProduct = productService.getById(id);
        return foundProduct != null ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Success", foundProduct)
                ) :
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("failed", "Cannot find product by id = " + id, "")
                );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
//        System.out.println(newProduct.getCategory());
        Product productSaved = productService.insert(newProduct);
        return ResponseEntity.ok(
                new ResponseObject("ok", "success", productSaved)
        );
    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateProduct(@PathVariable int id, @RequestBody Product newProduct) {
        Product productUpdate = productService.update(id, newProduct);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "success", productUpdate)
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteProduct(@PathVariable int id) {
        Product product = productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "success", product)
        );
    }

    @GetMapping("/search")
    ResponseEntity<ResponseObject> searchProduct(@RequestParam String name) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", productService.search(name)));
    }

}
