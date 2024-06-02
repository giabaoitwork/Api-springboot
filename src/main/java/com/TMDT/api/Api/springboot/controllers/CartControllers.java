package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.models.CartDetail;
import com.TMDT.api.Api.springboot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/api/v1/carts")
public class CartControllers {
    @Autowired
    private CartService cartService;

    @GetMapping("/getByCustomerId")
    public ResponseEntity<ResponseObject> getCartByCustomerId(@RequestParam int customerId) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.getCartByCustomerId(customerId)));
    }

    public ResponseEntity<ResponseObject> add(CartDetail cartDetail) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.add(cartDetail)));
    }

}
