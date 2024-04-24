package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
