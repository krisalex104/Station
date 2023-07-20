package com.kp.station.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationDetailsRequestDto {

    @NotNull(message = "Start date shouldn't be null")
    private String startDate;

    @NotNull(message = "end date shouldn't be null")
    private String endDate;

}
