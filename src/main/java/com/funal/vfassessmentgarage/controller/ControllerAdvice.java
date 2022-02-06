package com.funal.vfassessmentgarage.controller;

import com.funal.vfassessmentgarage.exception.NoAvailableSlotExistException;
import com.funal.vfassessmentgarage.exception.NoValidVehicleTypeException;
import com.funal.vfassessmentgarage.exception.NoVehicleExistException;
import com.funal.vfassessmentgarage.resource.ExceptionResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({NoVehicleExistException.class, NoValidVehicleTypeException.class})
    public ResponseEntity<ExceptionResource> badRequest(final RuntimeException e) {
        ExceptionResource exceptionResource = new ExceptionResource(e.getMessage());
        return ResponseEntity.badRequest().body(exceptionResource);
    }

    @ExceptionHandler({NoAvailableSlotExistException.class})
    public ResponseEntity<String> garageIsFull(final NoAvailableSlotExistException e) {
        return ResponseEntity.badRequest().body("Garage is full.");
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionResource> unexpectedError(final Exception e) {
        ExceptionResource exceptionResource = new ExceptionResource("Unexpected Error Occurred. Details : " + e.getMessage());
        return ResponseEntity.badRequest().body(exceptionResource);
    }

}
