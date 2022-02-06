package com.funal.vfassessmentgarage.persistence;

import com.funal.vfassessmentgarage.domain.Slot;
import com.funal.vfassessmentgarage.domain.Vehicle;

import java.util.List;

public interface Persistence {
    List<Slot> getSlots();

    void parkVehicle(Vehicle vehicle);

    Vehicle getVehicleWithNumber(int vehicleNumber);

    void removeVehicleWithNumber(int vehicleNumber);

    List<Vehicle> getVehicles();
}
