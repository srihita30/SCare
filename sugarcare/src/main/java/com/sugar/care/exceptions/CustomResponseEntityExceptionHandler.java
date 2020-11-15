package com.sugar.care.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public  ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomExceptionTemplate template =
                new CustomExceptionTemplate(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity(template,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public  ResponseEntity<Object> handleResourceConflictException(ResourceAlreadyExistsException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomExceptionTemplate template =
                new CustomExceptionTemplate(new Date(),ex.getMessage(),request.getDescription(false));
        return new ResponseEntity(template,HttpStatus.CONFLICT);
    }

}
