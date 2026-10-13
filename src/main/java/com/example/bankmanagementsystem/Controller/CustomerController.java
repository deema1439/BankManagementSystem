package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.Dtoin.CustomerDto;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // الأدمن والموظف بس
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllCustomers(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(customerService.getAllCustomers(user.getId()));
    }


    @GetMapping("/my-info")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(customerService.getMyInfo(user.getId()));
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid CustomerDto dto) {
        customerService.register(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Customer registered successfully"));
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@AuthenticationPrincipal User user,
                                            @RequestBody @Valid CustomerDto dto) {
        customerService.updateCustomer(user.getId(), dto);
        return ResponseEntity.status(200).body(new ApiResponse("Customer updated successfully"));
    }


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer(@AuthenticationPrincipal User user) {
        customerService.deleteCustomer(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted successfully"));
    }
}