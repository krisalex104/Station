package com.kp.station.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class ServiceException extends RuntimeException{

    private final String operation;
    private final Error error;
    private final String description;
    private final Exception exception;

    public ServiceException(String operation, Error error) {
        super(error.getDescription());
        this.operation = operation;
        this.error = error;
        this.description = "";
        this.exception = null;
    }

}
