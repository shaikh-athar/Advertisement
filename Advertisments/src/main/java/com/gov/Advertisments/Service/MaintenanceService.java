package com.gov.Advertisments.Service;

import com.gov.Advertisments.Model.Request.MaintenanceRequest;

public interface MaintenanceService {
    String addMaintenance(MaintenanceRequest maintenanceRequest);
    String updateMaintenance(String complaintId, MaintenanceRequest maintenanceRequest);
    public String screenRepaired(long id);
}
