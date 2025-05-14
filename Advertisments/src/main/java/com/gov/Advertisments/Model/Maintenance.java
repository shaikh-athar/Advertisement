package com.gov.Advertisments.Model;

import com.gov.Advertisments.Model.Enums.MaintenanceStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(name = "issue", nullable = false)
    private String issue;

    @Column(name = "complaint_id", unique = true, nullable = false)
    private String complaintId;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MaintenanceStatus status;

    @Column(name = "maintenance_time")
    private LocalDateTime maintenanceTime;

    @ManyToOne
    @JoinColumn(name = "maintainer_id")
    private Maintainer maintainer;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public MaintenanceStatus getStatus() {
        return status;
    }

    public void setStatus(MaintenanceStatus status) {
        this.status = status;
    }

    public Maintainer getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(Maintainer maintainer) {
        this.maintainer = maintainer;
    }


    public LocalDateTime getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(LocalDateTime maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }
}
