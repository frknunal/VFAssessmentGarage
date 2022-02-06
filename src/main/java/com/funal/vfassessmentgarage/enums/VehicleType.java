package com.funal.vfassessmentgarage.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.ObjectUtils;

public enum VehicleType {

    CAR("Car"),
    TRUCK("Truck"),
    JEEP("Jeep");

    private final String VEHICLE_TYPE;

    VehicleType(String VEHICLE_TYPE) {
        this.VEHICLE_TYPE = VEHICLE_TYPE;
    }

    public String getType() {
        return VEHICLE_TYPE;
    }

    @JsonCreator
    public static VehicleType fromText(String text) {
        if (ObjectUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Vehicle Type can not be empty!");
        }
        for (VehicleType vehicleType : values()) {
            if (vehicleType.getType().equals(text)) {
                return vehicleType;
            }
        }
        throw new IllegalArgumentException("Vehicle Type '" + text + "' could not be found!");
    }
}
