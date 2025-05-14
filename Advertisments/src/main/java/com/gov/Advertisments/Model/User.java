package com.gov.Advertisments.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gov.Advertisments.Model.Enums.Role;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    private String name;
    @Column(nullable = false , unique = true)
    private String email;
    @Column(nullable = false )
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role roles;

    @Column
    private Boolean active;

    @OneToMany(mappedBy = "advertiser")
    @JsonIgnore
    private List<Booking> bookings;

    @OneToMany(mappedBy = "admin")
    private List<Maintenance> admin;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }


    public List<Maintenance> getAdmin() {
        return admin;
    }

    public void setAdmin(List<Maintenance> admin) {
        this.admin = admin;
    }

}
