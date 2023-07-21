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
public class EntryRequestDto {
    @NotNull
    @Size(min=3,max = 30,message="Station name must not be less than 3 and greater 30 characters")
    private String stationName;
}
