package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
