package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.Dtoin.CustomerDto;
import com.example.bankmanagementsystem.Model.Customer;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AuthRepository;
import com.example.bankmanagementsystem.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {


    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;
     //only admin and employee
    public List<Customer>getAllCustomers(Integer user_id){
        User user=authRepository.findUsersById(user_id);
        if(user==null){
            throw new ApiException("Admin or Employee not found");
        }
        if(!user.getRole().equals("ADMIN") && !user.getRole().equals("EMPLOYEE")){
            throw new ApiException("ONLY ADMIN OR EMPLOYEE ALLOWED TO GET ALL CUSTOMERS");
        }
        return customerRepository.findAll() ;
    }

    public Customer getMyInfo(Integer customers_id){
        User user=authRepository.findUsersById(customers_id);
        if (user==null){
            throw new ApiException("Customer not found");
        }
        Customer customer=customerRepository.findCustomerById(customers_id);
        if(customer==null){
            throw new ApiException("User Not Customer ");
        }
        return customer;
    }


    public void register(CustomerDto dto){
      User user=new User();
      user.setUsername(dto.getUsername());
      user.setName(dto.getName());
      user.setEmail(dto.getEmail());
      user.setRole("CUSTOMER");
      String hash=new BCryptPasswordEncoder().encode(dto.getPassword());
      user.setPassword(hash);

      authRepository.save(user);

      Customer customer=new Customer();
      customer.setPhoneNumber(dto.getPhoneNumber());
      customer.setUser(user);
      customerRepository.save(customer);


    }


    public void updateCustomer(Integer customer_id,CustomerDto dto){
      Customer customer=customerRepository.findCustomerById(customer_id);
      if(customer==null){
          throw new ApiException("Customer not found");
      }
      User user=customer.getUser();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole("CUSTOMER");
        String hash=new BCryptPasswordEncoder().encode(dto.getPassword());
        user.setPassword(hash);
        customer.setPhoneNumber(dto.getPhoneNumber());

        authRepository.save(user);
        customerRepository.save(customer);
    }


    public void deleteCustomer(Integer userId) {
        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        authRepository.delete(customer.getUser());
    }










}
