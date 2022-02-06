package com.funal.vfassessmentgarage.persistence;

import com.funal.vfassessmentgarage.domain.Slot;
import com.funal.vfassessmentgarage.domain.Vehicle;
import com.funal.vfassessmentgarage.exception.NoVehicleExistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
public class PersistenceImpl implements Persistence {

    private final Logger logger = LoggerFactory.getLogger(PersistenceImpl.class);
    private final List<Slot> slotList = new ArrayList<>();
    private final List<Vehicle> vehicleList = new LinkedList<>();
    private final int NUMBER_OF_SLOTS = 10;

    public PersistenceImpl() {
        fillSlotList();
    }

    @Override
    public List<Slot> getSlots() {
        logger.debug("returning slot list : {}", slotList);
        return slotList;
    }

    @Override
    public void parkVehicle(Vehicle vehicle) {
        logger.debug("adding vehicle : {}", vehicle);
        vehicleList.add(vehicle);
    }

    @Override
    public Vehicle getVehicleWithNumber(int vehicleNumber) {
        checkVehicleNumber(vehicleNumber);
        Vehicle vehicle = vehicleList.get(vehicleNumber - 1);
        logger.debug("getting vehicle {} with number : {}", vehicle, vehicleNumber);
        return vehicle;
    }

    @Override
    public void removeVehicleWithNumber(int vehicleNumber) {
        checkVehicleNumber(vehicleNumber);
        Vehicle vehicle = vehicleList.get(vehicleNumber - 1);
        logger.debug("removing vehicle {} with number : {}", vehicle, vehicleNumber);
        vehicleList.remove(vehicleNumber - 1);
    }

    @Override
    public List<Vehicle> getVehicles() {
        logger.debug("returning vehicle list : {}", vehicleList);
        return vehicleList;
    }

    private void fillSlotList() {
        logger.debug("Filling slots for {}", NUMBER_OF_SLOTS);
        for (int i = 0; i < NUMBER_OF_SLOTS; i++) {
            slotList.add(new Slot(i + 1, true));
        }
    }

    private void checkVehicleNumber(int vehicleNumber) {
        logger.debug("checking vehicle number : {}", vehicleNumber);
        if (vehicleNumber > vehicleList.size() || vehicleNumber < 1) {
            throw new NoVehicleExistException(vehicleNumber);
        }
    }
}
