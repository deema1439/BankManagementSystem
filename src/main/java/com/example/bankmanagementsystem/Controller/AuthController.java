package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal User admin) {
        return ResponseEntity.status(200).body(authService.getAllUsers(admin.getId()));
    }

    @PostMapping("/add-admin")
    public ResponseEntity<?> addAdmin(@AuthenticationPrincipal User admin, @RequestBody @Valid User user) {
        authService.addAdmin(admin.getId(), user);
        return ResponseEntity.status(200).body(new ApiResponse("Admin added successfully"));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal User user, @RequestBody @Valid User newUser) {
        authService.updateUser(user.getId(), newUser);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal User user) {
        authService.deleteUser(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
    }

    @PutMapping("/update-by-admin/{userId}")
    public ResponseEntity<?> updateUserByAdmin(@AuthenticationPrincipal User admin, @PathVariable Integer userId, @RequestBody @Valid User user) {
        authService.updateUserByAdmin(admin.getId(), userId, user);
        return ResponseEntity.status(200).body(new ApiResponse("User updated by admin successfully"));
    }

    @DeleteMapping("/delete-by-admin/{userId}")
    public ResponseEntity<?> deleteUserByAdmin(@AuthenticationPrincipal User admin, @PathVariable Integer userId) {
        authService.deleteUserByAdmin(admin.getId(), userId);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted by admin successfully"));
    }
}