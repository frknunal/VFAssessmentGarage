package com.funal.vfassessmentgarage.exception;

public class NoAvailableSlotExistException extends RuntimeException {

    private static final String MESSAGE = "No Available Slot Exist For '%1'";

    public NoAvailableSlotExistException(String plate) {
        super(MESSAGE.replace("%1", plate));
    }
}
