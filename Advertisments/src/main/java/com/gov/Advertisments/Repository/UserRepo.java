package com.gov.Advertisments.Repository;

import com.gov.Advertisments.Model.Enums.Role;
import com.gov.Advertisments.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String Email);
    Optional<User> findByName(String Name);
    List<User> findByRoles(Role role);
    List<User> findAllByActiveTrue();
    @Query(value = "update User u set u.active = false where u.email = :email")
//    InvalidDataAccessApiUsage
    @Modifying
    void removeUser(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE id = :id AND roles = 'MAINTAINER'", nativeQuery = true)
    Optional<User> findMaintainerById(@Param("id") long id);
    @Query(value = "SELECT * FROM users WHERE id = :id AND roles = 'ADMIN'", nativeQuery = true)
    Optional<User> findAdminById(@Param("id") long adminId);
}
