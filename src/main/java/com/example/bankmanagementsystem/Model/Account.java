package com.example.bankmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Account number cannot be empty")
    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$",
            message = "Account number must follow XXXX-XXXX-XXXX-XXXX")
    @Column(columnDefinition = "varchar(19) not null unique")
    private String accountNumber;

    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Balance must be non-negative")
    @Column(columnDefinition = "double not null")
    private Double balance;

    @Column(columnDefinition = "boolean default false")
    private Boolean isActive = false;



    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Customer customer;

}
