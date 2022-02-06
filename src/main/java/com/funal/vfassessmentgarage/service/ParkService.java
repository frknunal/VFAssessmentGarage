package com.funal.vfassessmentgarage.service;

import com.funal.vfassessmentgarage.domain.Vehicle;
import com.funal.vfassessmentgarage.resource.StatusResource;
import com.funal.vfassessmentgarage.resource.VehicleResource;

import java.util.List;

public interface ParkService {
    Vehicle park(VehicleResource vehicleResource);

    void leave(int carNumberToLeave);

    List<StatusResource> status();
}
