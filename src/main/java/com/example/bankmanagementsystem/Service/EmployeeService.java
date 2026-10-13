package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.Dtoin.EmployeeDto;
import com.example.bankmanagementsystem.Model.Employee;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AuthRepository;
import com.example.bankmanagementsystem.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;
    //admin
      public List<Employee> getAllEmployees(Integer admin_id){
        User user=authRepository.findUsersById(admin_id);
        if(user==null){
            throw new ApiException("Admin not found");
        }
        if(!user.getRole().equals("ADMIN")){
            throw new ApiException("only admin can get Employees");
        }
        return employeeRepository.findAll();
    }

    public Employee getMyInfo(Integer employee_id){
     User user=authRepository.findUsersById(employee_id);
     if(user==null){
         throw new ApiException("Employee not found");
     }
     if (!user.getRole().equals("EMPLOYEE")) {
            throw new ApiException("Only employee can view his info");
     }
     Employee employee=employeeRepository.findEmployeeById(employee_id);
     if(employee==null){
         throw new ApiException("User is not an employee");
     }
     return employee;
    }

    public void register(EmployeeDto dto){
     User user=new User();
     user.setEmail(dto.getEmail());
     user.setName(dto.getName());
     user.setUsername(dto.getUsername());
     user.setRole("EMPLOYEE");
     String hash=new BCryptPasswordEncoder().encode(dto.getPassword());
     user.setPassword(hash);
     authRepository.save(user);

     Employee employee=new Employee();
     employee.setPosition(dto.getPosition());
     employee.setSalary(dto.getSalary());
     employee.setUser(user);
     employeeRepository.save(employee);
    }


    public void updateEmployee(Integer employee_id,EmployeeDto dto){
          User user=authRepository.findUsersById(employee_id);
          if(user==null){
              throw new ApiException("user not found");
          }
          if(!user.getRole().equals("EMPLOYEE")){
              throw new ApiException("only employee can update");
          }
          Employee employee=employeeRepository.findEmployeeById(employee_id);
          if(employee==null){
              throw new ApiException("employee not found");
          }

          user.setUsername(dto.getUsername());
          user.setName(dto.getName());
          user.setEmail(dto.getEmail());
          String hash=new BCryptPasswordEncoder().encode(dto.getPassword());
          user.setPassword(hash);
          employee.setPosition(dto.getPosition());
          employee.setSalary(dto.getSalary());
          authRepository.save(user);
          employeeRepository.save(employee);



    }


    public void deleteEmployee(Integer employee_id) {
        User user = authRepository.findUsersById(employee_id);
        if (user == null) {
            throw new ApiException("User not found");
        }
        if (!user.getRole().equals("EMPLOYEE")) {
            throw new ApiException("Only employee can delete his account");
        }
        Employee employee = employeeRepository.findEmployeeById(employee_id);
        if (employee == null) {
            throw new ApiException("Employee not found");
        }
        authRepository.delete(user);
    }



















}
