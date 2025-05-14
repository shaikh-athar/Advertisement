package com.gov.Advertisments.Model.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MaintenanceRequest {

    @NotBlank(message = "Issue of Screen is Required")
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "Issue must contain only letters and Digits")
    private String issue;
    @NotNull(message = "Admin name is Required")
    private long adminId;
    @NotNull(message = "Screen ID is required")
    private long screenId;


    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }


    public long getScreenId() {
        return screenId;
    }

    public void setScreenId(long screenId) {
        this.screenId = screenId;
    }
}
