package com.funal.vfassessmentgarage.resource;

import java.util.ArrayList;
import java.util.List;

public class StatusResource {

    private String plate;
    private String colour;
    private List<Integer> assignedSlotNumbers;

    public StatusResource(String plate,
                          String colour,
                          List<Integer> assignedSlotNumbers) {
        this.plate = plate;
        this.colour = colour;
        this.assignedSlotNumbers = new ArrayList<>();
    }

    public StatusResource() {
        this.assignedSlotNumbers = new ArrayList<>();
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

    public List<Integer> getAssignedSlotNumbers() {
        return assignedSlotNumbers;
    }

    public void setAssignedSlotNumbers(List<Integer> assignedSlotNumbers) {
        this.assignedSlotNumbers = assignedSlotNumbers;
    }

    @Override
    public String toString() {
        return plate + " " + colour + " [" + assignedSlotNumbers + "]";
    }
}
