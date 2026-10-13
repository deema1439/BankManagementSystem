package com.example.bankmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String username;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // عشان ما يطلع الباسورد للادمن وقت مايسوي get all
    @NotEmpty(message = "password Should not be Empty")
//    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#])[A-Za-z\\d@$!%*?&#]{6,}$",
//            message = "Password must be at least 6 characters and contain uppercase, lowercase, number and special character")
    @Size(min = 6,message = "password length at least 6 characters  ")
    @Column(columnDefinition = "varchar(255) not null")
   private String password;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email must be valid")
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;

    @Pattern(regexp = "^(CUSTOMER|EMPLOYEE|ADMIN)$",
            message = "Role must be CUSTOMER, EMPLOYEE or ADMIN")
    @Column(columnDefinition = "varchar(10) not null")
    private String role;


    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
    private Employee employee;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }
    //بناء على ال set
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}




















