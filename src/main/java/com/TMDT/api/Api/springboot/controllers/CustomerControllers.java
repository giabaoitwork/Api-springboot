package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.models.Customer;
import com.TMDT.api.Api.springboot.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/customers")
public class CustomerControllers {

    //Tạo ra biến userRepository, giống như singleton
    @Autowired
    CustomerRepository customerRepository;

    // get /api/v1/users/getAll
    @GetMapping("/getAll")
    ResponseEntity<List<Customer>> getUsers() {
        return new ResponseEntity<>(customerRepository.findAll(),
                HttpStatus.OK); // hàm này được cung cấp sẵn.
    }

    // /api/v1/products/1
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(@PathVariable int id) {
        Optional<Customer> foundUser = customerRepository.findById(id);
        return foundUser.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Success", foundUser)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find user by id = " + id, "")
                );
    }

    @PostMapping(value = "/login",consumes = {"application/json"}, produces = {"application/json"})
    ResponseEntity<ResponseObject> login(@RequestBody Customer customer) {
        System.out.println(customer);
//        String email = loginRequest.getUsername();
//        String password = loginRequest.getPassword();

//        Optional<Customer> foundUser = customerRepository.findByEmail(email);

//        return foundUser.isPresent() && foundUser.get().getPassword().equals(password) ?
//                ResponseEntity.status(HttpStatus.OK).body(
//                        new ResponseObject("ok", "Login successful", foundUser)
//                ) :
//                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
//                        new ResponseObject("failed", "Invalid username or password", "")
//                );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ResponseObject("failed", "Invalid username or password", "")
        );
    }
    @PostMapping(value ="/test")
    String test(@RequestBody Customer customer){
        Customer customer1 = new Customer();
        return "ok";
    }
}
