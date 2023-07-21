package com.kp.station.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "station_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "total_entry",nullable = false)
    private Integer totalEntry;

    @Column(name = "total_exit",nullable = false)
    private Integer totalExit;
    @Column(name="created_on",nullable = false ,columnDefinition = "DATETIME")
    private Timestamp createdOn;

}


