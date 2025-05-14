package com.gov.Advertisments.ServiceImple;

import com.gov.Advertisments.Model.Enums.Role;
import com.gov.Advertisments.Model.Request.UserRequest;
import com.gov.Advertisments.Model.User;
import com.gov.Advertisments.Repository.UserRepo;
import com.gov.Advertisments.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    private UserRepo userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAllByActiveTrue();
    }

    public Optional<User> getUserByEmail(String email) {
        return Optional.of(userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found !!")));
    }

    public List<User> getUserByRoles(String roles){
        return userRepository.findByRoles(Role.valueOf(roles));
    }

    //    DataIntegrityViolationException if filed data duplication occur
    public String addUser(UserRequest userRequest) {
        try {
            if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
                return "User already exists!";
            }

            User user = new User();
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            user.setRoles(userRequest.getRoles());
            user.setActive(Boolean.TRUE);
            userRepository.save(user);

            return "User added successfully";
        } catch (Exception e) {
            return "Error adding user: " + e.getMessage();
        }
    }

    public String updateUser(String email, UserRequest userRequest) {
        try {
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isEmpty()) {
                return "User not found!";
            }

            User user = existingUser.get();
            user.setName(userRequest.getName());
            user.setPassword(userRequest.getPassword());
            user.setRoles(userRequest.getRoles());
            user.setActive(Boolean.TRUE);
            userRepository.save(user);

            return "User updated successfully";
        } catch (Exception e) {
            return "Error updating user: " + e.getMessage();
        }
    }


    @Transactional
//    InvalidDataAccessApiUsage : For Update/Delete
    public String deleteUser(String email) {
        userRepository.removeUser(email);
        return "User Removed" ;
    }
}
