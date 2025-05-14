package com.gov.Advertisments.Repository;

import com.gov.Advertisments.Model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepo extends JpaRepository<Banner,Long> {
    List<Banner> findAllByActiveTrue();

}
