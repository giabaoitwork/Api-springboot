package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.helper.CustomerRole;
import com.TMDT.api.Api.springboot.helper.CustomerStatus;
import com.TMDT.api.Api.springboot.helper.MD5;
import com.TMDT.api.Api.springboot.models.Address;
import com.TMDT.api.Api.springboot.models.Customer;
import com.TMDT.api.Api.springboot.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/users")
public class CustomerControllers {

    //Tạo ra biến userRepository, giống như singleton
    @Autowired
    CustomerRepository customerRepository;

    // get /api/v1/users/getAll
    @GetMapping("/getAll")
    List<Customer> getUsers() {
        return customerRepository.findAll(); // hàm này được cung cấp sẵn.
    }

    // /api/v1/products/1
    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getUserById(@PathVariable int id) {
        Optional<Customer> foundUser = customerRepository.findById(id);
        return foundUser.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Success", foundUser)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find user by id = " + id, "")
                );
    }

    @PostMapping("/login")
    ResponseEntity<ResponseObject> login(@RequestBody Customer loginRequest) {
        // Lấy thông tin từ request
        String email = loginRequest.getEmail();
        String password = MD5.getHashMD5(loginRequest.getPassword());

        Optional<Customer> foundUser = customerRepository.findByEmailAndPassword(email, password);
        return foundUser.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Success", foundUser))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Cannot find user by email = " + email + " and password = " + password, ""));
    }

    @PutMapping("/update")
    ResponseEntity<ResponseObject> update(@RequestBody Customer updateRequest) {
        // Lấy thông tin từ request
        int id = updateRequest.getId();
        String username = updateRequest.getUsername();
        String email = updateRequest.getEmail();
        String password = MD5.getHashMD5(updateRequest.getPassword());
        String phone = updateRequest.getPhone();
        List<Address> addresses = updateRequest.getAddresses();
        int role = updateRequest.getRole();
        int status = updateRequest.getStatus();

        Customer customer = customerRepository.findById(id).map(user -> {
            user.setId(id);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setAddresses(addresses);
            user.setRole(role);
            user.setStatus(status);
            return customerRepository.save(user);
        }).orElseGet(() -> {
            Customer newCustomer = new Customer();
            newCustomer.setId(id);
            newCustomer.setUsername(username);
            newCustomer.setEmail(email);
            newCustomer.setPassword(password);
            newCustomer.setPhone(phone);
            newCustomer.setAddresses(addresses);
            newCustomer.setRole(role);
            newCustomer.setStatus(status);
            return customerRepository.save(newCustomer);
        });
        ResponseObject response = new ResponseObject("Success", "Customer updated successfully.", customer);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    ResponseEntity<ResponseObject> register(@RequestBody Customer registerRequest) {
        // Lấy thông tin từ request
        String username = registerRequest.getUsername();
        String email = registerRequest.getEmail();
        String password = MD5.getHashMD5(registerRequest.getPassword());

        Optional<Customer> foundUser = customerRepository.findByEmail(email);
        if(foundUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", "Email already exists", ""));
        } else {
            Customer customer = new Customer();
            customer.setUsername(username);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setRole(CustomerRole.USER);
            customer.setStatus(CustomerStatus.ACTIVE);
            customerRepository.save(customer);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("ok", "Success", customer));
        }
    }
}
