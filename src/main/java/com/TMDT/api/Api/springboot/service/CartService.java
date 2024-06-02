package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.models.CartDetail;
import com.TMDT.api.Api.springboot.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public List<CartDetail> getCartByCustomerId(int customerId) {
        return cartRepository.findByCustomer_Id(customerId);
    }

    public CartDetail add(CartDetail cartDetail) {
        return cartRepository.save(cartDetail);
    }

    public void delete(int id) {
        cartRepository.deleteById(id);
    }

    public void deleteAll(List<CartDetail> cartDetails) {
        cartRepository.deleteAll(cartDetails);
    }

    public CartDetail update(int id, int quantity) {
        CartDetail cartDetail = cartRepository.findById(id).orElse(null);
        if (cartDetail == null) {
            return null;
        }
        cartDetail.setQuantity(quantity);
        return cartRepository.save(cartDetail);
    }
}
