package com.sugar.care.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PARTIAL_CONTENT)
public class BadDataException extends RuntimeException{
    public BadDataException(String message){
        super(message);
    }
}
