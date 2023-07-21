package com.kp.station.repository;

import com.kp.station.entity.StationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StationDetailsRepository extends JpaRepository<StationDetails, Long> {

    @Query(value = "select s \n" +
            "from StationDetails  s where \n" +
            "s.name=:name and s.createdOn  between :fromDate And :toDate")
    List<StationDetails> fetchStationDetails(@Param("name") String name, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    @Query("SELECT s.name, SUM(s.totalEntry) AS totalEntry, SUM(s.totalExit) AS totalExit " +
            "FROM StationDetails s " +
            "WHERE s.createdOn BETWEEN :fromDate AND :toDate " +
            "GROUP BY s.name")
    List<Object[]> getEntryExitCountsBetweenDates(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
