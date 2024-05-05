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

//    @PostMapping("/login")
//    ResponseEntity<ResponseObject> login(@RequestBody Customer loginRequest) {
//        // Lấy thông tin từ request
//        String username = loginRequest.getUsername();
//        String password = loginRequest.getPassword();
//
//        // Kiểm tra xem username và password có hợp lệ không (ví dụ: kiểm tra trong database)
//        if (isValidLogin(username, password)) {
//            // Trả về thông tin user nếu đăng nhập thành công
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "Login successful", getUserByUsername(username))
//            );
//        } else {
//            // Trả về thông báo lỗi nếu đăng nhập thất bại
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
//                    new ResponseObject("failed", "Invalid username or password", "")
//            );
//        }
//    }

//    // Phương thức để kiểm tra xem username và password có hợp lệ không
//    private boolean isValidLogin(String username, String password) {
//        // Ở đây bạn có thể thực hiện việc kiểm tra trong database,
//        // hoặc bất kỳ cơ chế xác thực nào khác phù hợp với ứng dụng của bạn.
//        // Đây chỉ là một ví dụ đơn giản, không phải là một cách thực hiện an toàn.
//        return "admin".equals(username) && "password".equals(password);
//    }
//
//    // Phương thức để lấy thông tin user từ database (ví dụ)
//    private Customer getUserByUsername(String username) {
//        // Ở đây bạn có thể truy vấn database để lấy thông tin user dựa trên username.
//        // Đây chỉ là một ví dụ đơn giản, không phải là một cách thực hiện an toàn.
//        // Trong thực tế, bạn nên sử dụng cơ chế bảo mật tốt hơn.
//        // Giả sử có một lớp User tương ứng với bảng trong database.
//        return customerRepository.findByUserName(username);
//    }
}
