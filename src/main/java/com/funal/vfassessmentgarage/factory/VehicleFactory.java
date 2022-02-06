package com.funal.vfassessmentgarage.factory;

import com.funal.vfassessmentgarage.domain.Vehicle;
import com.funal.vfassessmentgarage.enums.VehicleType;
import com.funal.vfassessmentgarage.exception.NoValidVehicleTypeException;
import com.funal.vfassessmentgarage.resource.VehicleResource;
import org.springframework.stereotype.Component;

@Component
public class VehicleFactory {
    public Vehicle createVehicle(VehicleResource vehicleResource) {
        switch (vehicleResource.getVehicleType()) {
            case CAR:
                return new Vehicle(vehicleResource.getPlate(), vehicleResource.getColour(), VehicleType.CAR, 1);
            case JEEP:
                return new Vehicle(vehicleResource.getPlate(), vehicleResource.getColour(), VehicleType.JEEP, 2);
            case TRUCK:
                return new Vehicle(vehicleResource.getPlate(), vehicleResource.getColour(), VehicleType.TRUCK, 4);
            default:
                throw new NoValidVehicleTypeException(vehicleResource.getVehicleType().name());
        }
    }
}
