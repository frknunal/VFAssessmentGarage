package com.funal.vfassessmentgarage.persistence;

import com.funal.vfassessmentgarage.TestConstants;
import com.funal.vfassessmentgarage.domain.Vehicle;
import com.funal.vfassessmentgarage.enums.VehicleType;
import com.funal.vfassessmentgarage.exception.NoVehicleExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class PersistenceTest {

    private Persistence persistence;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        persistence = new PersistenceImpl();
    }

    @Test
    void whenGetSLotsThenReturnSlotList() {
        assertNotNull(persistence.getSlots());
    }

    @Test
    void givenVehicleWhenParkVehicleThenParkVehicleSuccessfully() {
        // Given
        Vehicle vehicle = new Vehicle(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR, TestConstants.REQUIRED_SLOTS_FOR_CAR);

        // When
        persistence.parkVehicle(vehicle);

        // Then
        assertEquals(1, persistence.getVehicles().size());
    }

    @Test
    void givenVehicleNumberWhenGetVehicleWithNumberThenReturnVehicle() {
        // Given
        Vehicle vehicle = new Vehicle(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR, TestConstants.REQUIRED_SLOTS_FOR_CAR);
        persistence.parkVehicle(vehicle);

        // When
        Vehicle returnedVehicle = persistence.getVehicleWithNumber(1);

        // Then
        assertNotNull(returnedVehicle);
        assertEquals(vehicle.getPlate(), returnedVehicle.getPlate());
        assertEquals(1, persistence.getVehicles().size());
    }

    @Test
    void givenNotExistVehicleNumberWhenGetVehicleWithNumberThenReturnVehicle() {
        // Given
        Vehicle vehicle = new Vehicle(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR, TestConstants.REQUIRED_SLOTS_FOR_CAR);
        persistence.parkVehicle(vehicle);

        // When
        final Executable executable = () -> persistence.getVehicleWithNumber(2);

        // Then
        assertThrows(NoVehicleExistException.class, executable);
        assertEquals(1, persistence.getVehicles().size());
    }

    @Test
    void givenNegativeVehicleNumberWhenGetVehicleWithNumberThenReturnVehicle() {
        // Given
        Vehicle vehicle = new Vehicle(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR, TestConstants.REQUIRED_SLOTS_FOR_CAR);
        persistence.parkVehicle(vehicle);

        // When
        final Executable executable = () -> persistence.getVehicleWithNumber(2);

        // Then
        assertThrows(NoVehicleExistException.class, executable);
        assertEquals(1, persistence.getVehicles().size());
    }

    @Test
    void givenVehicleNumberWhenRemoveVehicleWithNumberThenRemoveVehicle() {
        // Given
        Vehicle vehicle = new Vehicle(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR, TestConstants.REQUIRED_SLOTS_FOR_CAR);
        persistence.parkVehicle(vehicle);

        // When
        persistence.removeVehicleWithNumber(1);

        // Then
        assertEquals(0, persistence.getVehicles().size());
    }

    @Test
    void givenNotExistVehicleNumberWhenRemoveVehicleWithNumberThenThrowNoVehicleExistException() {
        // Given
        Vehicle vehicle = new Vehicle(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR, TestConstants.REQUIRED_SLOTS_FOR_CAR);
        persistence.parkVehicle(vehicle);

        // When
        final Executable executable = () -> persistence.removeVehicleWithNumber(2);

        // Then
        assertThrows(NoVehicleExistException.class, executable);
        assertEquals(1, persistence.getVehicles().size());
    }

    @Test
    void givenNegativeVehicleNumberWhenRemoveVehicleWithNumberThenThrowNoVehicleExistException() {
        // Given
        Vehicle vehicle = new Vehicle(TestConstants.PLATE, TestConstants.COLOUR, VehicleType.CAR, TestConstants.REQUIRED_SLOTS_FOR_CAR);
        persistence.parkVehicle(vehicle);

        // When
        final Executable executable = () -> persistence.removeVehicleWithNumber(-1);

        // Then
        assertThrows(NoVehicleExistException.class, executable);
        assertEquals(1, persistence.getVehicles().size());
    }

}
