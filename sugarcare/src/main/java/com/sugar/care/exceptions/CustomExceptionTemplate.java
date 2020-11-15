package com.sugar.care.exceptions;

import java.util.Date;

public class CustomExceptionTemplate extends Exception{
    private Date date;

    private String message;

    private String details;

    public CustomExceptionTemplate(Date date , String message ,String details){
        this.date = date;
        this.message = message;
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
