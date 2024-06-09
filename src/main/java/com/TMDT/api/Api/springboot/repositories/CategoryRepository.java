package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
