package com.gov.Advertisments.Service;

import com.gov.Advertisments.Model.Request.UserRequest;
import com.gov.Advertisments.Model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserByEmail(String email);
    List<User> getUserByRoles(String roles);
    String addUser(UserRequest userRequest);
    String updateUser(String email, UserRequest userRequest);
    String deleteUser(String email);
}
