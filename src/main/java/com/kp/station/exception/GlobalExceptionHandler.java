package com.kp.station.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<ApiResponse> serviceException(ServiceException e) {
        String message = e.getMessage();
        Error error = e.getError();
        ApiResponse response = ApiResponse.builder()
                .code(error.getCode())
                .message(message)
                .statusCode(error.getHttpStatusCode())
                .httpStatusDescription(error.getHttpStatusDescription())
                .operation(e.getOperation())
                .timeStamp(new Date())
                .build();
        return new ResponseEntity<>(response, HttpStatus.valueOf(error.getHttpStatusCode()));
    }

}
