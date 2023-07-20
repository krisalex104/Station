package com.kp.station.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationDetailResponseDto {

    private String stationName;
    private Integer totalEntry;
    private Integer totalExit;
}
