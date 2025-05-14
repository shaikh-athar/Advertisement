package com.gov.Advertisments.Repository;

import com.gov.Advertisments.Model.Maintainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MaintainerRepo extends JpaRepository<Maintainer,Long> {
    Optional<Maintainer> findByMaintainerId(String maintainerId);

    Optional<Maintainer> findByEmail(String email);
}
