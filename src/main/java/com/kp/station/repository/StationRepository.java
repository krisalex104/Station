package com.kp.station.repository;

import com.kp.station.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StationRepository extends JpaRepository<Station, Integer> {


    @Query(value = "SELECT name, " +
            "COUNT(CASE WHEN is_entry = 1 THEN 1 END) AS entryCount, " +
            "COUNT(CASE WHEN is_entry = 0 THEN 1 END) AS exitCount " +
            "FROM station " +
            "WHERE created_on BETWEEN :startDate AND :endDate " +
            "GROUP BY name", nativeQuery = true)
    List<Object[]> getEntryExitCountsBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


}
