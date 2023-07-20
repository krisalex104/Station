package com.kp.station.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private String message;
    private int statusCode;
    private int code;
    private String operation;
    private String httpStatusDescription;

    private Date timeStamp;
}
