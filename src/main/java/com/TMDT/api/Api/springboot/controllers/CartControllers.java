package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.models.CartDetail;
import com.TMDT.api.Api.springboot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/carts")
public class CartControllers {
    @Autowired
    private CartService cartService;

    @GetMapping("/getByCustomerId/{customerId}")
    public ResponseEntity<ResponseObject> getCartByCustomerId(@PathVariable int customerId) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.getCartByCustomerId(customerId)));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<ResponseObject> getCartByCartId(@PathVariable int cartId) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.get(cartId)));
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> add(@RequestBody CartDetail cartDetail) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.add(cartDetail)));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable int id, @RequestParam int quantity) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.update(id, quantity)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> delete(@PathVariable int id) {
        cartService.delete(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Success", ""));
    }

    @DeleteMapping("/deleteAllByCustomerId/{customerId}")
    public ResponseEntity<ResponseObject> deleteAllByCustomerId(@PathVariable int customerId) {
        cartService.deleteAllByCustomerId(customerId);
        return ResponseEntity.ok(new ResponseObject("ok", "Success", ""));
    }

    @PostMapping("/getTotalPrice")
    public ResponseEntity<ResponseObject> getTotalPrice(@RequestBody List<CartDetail> cartDetails) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", cartService.calculateTotalAmount(cartDetails)));
    }

}
