package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.dto.ListProductDTO;
import com.TMDT.api.Api.springboot.dto.ProductDTO;
import com.TMDT.api.Api.springboot.dto.ProductInsertDTO;
import com.TMDT.api.Api.springboot.mapper.ProductMapper;
import com.TMDT.api.Api.springboot.models.*;
import com.TMDT.api.Api.springboot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductPhoneCategoryRepository productPhoneCategoryRepository;

    @Autowired
    PhoneCategoryRepository phoneCategoryRepository;

    public Product getById(int id) {
        return productRepository.findFirstByIdAndStatusNot(id, 0);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public ListProductDTO getByFilter(String category, int page, int limit, String order, String orderBy) {
        Sort sort = Sort.by(orderBy);
        sort = order.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();
        Pageable pageable = PageRequest.of(page, limit, sort);

        // Gọi repository để lấy dữ liệu
        Page<Product> productPage = productRepository.findByCategoryAndStatusNot(category, pageable);
//        return clearProperties(productPage.getContent());
        return new ListProductDTO(productPage.getTotalPages(), clearProperties(productPage.getContent()));
    }

    public List<Product> getByCategory(String category) {
        Category foundCategory = categoryRepository.findByName(category);
        if (foundCategory == null) {
            return new ArrayList<>();
        }
        List<Product> products = foundCategory.getProducts();
        products.forEach(this::clearProperty);
        return products;
    }


    public Product insert(ProductInsertDTO productDTO) {
        Category category = categoryService.getById(productDTO.getCategoryId());

        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setDiscount(productDTO.getDiscount());
        newProduct.setSold(productDTO.getSold());
        newProduct.setQuantity(productDTO.getQuantity());
        newProduct.setStatus(1);
        newProduct.setCategory(category);
        newProduct.setCreateAt(LocalDateTime.now());
        Product productSaved = productRepository.save(newProduct);


        List<Image> images = new ArrayList<>();
        productDTO.getImages().forEach(url -> {
            Image image = new Image();
            image.setProduct(productSaved);
            image.setUrl(url);
            Image imageSaved = imageRepository.save(image);
            images.add(imageSaved);
        });
        productSaved.setImages(images);

        List<ProductPhoneCategory> productPhoneCategories = new ArrayList<>();
        productDTO.getPhoneCategoryIds().forEach(id -> {
            ProductPhoneCategory productPhoneCategory = new ProductPhoneCategory();
            ProductPhoneCategoryId productPhoneCategoryId = new ProductPhoneCategoryId();
            productPhoneCategoryId.setProductId(productSaved.getId());
            productPhoneCategoryId.setPhoneCategoryId(id);
            productPhoneCategory.setId(productPhoneCategoryId);
            productPhoneCategory.setProduct(productSaved);
            productPhoneCategory.setPhoneCategory(phoneCategoryRepository.getById(id));
            ProductPhoneCategory productPhoneCategorySaved = productPhoneCategoryRepository.save(productPhoneCategory);
            ProductPhoneCategory productPhoneCategorySaved1 = new ProductPhoneCategory();
            productPhoneCategorySaved1.setProduct(productSaved);
            productPhoneCategorySaved1.setPhoneCategory(productPhoneCategorySaved.getPhoneCategory());
            productPhoneCategories.add(productPhoneCategorySaved1);
        });
        productSaved.setProductPhoneCategories(productPhoneCategories);

        return productRepository.findById(productSaved.getId()).orElse(null);
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

    public List<Product> clearProperties(List<Product> products) {
        for (Product p : products) {
            clearProperty(p);
        }
        return products;
    }

}
