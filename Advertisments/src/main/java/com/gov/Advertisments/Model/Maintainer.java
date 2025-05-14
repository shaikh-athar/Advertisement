package com.gov.Advertisments.Model;

import com.gov.Advertisments.Model.Enums.MaintainerStatus;
import com.gov.Advertisments.Model.Enums.MaintenanceStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "maintainers")
public class Maintainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String maintainerId;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String contactNo;

    private int age;
    private double latitude;
    private double longitude;

    @Enumerated(EnumType.STRING)
    private MaintainerStatus status;

    @OneToMany(mappedBy = "maintainer",cascade = CascadeType.ALL)
    private List<Maintenance> maintenanceTasks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMaintainerId() {
        return maintainerId;
    }

    public void setMaintainerId(String maintainerId) {
        this.maintainerId = maintainerId;
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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public MaintainerStatus getStatus() {
        return status;
    }

    public void setStatus(MaintainerStatus status) {
        this.status = status;
    }

    public List<Maintenance> getMaintenanceTasks() {
        return maintenanceTasks;
    }

    public void setMaintenanceTasks(List<Maintenance> maintenanceTasks) {
        this.maintenanceTasks = maintenanceTasks;
    }
}
