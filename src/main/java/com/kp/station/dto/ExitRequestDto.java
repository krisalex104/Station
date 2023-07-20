package com.kp.station.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExitRequestDto {

    @JsonProperty("stationName")
    @NotNull
    @Size(min=5, message="station name must not be less than 5 characters")
    private String stationName;
}
