package com.gov.Advertisments.Repository;

import com.gov.Advertisments.Model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaintenanceRepo extends JpaRepository<Maintenance,Long> {
    Optional<Maintenance> findByComplaintId(String complaintId);
    Optional<Maintenance> findByScreenId(long screenId);
}
