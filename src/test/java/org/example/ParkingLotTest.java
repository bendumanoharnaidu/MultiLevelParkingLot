package org.example;

import org.example.Exception.CarNotFoundException;
import org.example.Exception.ParkingLotIsFullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    @Test
    public void testNumberOfFloorsCantBeLessThanOne() {
        try {
            ParkingLot parkingLot = new ParkingLot(0);
        }
        catch (IllegalArgumentException e) {
            assertEquals("Number of parking floor can't be less than 1", e.getMessage());
        }
    }
    @Test
    public void testCreatingNewParkingLotWithNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-4));
    }

    @Test
    public void testAddingParkingFloorsToParkingLot() {
        ParkingLot parkingLot = new ParkingLot(2);
        Slot[] parkingFloor1 = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL)
        };
        Slot[] parkingFloor2 = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloorOne = new ParkingFloor(1,parkingFloor1);
        ParkingFloor parkingFloorTwo = new ParkingFloor(2,parkingFloor2);
        parkingLot.addParkingFloor(0,parkingFloorOne);
        parkingLot.addParkingFloor(1,parkingFloorTwo);
        ParkingFloor[] parkingFloors = parkingLot.getParkingFloors();
        assertNotNull(parkingFloors[0]);
    }

    @Test
    public void testAddingParkingFloorsToParkingLotMoreThanCapacity() { //
        ParkingLot parkingLot = new ParkingLot(2);
        Slot[] parkingFloor1 = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL)
        };
        Slot[] parkingFloor2 = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloorOne = new ParkingFloor(1,parkingFloor1);
        ParkingFloor parkingFloorTwo = new ParkingFloor(2,parkingFloor2);
        parkingLot.addParkingFloor(0,parkingFloorOne);
        parkingLot.addParkingFloor(1,parkingFloorTwo);

        assertThrows(IllegalArgumentException.class, ()-> {
            parkingLot.addParkingFloor(2,parkingFloorOne);
        });
    }

    @Test
    public void testParkingCarWhenFirstFloorIsFullAndSecondIsEmpty() {
        ParkingLot parkingLot = new ParkingLot(2);
        Slot[] parkingFloorOneSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL)
        };
        Slot[] parkingFloorTwoSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor1 = new ParkingFloor(1,parkingFloorOneSlots);
        ParkingFloor parkingFloor2 = new ParkingFloor(2,parkingFloorTwoSlots);
        parkingLot.addParkingFloor(0,parkingFloor1);
        parkingLot.addParkingFloor(1,parkingFloor2);

        Car car = new Car("TL23AH7007", "Black");

        Ticket ticket = parkingLot.park(car);
        Ticket expectedTicket = new Ticket(2,1,"TL23AH7007");
        assertEquals(expectedTicket,ticket);

    }

    @Test
    public void testParkingCarWhenAllFloorIsFull_GivesException() {
        ParkingLot parkingLot = new ParkingLot(2);
        Slot[] parkingFloorOneSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL)
        };
        Slot[] parkingFloorTwoSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL)
        };
        ParkingFloor parkingFloorOne = new ParkingFloor(1, parkingFloorOneSlots);
        ParkingFloor parkingFloorTwo = new ParkingFloor(2, parkingFloorTwoSlots);
        parkingLot.addParkingFloor(0, parkingFloorOne);
        parkingLot.addParkingFloor(1, parkingFloorTwo);

        Car car = new Car("TL23AH7007", "Black");
        assertThrows(ParkingLotIsFullException.class, () -> {
            parkingLot.park(car);
        });
        try {
            parkingLot.park(car);
        }
        catch (ParkingLotIsFullException e) {
            assertEquals("Parking Lot is Full", e.getMessage());
        }
    }

    @Test
    public void testUnparkingCarWithInvalidTicket() {
        ParkingLot parkingLot = new ParkingLot(2);
        Slot[] parkingFloorOneSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        Slot[] parkingFloorTwoSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL)
        };
        ParkingFloor parkingFloorOne = new ParkingFloor(1, parkingFloorOneSlots);
        ParkingFloor parkingFloorTwo = new ParkingFloor(2, parkingFloorTwoSlots);
        parkingLot.addParkingFloor(0, parkingFloorOne);
        parkingLot.addParkingFloor(1, parkingFloorTwo);

        Car parkedCar = new Car("TL23AH7007", "Black");
        parkingLot.park(parkedCar);
        Ticket ticket = new Ticket(1,2,"ZZ55DD0000");
        try {
            parkingLot.unPark(ticket);
        }
        catch (CarNotFoundException e) {
            assertEquals("Car not found", e.getMessage());
        }
    }

    @Test
    public void testUnparkingCarWithValidTicket() {
        ParkingLot parkingLot = new ParkingLot(2);
        Slot[] parkingFloorOneSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        Slot[] parkingFloorTwoSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL)
        };
        ParkingFloor parkingFloorOne = new ParkingFloor(0, parkingFloorOneSlots);
        ParkingFloor parkingFloorTwo = new ParkingFloor(1, parkingFloorTwoSlots);
        parkingLot.addParkingFloor(0, parkingFloorOne);
        parkingLot.addParkingFloor(1, parkingFloorTwo);

        Car parkedCar = new Car("TL23AH7007", "Black");

        Ticket ticket = parkingLot.park(parkedCar);
        Car expectedCar = parkingLot.unPark(ticket);
        assertEquals(expectedCar,parkedCar);
    }

    @Test
    public void testMakingParkingLotFullThenUnParkingCar_ExpectParkingLotToBeAvailable() {
        ParkingLot parkingLot = new ParkingLot(2);
        Slot[] parkingFloorOneSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL)
        };
        Slot[] parkingFloorTwoSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL)
        };
        ParkingFloor parkingFloorOne = new ParkingFloor(0,parkingFloorOneSlots);
        ParkingFloor parkingFloorTwo = new ParkingFloor(1,parkingFloorTwoSlots);
        parkingLot.addParkingFloor(0,parkingFloorOne);
        parkingLot.addParkingFloor(1,parkingFloorTwo);

        Car car = new Car("TL23AH7007", "Black");
        Ticket ticket = parkingLot.park(car);
        Ticket expectedTicket = new Ticket(1,1,"TL23AH7007");
        assertEquals(expectedTicket,ticket);

        assertTrue(parkingLot.isFull());

        Car unParkedCar = parkingLot.unPark(ticket);
        assertEquals(car,unParkedCar);

        assertFalse(parkingLot.isFull());
    }

    @Test
    public void testMakingParkingLotFull_ExpectParkingLotToBeFull() {
        ParkingLot parkingLot = new ParkingLot(2);
        Slot[] parkingFloorOneSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL)
        };
        Slot[] parkingFloorTwoSlots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL)
        };
        ParkingFloor parkingFloorOne = new ParkingFloor(0,parkingFloorOneSlots);
        ParkingFloor parkingFloorTwo = new ParkingFloor(1,parkingFloorTwoSlots);
        parkingLot.addParkingFloor(0,parkingFloorOne);
        parkingLot.addParkingFloor(1,parkingFloorTwo);

        Car car = new Car("TL23AH7007", "Black");
        Ticket ticket = parkingLot.park(car);
        Ticket expectedTicket = new Ticket(1,1,"TL23AH7007");
        assertEquals(expectedTicket,ticket);

        assertTrue(parkingLot.isFull());
    }

}