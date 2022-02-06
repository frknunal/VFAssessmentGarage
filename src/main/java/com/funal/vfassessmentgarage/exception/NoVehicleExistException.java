package com.funal.vfassessmentgarage.exception;

public class NoVehicleExistException extends RuntimeException {

    private static final String MESSAGE = "Vehicle with number '%1' is not exist!";

    public NoVehicleExistException(int vehicleNumber) {
        super(MESSAGE.replace("%1", String.valueOf(vehicleNumber)));
    }

}
