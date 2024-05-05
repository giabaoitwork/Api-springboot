package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByNameAndId(String name, int id);
}
