package com.funal.vfassessmentgarage.resource;

public class ExceptionResource {

    private final String message;

    public ExceptionResource(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
