package com.sleepypoem.testplatform.config;


import com.sleepypoem.testplatform.exception.MyInternalException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MyInternalException.class)
    public ResponseEntity<Object> handleMyInternalException(MyInternalException ex, WebRequest request) {
        String bodyOfResponse = "Semantic error: " + ex.getMessage();

        return createResponseEntity(bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


}
