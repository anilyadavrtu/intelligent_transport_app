package com.transport.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This car is already with some other driver")
public class CarAlreadyInUseException extends Exception {

    private static final long serialVersionUID = -7471088447532974999L;

    public CarAlreadyInUseException(String message) {
        super(message);
    }
}
