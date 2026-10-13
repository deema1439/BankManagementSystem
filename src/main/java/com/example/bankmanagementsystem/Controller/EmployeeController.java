package com.example.bankmanagementsystem.Controller;

import com.example.bankmanagementsystem.Api.ApiResponse;
import com.example.bankmanagementsystem.Dtoin.EmployeeDto;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    // الأدمن بس
    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEmployees(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(employeeService.getAllEmployees(user.getId()));
    }

    // الموظف يشوف بياناته
    @GetMapping("/my-info")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(employeeService.getMyInfo(user.getId()));
    }

    //  للكل
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid EmployeeDto dto) {
        employeeService.register(dto);
        return ResponseEntity.status(200).body(new ApiResponse("Employee registered successfully"));
    }

    // الموظف يعدل بياناته
    @PutMapping("/update")
    public ResponseEntity<?> updateEmployee(@AuthenticationPrincipal User user,
                                            @RequestBody @Valid EmployeeDto dto) {
        employeeService.updateEmployee(user.getId(), dto);
        return ResponseEntity.status(200).body(new ApiResponse("Employee updated successfully"));
    }

    // الموظف يحذف حسابه
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteEmployee(@AuthenticationPrincipal User user) {
        employeeService.deleteEmployee(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Employee deleted successfully"));
    }
}