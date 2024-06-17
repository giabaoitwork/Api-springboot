package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.models.Category;
import com.TMDT.api.Api.springboot.models.Product;
import com.TMDT.api.Api.springboot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PhoneCategoryRepository phoneCategoryRepository;

    @Autowired
    ProductPhoneCategoryRepository productPhoneCategoryRepository;

    public Product getById(int id) {
        Product product = productRepository.findFirstByIdAndStatusNot(id, 0);

        if (product != null) {
            clearProperty(product);
            return product;
        }
        return null;
    }

    public List<Product> getAll() {
        List<Product> products = productRepository.findAllByStatusNot(0);
        for (Product p : products) {
            clearProperty(p);
        }
        return products;
    }

    public List<Product> getByCategory(String category) {
        Category foundCategory = categoryRepository.findByName(category);
        if(foundCategory == null){
            return new ArrayList<>();
        }
        List<Product> products = foundCategory.getProducts();
        products.forEach(this::clearProperty);
        return products;
    }

    public Product insert(Product newProduct) {
        newProduct.setCreateAt(LocalDateTime.now());
        Product productSaved = productRepository.save(newProduct);

        newProduct.getImages().forEach(image -> {
            image.setProduct(productSaved);
        });
        imageRepository.saveAll(newProduct.getImages());

        newProduct.getProductPhoneCategories().forEach(productPhoneCategory -> {
            productPhoneCategory.setProduct(productSaved);
        });
        productPhoneCategoryRepository.saveAll(newProduct.getProductPhoneCategories());

        Product result = productRepository.findById(productSaved.getId()).orElse(null);
        clearProperty(result);
        return result;
    }

    @Transactional
    public Product update(int id, Product newProduct) {
        imageRepository.deleteByProductId(id);
        newProduct.getImages().forEach(image -> {
            image.setProduct(newProduct);
        });
        imageRepository.saveAll(newProduct.getImages());

//        for (int i = 0; i < newProduct.getPhoneCategories().size(); i++) {
//            PhoneCategory foundPhoneCategory = phoneCategoryRepository.findByName(newProduct.getPhoneCategories().get(i).getName());
//            newProduct.getPhoneCategories().set(i, foundPhoneCategory);
//        }

        newProduct.setCategory(categoryRepository.findByName(newProduct.getCategory().getName()));
        Product productUpdate = productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setDescription(newProduct.getDescription());
                    product.setPrice(newProduct.getPrice());
                    product.setDiscount(newProduct.getDiscount());
                    product.setSold(newProduct.getSold());
                    product.setQuantity(newProduct.getQuantity());
                    product.setStatus(newProduct.getStatus());
                    product.setCategory(newProduct.getCategory());
//                    product.setPhoneCategories(newProduct.getPhoneCategories());
                    return productRepository.save(product);
                }).get();
        clearProperty(productUpdate);
        return productUpdate;
    }

    public List<Product> search(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        for (Product p : products) {
            clearProperty(p);
        }
        return products;
    }


    public Product delete(int id) {
        Product product = productRepository.findById(id).map(p -> {
            p.setStatus(0);
            return productRepository.save(p);
        }).orElse(null);
        assert product != null;
        clearProperty(product);
        return product;
    }

    public Product clearProperty(Product product) {
        product.getProductPhoneCategories().forEach(productPhoneCategory -> {
            productPhoneCategory.setProduct(null);
            if (productPhoneCategory.getPhoneCategory() != null)
                productPhoneCategory.getPhoneCategory().setProductPhoneCategories(null);
        });
        product.getImages().forEach(image -> {
            image.setProduct(null);
        });
        if (product.getCategory() != null)
            product.getCategory().setProducts(null);
        return product;
    }


}
