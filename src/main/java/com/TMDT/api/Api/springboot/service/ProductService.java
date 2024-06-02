package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.models.Category;
import com.TMDT.api.Api.springboot.models.PhoneCategory;
import com.TMDT.api.Api.springboot.models.Product;
import com.TMDT.api.Api.springboot.repositories.CategoryRepository;
import com.TMDT.api.Api.springboot.repositories.ImageRepository;
import com.TMDT.api.Api.springboot.repositories.PhoneCategoryRepository;
import com.TMDT.api.Api.springboot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Product getById(int id) {
        Product product = productRepository.findFirstByIdAndStatusNot(id, 0);

        if (product != null) {
            clearProperties(product);
            return product;
        }
        return null;
    }

    public List<Product> getAll() {
        List<Product> products = productRepository.findAllByStatusNot(0);
        for (Product p : products) {
            clearProperties(p);
        }
        return products;
    }

    public List<Product> getByCategory(String category) {
        Category foundCategory = categoryRepository.findByName(category);
        List<Product> products = foundCategory.getProducts();
        products.forEach(this::clearProperties);
        return products;
    }

    public Product insert(Product newProduct) {
        if (categoryRepository.findByName(newProduct.getCategory().getName()) != null) {
            Category category = categoryRepository.findByName(newProduct.getCategory().getName());
            newProduct.setCategory(category);
        } else {
            Category newCategory = new Category();
            newCategory.setName(newProduct.getCategory().getName());
            categoryRepository.save(newCategory);
            newProduct.setCategory(newCategory);
        }
        List<PhoneCategory> phoneCategories = newProduct.getPhoneCategories();
        for (int i = 0; i < phoneCategories.size(); i++) {
            PhoneCategory foundPhoneCategory = phoneCategoryRepository.findByName(phoneCategories.get(i).getName());
            if (foundPhoneCategory != null) {
                newProduct.getPhoneCategories().set(i, foundPhoneCategory);
            } else {
                PhoneCategory newPhoneCategory = new PhoneCategory();
                newPhoneCategory.setName(phoneCategories.get(i).getName());
                newPhoneCategory.setStatus(phoneCategories.get(i).getStatus());
                phoneCategoryRepository.save(newPhoneCategory);
                newProduct.getPhoneCategories().set(i, newPhoneCategory);
            }
        }

        newProduct.setCreateAt(LocalDateTime.now());
        Product productSaved = productRepository.save(newProduct);

        newProduct.getImages().forEach(image -> {
            image.setProduct(productSaved);
        });
        imageRepository.saveAll(newProduct.getImages());

        Product result = productRepository.findById(productSaved.getId()).orElse(null);
        clearProperties(result);
        return result;
    }

    @Transactional
    public Product update(int id, Product newProduct) {
        imageRepository.deleteByProductId(id);
        newProduct.getImages().forEach(image -> {
            image.setProduct(newProduct);
        });
        imageRepository.saveAll(newProduct.getImages());

        for (int i = 0; i < newProduct.getPhoneCategories().size(); i++) {
            PhoneCategory foundPhoneCategory = phoneCategoryRepository.findByName(newProduct.getPhoneCategories().get(i).getName());
            newProduct.getPhoneCategories().set(i, foundPhoneCategory);
        }

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
                    product.setPhoneCategories(newProduct.getPhoneCategories());
                    return productRepository.save(product);
                }).get();
        clearProperties(productUpdate);
        return productUpdate;
    }

    public List<Product> search(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        for (Product p : products) {
            clearProperties(p);
        }
        return products;
    }


    public Product delete(int id) {
        Product product = productRepository.findById(id).map(p -> {
            p.setStatus(0);
            return productRepository.save(p);
        }).orElse(null);
        assert product != null;
        clearProperties(product);
        return product;
    }

    private void clearProperties(Product product) {
        product.getPhoneCategories().forEach(phoneCategory -> {
            phoneCategory.setProducts(null);
        });
        product.getImages().forEach(image -> {
            image.setProduct(null);
        });
        product.getCategory().setProducts(null);
    }


}
