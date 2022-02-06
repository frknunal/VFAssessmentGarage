package com.funal.vfassessmentgarage.controller;

import com.funal.vfassessmentgarage.domain.Vehicle;
import com.funal.vfassessmentgarage.resource.StatusResource;
import com.funal.vfassessmentgarage.resource.VehicleResource;
import com.funal.vfassessmentgarage.service.ParkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/garage")
public class GarageController {

    private final Logger logger = LoggerFactory.getLogger(GarageController.class);
    private final ParkService parkService;

    public GarageController(ParkService parkService) {
        this.parkService = parkService;
    }

    @PostMapping("/park")
    public ResponseEntity<String> park(@Valid @RequestBody VehicleResource vehicleResource) {
        logger.debug("park vehicle {}", vehicleResource.getPlate());
        Vehicle parkedVehicle = parkService.park(vehicleResource);
        return ResponseEntity.ok("Allocated " + parkedVehicle.getSlotsUsed().size() + " slot.");
    }

    @PostMapping("/leave/{vehicleNumber}")
    public ResponseEntity<Void> leave(@NotNull @PathVariable int vehicleNumber) {
        logger.debug("leave park {}", vehicleNumber);
        parkService.leave(vehicleNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<List<StatusResource>> status() {
        return ResponseEntity.ok(parkService.status());
    }

}
