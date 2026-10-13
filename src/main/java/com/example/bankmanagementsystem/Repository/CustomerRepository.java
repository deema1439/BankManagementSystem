package com.example.bankmanagementsystem.Repository;

import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
  Customer findCustomerById(Integer id);
  Customer findCustomerByUser(User user);





}
