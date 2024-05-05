package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
