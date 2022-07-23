package com.apebble.askwatson.cafe;

import com.apebble.askwatson.cafe.company.Company;
import com.apebble.askwatson.cafe.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CafeJpaRepository extends JpaRepository<Cafe, Long> {
    @Query(value = "select c from Cafe c where (:location is null or c.location=:location) and (:company is null or c.company=:company)")
    List<Cafe> findCafesByLocationAndCompany(@Param("location") Location location, @Param("company") Company company);
}
