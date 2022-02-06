package com.funal.vfassessmentgarage.service;

import com.funal.vfassessmentgarage.converter.Converter;
import com.funal.vfassessmentgarage.domain.Slot;
import com.funal.vfassessmentgarage.domain.Vehicle;
import com.funal.vfassessmentgarage.exception.NoAvailableSlotExistException;
import com.funal.vfassessmentgarage.factory.VehicleFactory;
import com.funal.vfassessmentgarage.persistence.Persistence;
import com.funal.vfassessmentgarage.resource.StatusResource;
import com.funal.vfassessmentgarage.resource.VehicleResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Service
public class ParkServiceImpl implements ParkService {

    private final Logger logger = LoggerFactory.getLogger(ParkServiceImpl.class);
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Persistence persistence;
    private final VehicleFactory vehicleFactory;
    private final Converter converter;

    public ParkServiceImpl(Persistence persistence,
                           VehicleFactory vehicleFactory,
                           Converter converter) {
        this.persistence = persistence;
        this.vehicleFactory = vehicleFactory;
        this.converter = converter;
    }

    @Override
    public Vehicle park(VehicleResource vehicleResource) {
        logger.debug("parking '{} {} {}'", vehicleResource.getPlate(), vehicleResource.getColour(), vehicleResource.getVehicleType().getType());
        Vehicle vehicle = vehicleFactory.createVehicle(vehicleResource);
        List<Slot> assignedSlots = new ArrayList<>();
        readWriteLock.writeLock().lock();
        try {
            List<Slot> slotList = persistence.getSlots();
            slotList.forEach(slot -> {
                if (assignedSlots.size() == vehicle.getRequiredSlots()) {
                    return;
                }

                if (slot.isAvailable()) {
                    assignedSlots.add(slot);
                    logger.debug("assigning slot {} to {}", slot.getSlotNumber(), vehicle.getPlate());
                } else {
                    logger.debug("clear assigned slots");
                    assignedSlots.clear();
                }

            });
            if (isVehicleParked(assignedSlots, vehicle)) {
                if (!isLastSlotAssignedToCar(assignedSlots, slotList)) {
                    slotList.get(assignedSlots.get(assignedSlots.size() - 1).getSlotNumber()).setAvailable(false);
                }
                assignedSlots.forEach(slot -> slot.setAvailable(false));
                vehicle.setSlotsUsed(assignedSlots);
                persistence.parkVehicle(vehicle);
                logger.debug("vehicle {} parked successfully", vehicle.getPlate());
            } else {
                logger.error("No available slot exist for {}", vehicle.getPlate());
                throw new NoAvailableSlotExistException(vehicle.getPlate());
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }

        return vehicle;
    }

    @Override
    public void leave(int carNumberToLeave) {
        logger.debug("leaving car {}", carNumberToLeave);
        readWriteLock.writeLock().lock();
        try {
            Vehicle vehicle = persistence.getVehicleWithNumber(carNumberToLeave);
            vehicle.getSlotsUsed().forEach(slot -> slot.setAvailable(true));
            persistence.removeVehicleWithNumber(carNumberToLeave);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public List<StatusResource> status() {
        readWriteLock.readLock().lock();
        List<StatusResource> statusResourceList;
        try {
            statusResourceList = persistence.getVehicles().stream().map(converter::toStatusResource).collect(Collectors.toList());
        } finally {
            readWriteLock.readLock().unlock();
        }
        logger.debug("status : {}", statusResourceList);
        return statusResourceList;
    }

    private boolean isVehicleParked(List<Slot> assignedSlots, Vehicle vehicle) {
        return assignedSlots.size() == vehicle.getRequiredSlots();
    }

    private boolean isLastSlotAssignedToCar(List<Slot> assignedSlots, List<Slot> slotList) {
        return assignedSlots.get(assignedSlots.size() - 1).getSlotNumber() == slotList.get(slotList.size() - 1).getSlotNumber();
    }
}
