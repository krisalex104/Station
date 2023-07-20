package com.kp.station.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum Error {

    INVALID_DATE_FORMAT(100, "Invalid date format , please provide date in yyyy-MM-dd format", HttpStatus.PRECONDITION_FAILED.value(), Constants.PRECONDITION_FAILED),
    INVALID_START_DATE(101, "start date can't be greater than end date and today's date", HttpStatus.BAD_REQUEST.value(), Constants.BAD_REQUEST),
    INVALID_END_DATE(102, "end date can't be greater than today's date", HttpStatus.BAD_REQUEST.value(), Constants.BAD_REQUEST);


    private final int code;
    private final String description;

    private final int httpStatusCode;

    private final String httpStatusDescription;


    Error(int code, String description, int httpStatusCode, String httpStatusDescription) {
        this.code = code;
        this.description = description;
        this.httpStatusCode = httpStatusCode;
        this.httpStatusDescription = httpStatusDescription;
    }

    private static class Constants {
        public static final String NOT_FOUND = "NOT FOUND";
        public static final String UNAUTHORIZED = "UNAUTHORIZED";
        public static final String BAD_REQUEST = "BAD REQUEST";
        public static final String PRECONDITION_FAILED = "PRECONDITION_FAILED";
    }
}
