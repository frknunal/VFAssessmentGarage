package com.funal.vfassessmentgarage.resource;

import com.funal.vfassessmentgarage.enums.VehicleType;

import javax.validation.constraints.NotNull;

public class VehicleResource {

    @NotNull
    private String plate;
    @NotNull
    private String colour;
    @NotNull
    private VehicleType vehicleType;

    public VehicleResource(String plate, String colour, VehicleType vehicleType) {
        this.plate = plate;
        this.colour = colour;
        this.vehicleType = vehicleType;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}
