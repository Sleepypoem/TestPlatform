package com.sleepypoem.testplatform.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public final class MyEntityNotFoundException extends RuntimeException {

    public MyEntityNotFoundException(final String message) {
        super(message);
    }

}
