package com.gov.Advertisments.Repository;

import com.gov.Advertisments.Model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScreenRepo extends JpaRepository<Screen,Long> {

    Optional<Screen> findBySerialNo(String serialNo);
    List<Screen> findByRating(int rating);
    List<Screen> findByLocationAndActive(String location , Boolean active);

    @Query(value = "update Screen s set s.active = false where s.id = :id")
//    InvalidDataAccessApiUsage
    @Modifying
    void removeScreen(@Param("id") Long id);
}
