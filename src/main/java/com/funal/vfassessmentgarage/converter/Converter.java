package com.funal.vfassessmentgarage.converter;

import com.funal.vfassessmentgarage.domain.Vehicle;
import com.funal.vfassessmentgarage.resource.StatusResource;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public StatusResource toStatusResource(Vehicle vehicle) {
        StatusResource statusResource = new StatusResource();
        statusResource.setPlate(vehicle.getPlate());
        statusResource.setColour(vehicle.getColour());
        vehicle.getSlotsUsed().forEach(slot -> statusResource.getAssignedSlotNumbers().add(slot.getSlotNumber()));
        return statusResource;
    }
}
