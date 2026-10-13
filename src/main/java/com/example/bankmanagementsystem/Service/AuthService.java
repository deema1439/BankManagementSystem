package com.example.bankmanagementsystem.Service;

import com.example.bankmanagementsystem.Api.ApiException;
import com.example.bankmanagementsystem.Model.User;
import com.example.bankmanagementsystem.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;

   public List<User>getAllUsers(Integer admin_id){
       User admin=authRepository.findUsersById(admin_id);
       if(admin==null){
           throw new ApiException("admin not found");
       }
       if(!admin.getRole().equals("ADMIN")){
           throw new ApiException("only admin allowed to get users");
       }
       return authRepository.findAll();
   }
 //add Admin by Admin
   public void addAdmin(Integer admin_id,User user){
       User admin=authRepository.findUsersById(admin_id);
       if(admin==null){
           throw new ApiException("admin not found");
       }
       if(!admin.getRole().equals("ADMIN")){
           throw new ApiException("only admin allowed to add admin");
       }
       String hash=new BCryptPasswordEncoder().encode(user.getPassword());
       user.setPassword(hash);
       user.setRole("ADMIN");
       authRepository.save(user);
   }

//اليوزر يعدل بياناته
   public void updateUser(Integer user_id,User user){
   User oldUser=authRepository.findUsersById(user_id);
   if(oldUser==null){
       throw new ApiException("User not found");
   }
   oldUser.setUsername(user.getUsername());
   oldUser.setName(user.getName());
   oldUser.setEmail(user.getEmail());
   String hash=new BCryptPasswordEncoder().encode(user.getPassword());
   oldUser.setPassword(hash);
   authRepository.save(oldUser);
   }

   public void deleteUser(Integer user_id){
       User user=authRepository.findUsersById(user_id);
       if(user==null){
           throw new ApiException("User not found");
       }
       authRepository.delete(user);
   }

    // PUT: الأدمن يعدل بيانات أي يوزر
    public void updateUserByAdmin(Integer adminId, Integer userId, User user) {
        User admin = authRepository.findUsersById(adminId);
        if (admin == null) {
            throw new ApiException("Admin not found");
        }
        if (!admin.getRole().equals("ADMIN")) {
            throw new ApiException("Only admin can update users");
        }
        User oldUser = authRepository.findUsersById(userId);
        if (oldUser == null) {
            throw new ApiException("User not found");
        }
        oldUser.setUsername(user.getUsername());
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());

        authRepository.save(oldUser);
    }

    //  الأدمن يحذف أي يوزر
    public void deleteUserByAdmin(Integer adminId, Integer userId) {
        User admin = authRepository.findUsersById(adminId);
        if (admin == null) {
            throw new ApiException("Admin not found");
        }

        if (!admin.getRole().equals("ADMIN")) {
            throw new ApiException("Only admin can delete users");
        }

        if (adminId.equals(userId)) {
            throw new ApiException("Admin cannot delete himself from here");
        }
        User user = authRepository.findUsersById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        authRepository.delete(user);
    }




































}
