package com.funal.vfassessmentgarage.domain;

import com.funal.vfassessmentgarage.enums.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    private String plate;
    private String colour;
    private VehicleType type;
    private int requiredSlots;
    private List<Slot> slotsUsed;

    public Vehicle(String plate, String colour, VehicleType type, int requiredSlots) {
        this.plate = plate;
        this.colour = colour;
        this.type = type;
        this.requiredSlots = requiredSlots;
        this.slotsUsed = new ArrayList<>();
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

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public int getRequiredSlots() {
        return requiredSlots;
    }

    public void setRequiredSlots(int requiredSlots) {
        this.requiredSlots = requiredSlots;
    }

    public List<Slot> getSlotsUsed() {
        return slotsUsed;
    }

    public void setSlotsUsed(List<Slot> slotsUsed) {
        this.slotsUsed = slotsUsed;
    }

    @Override
    public String toString() {
        return "Vehicle -> {\nPlate : " + plate + "\nColour : " + colour + "\nType : " + type + "\nRequiredSlots : " + requiredSlots + "\nSlotsUsed : " + slotsUsed + "\n}";
    }
}
