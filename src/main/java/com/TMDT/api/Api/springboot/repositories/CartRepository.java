package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartDetail, Integer> {
    List<CartDetail> findByCustomer_Id(int cartId);
    void deleteCartDetailByCustomer_Id(int customerId);
}
