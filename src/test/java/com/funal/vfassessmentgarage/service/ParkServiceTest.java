package com.funal.vfassessmentgarage.service;

import com.funal.vfassessmentgarage.TestConstants;
import com.funal.vfassessmentgarage.converter.Converter;
import com.funal.vfassessmentgarage.domain.Slot;
import com.funal.vfassessmentgarage.domain.Vehicle;
import com.funal.vfassessmentgarage.enums.VehicleType;
import com.funal.vfassessmentgarage.exception.NoAvailableSlotExistException;
import com.funal.vfassessmentgarage.exception.NoVehicleExistException;
import com.funal.vfassessmentgarage.factory.VehicleFactory;
import com.funal.vfassessmentgarage.persistence.Persistence;
import com.funal.vfassessmentgarage.resource.StatusResource;
import com.funal.vfassessmentgarage.resource.VehicleResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkServiceTest {

    private ParkService parkService;
    private Converter converter;

    @Mock
    private Persistence persistence;
    @Mock
    private VehicleFactory vehicleFactory;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        converter = new Converter();
        parkService = new ParkServiceImpl(persistence, vehicleFactory, converter);
    }

    private List<Slot> getEmptySlotList() {
        List<Slot> slotList = new LinkedList<>();
        for (int i = 0; i < TestConstants.SLOT_SIZE; i++) {
            slotList.add(new Slot(i + 1, true));
        }
        return slotList;
    }

    @Test
    void givenVehicleResourceWhenParkThenParkCarSuccessfully() {
        // Given
        VehicleResource vehicleResource = new VehicleResource(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR);

        Vehicle vehicle = new Vehicle(vehicleResource.getPlate(), vehicleResource.getColour(), vehicleResource.getVehicleType(), TestConstants.REQUIRED_SLOTS_FOR_CAR);

        List<Slot> slotList = getEmptySlotList();
        when(persistence.getSlots()).thenReturn(slotList);
        when(vehicleFactory.createVehicle(vehicleResource))
                .thenReturn(vehicle);

        // When
        Vehicle parkedVehicle = parkService.park(vehicleResource);

        // Then
        assertNotNull(parkedVehicle);
        assertEquals(TestConstants.REQUIRED_SLOTS_FOR_CAR, parkedVehicle.getSlotsUsed().size());
        assertFalse(slotList.get(TestConstants.REQUIRED_SLOTS_FOR_CAR).isAvailable());
        verify(vehicleFactory).createVehicle(vehicleResource);
        verify(persistence).getSlots();
        verify(persistence).parkVehicle(vehicle);
    }

    @Test
    void givenVehicleResourceWithSlotSizeWhenParkThenParkCarSuccessfully() {
        // Given
        VehicleResource vehicleResource = new VehicleResource(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR);

        Vehicle vehicle = new Vehicle(vehicleResource.getPlate(), vehicleResource.getColour(), vehicleResource.getVehicleType(), TestConstants.SLOT_SIZE);

        when(persistence.getSlots()).thenReturn(getEmptySlotList());
        when(vehicleFactory.createVehicle(vehicleResource))
                .thenReturn(vehicle);

        // When
        Vehicle parkedVehicle = parkService.park(vehicleResource);

        // Then
        assertNotNull(parkedVehicle);
        assertEquals(TestConstants.SLOT_SIZE, parkedVehicle.getSlotsUsed().size());
        verify(vehicleFactory).createVehicle(vehicleResource);
        verify(persistence).getSlots();
        verify(persistence).parkVehicle(vehicle);
    }

    @Test
    void givenVehicleResourceWithNoAvailableSlotWhenParkThenThrowNoAvailableSlotExistException() {
        // Given
        VehicleResource vehicleResource = new VehicleResource(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR);

        Vehicle vehicle = new Vehicle(vehicleResource.getPlate(), vehicleResource.getColour(), vehicleResource.getVehicleType(), TestConstants.REQUIRED_SLOTS_FOR_CAR);

        List<Slot> slotList = getEmptySlotList();
        slotList.forEach(slot -> slot.setAvailable(false));
        when(persistence.getSlots()).thenReturn(slotList);
        when(vehicleFactory.createVehicle(vehicleResource))
                .thenReturn(vehicle);

        // When
        final Executable executable = () -> parkService.park(vehicleResource);

        // Then
        assertThrows(NoAvailableSlotExistException.class, executable);
        verify(vehicleFactory).createVehicle(vehicleResource);
        verify(persistence).getSlots();
        verify(persistence, never()).parkVehicle(vehicle);
        assertEquals(0, vehicle.getSlotsUsed().size());
    }

    @Test
    void givenVehicleResourceWithNotEnoughAvailableSlotWhenParkThenThrowNoAvailableSlotExistException() {
        // Given
        VehicleResource vehicleResource = new VehicleResource(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR);

        Vehicle vehicle = new Vehicle(vehicleResource.getPlate(), vehicleResource.getColour(), vehicleResource.getVehicleType(), TestConstants.REQUIRED_SLOTS_FOR_CAR);

        List<Slot> slotList = getEmptySlotList();
        slotList.stream().filter(slot -> slot.getSlotNumber() < TestConstants.REQUIRED_SLOTS_FOR_CAR).forEach(slot -> slot.setAvailable(false));
        when(persistence.getSlots()).thenReturn(slotList);
        when(vehicleFactory.createVehicle(vehicleResource))
                .thenReturn(vehicle);

        // When
        final Executable executable = () -> parkService.park(vehicleResource);

        // Then
        assertThrows(NoAvailableSlotExistException.class, executable);
        verify(vehicleFactory).createVehicle(vehicleResource);
        verify(persistence).getSlots();
        verify(persistence, never()).parkVehicle(vehicle);
        assertEquals(0, vehicle.getSlotsUsed().size());
    }

    @Test
    void givenVehicleResourceWithAvailableSlotWhenParkThenParkSuccessfully() {
        // Given
        VehicleResource vehicleResource = new VehicleResource(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR);

        Vehicle vehicle = new Vehicle(vehicleResource.getPlate(), vehicleResource.getColour(), vehicleResource.getVehicleType(), TestConstants.REQUIRED_SLOTS_FOR_CAR);

        List<Slot> slotList = getEmptySlotList();
        slotList.get(2).setAvailable(false);
        slotList.get(3).setAvailable(false);
        when(persistence.getSlots()).thenReturn(slotList);
        when(vehicleFactory.createVehicle(vehicleResource))
                .thenReturn(vehicle);

        // When
        Vehicle parkedVehicle = parkService.park(vehicleResource);

        // Then
        assertNotNull(parkedVehicle);
        assertEquals(TestConstants.REQUIRED_SLOTS_FOR_CAR, vehicle.getSlotsUsed().size());
        verify(vehicleFactory).createVehicle(vehicleResource);
        verify(persistence).getSlots();
        verify(persistence).parkVehicle(vehicle);
    }

    @Test
    void givenVehicleNumberWhenLeaveThenLeaveCarSuccessfully() {
        // Given
        List<Slot> slotList = getEmptySlotList();
        Vehicle vehicle = new Vehicle(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR, TestConstants.REQUIRED_SLOTS_FOR_CAR);
        slotList.stream().filter(slot -> slot.getSlotNumber() - 1 < TestConstants.REQUIRED_SLOTS_FOR_CAR).forEach(slot -> {
            slot.setAvailable(false);
            vehicle.getSlotsUsed().add(slot);
        });

        when(persistence.getVehicleWithNumber(1))
                .thenReturn(vehicle);

        // When
        parkService.leave(1);

        // Then
        assertTrue(slotList.stream().allMatch(Slot::isAvailable));
        verify(persistence).getVehicleWithNumber(1);
        verify(persistence).removeVehicleWithNumber(1);
    }

    @Test
    void givenNotExistVehicleNumberWhenLeaveThenThrowVehicleNotExistException() {
        // Given
        when(persistence.getVehicleWithNumber(1))
                .thenThrow(NoVehicleExistException.class);

        // When
        final Executable executable = () -> parkService.leave(1);

        // Then
        assertThrows(NoVehicleExistException.class, executable);
        verify(persistence).getVehicleWithNumber(1);
        verify(persistence, never()).removeVehicleWithNumber(1);
    }

    @Test
    void whenStatusThenReturnGarageStatus() {
        // Given
        List<Vehicle> vehicleList = new LinkedList<>();
        Vehicle vehicle = new Vehicle(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR, TestConstants.REQUIRED_SLOTS_FOR_CAR);
        vehicle.getSlotsUsed().add(new Slot(1, false));
        vehicle.getSlotsUsed().add(new Slot(2, false));
        vehicle.getSlotsUsed().add(new Slot(3, false));
        vehicle.getSlotsUsed().add(new Slot(4, false));
        vehicle.getSlotsUsed().add(new Slot(5, false));
        vehicle.getSlotsUsed().add(new Slot(6, false));
        vehicle.getSlotsUsed().add(new Slot(7, false));

        vehicleList.add(vehicle);

        when(persistence.getVehicles())
                .thenReturn(vehicleList);

        // When
        List<StatusResource> parkedVehicleList = parkService.status();

        // Then
        assertNotNull(parkedVehicleList);
        assertFalse(parkedVehicleList.isEmpty());
    }

}
