package com.example.bankmanagementsystem.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Employee {


    @Id //one to one user
    private Integer id;

    @NotEmpty(message = "Position cannot be empty")
    @Column(columnDefinition = "varchar(30) not null")
    private String position;

    @NotNull(message = "Salary cannot be null")
    @PositiveOrZero(message = "Salary must be non-negative")
    @Column(columnDefinition = "double not null")
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;


}
