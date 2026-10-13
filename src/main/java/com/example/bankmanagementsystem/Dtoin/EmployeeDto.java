package com.example.bankmanagementsystem.Dtoin;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    private String username;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;

    @NotEmpty(message = "password Should not be Empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{6,}$",
            message = "Password must be at least 6 characters and contain uppercase, lowercase, number and special character")
    @Size(min = 6,message = "password length at least 6 characters  ")
    private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    private String email;


    @NotEmpty(message = "Position cannot be empty")
    private String position;

    @NotNull(message = "Salary cannot be null")
    @PositiveOrZero(message = "Salary must be non-negative")
    private Double salary;










}
