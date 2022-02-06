package com.funal.vfassessmentgarage.exception;

public class NoValidVehicleTypeException extends RuntimeException {

    private static final String MESSAGE = "Vehicle Type '%1' is not exist!";

    public NoValidVehicleTypeException(String vehicleType) {
        super(MESSAGE.replace("%1", vehicleType));
    }

}
