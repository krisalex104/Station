package com.kp.station.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "station")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "isEntry",nullable = false)
    private Boolean isEntry;

    @Column(name="created_on",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;

}
