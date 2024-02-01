package org.example;

import org.example.Exception.CarNotFoundException;
import org.example.Exception.ParkingLotIsFullException;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class ParkingFloorTest {

    @Test
    public void testCreateParkingLotWithNoSlots() {
        try {
            Slot slots[] = {};
            ParkingFloor parkingFloor = new ParkingFloor(2,slots);
        }
        catch (IllegalArgumentException e) {
            assertEquals("Number of slots shouldn't be less than 1", e.getMessage());
        }
    }
    @Test
    public void testCreateParkingLotWithNoSlots1() {
        Slot[] slots = {
                new Slot(SlotStatus.EMPTY)
        };
        try {
            ParkingFloor parkingFloor = new ParkingFloor(10,slots);
        }
        catch (IllegalArgumentException e) {
            assertEquals("Number of slots shouldn't be less than 1", e.getMessage());
        }
    }

    @Test
    public void testNewParkingLotWithSl0tsArray() {
        Slot[] slot = {
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slot);

        assertNotNull(parkingFloor);
    }

    @Test
    public void testCarParkingOnEmptySlot() {
        Slot[] slot = {
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slot);
        Car car = new Car("AP31AP0007","black");
        Ticket ticket = parkingFloor.park(car);
        Ticket expectTicket = new Ticket(1,0,"AP31AP0007");

        assertEquals(expectTicket,ticket);
    }

    @Test
    public void testIsCarPresentWithValidRegistrationNumber() {
        Slot[] slot = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slot);
        Car car = new Car("AP31AP0007","black");

        parkingFloor.park(car);

        assertTrue(parkingFloor.isCarPresent("AP31AP0007"));
    }

    @Test
    public void testIsCarPresentWithInvalid() {
        Slot[] slot = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slot);
        Car car = new Car("MH12AR3434","black");
        parkingFloor.park(car);
        assertFalse(parkingFloor.isCarPresent("JK23YY5555"));
    }

    @Test
    public void testCarUnparkingCarFromSlotWithValidCar_WeGetTrue() {
        Slot[] slot = {
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slot);
        Car parkedCar = new Car("AP31AP0007","black");

        Ticket ticket = parkingFloor.park(parkedCar);
        Car unParkedCar = parkingFloor.unpark(ticket.getParkingSlot(),ticket.getRegistrationNumber());

        assertEquals(parkedCar,unParkedCar);
    }

    @Test
    public void testCarUnparkingCarFromSlotWithInvalidRegistrationNumber_GivesException() {
        Slot[] slots = {
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slots);
        Car car = new Car("AP31AP0007","black");

        Ticket ticket = parkingFloor.park(car);
        assertThrows(CarNotFoundException.class, () -> {
            parkingFloor.unpark(ticket.getParkingSlot(),"KR00ZM4334");
        });
    }

    @Test
    public void testParkingCarsOnNearestSlot () {
        Slot[] slots = {
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slots);
        Car car1 = new Car("AP31AP0007","Red");
        Car car2 = new Car("WB01KK7777", "White");

        Ticket ticketOfCar1 = parkingFloor.park(car1);
        Ticket expectedTicket_Car1 = new Ticket(1,0,"AP31AP0007");
        assertEquals(expectedTicket_Car1,ticketOfCar1);

        Ticket ticketOfCar2 = parkingFloor.park(car2);
        Ticket expectedTicket_Car2 = new Ticket(1,2,"WB01KK7777");
        assertEquals(expectedTicket_Car2, ticketOfCar2);

    }

    @Test
    public void testParkingCarsIfNoSlotsAreEmpty_ExpectsException() {
        Slot[] slots = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slots);
        Car car1 = new Car("AP31AP0007","Red");
        Car car2 = new Car("WB01KK7777", "White");
        Ticket ticket = parkingFloor.park(car1);
        Ticket expectedTicket = new Ticket(1,1,"AP31AP0007");

        assertEquals(expectedTicket,ticket);
        assertThrows(ParkingLotIsFullException.class, () -> {
            parkingFloor.park(car2);
        });
    }

    @Test
    public void testParkingCarAndThenUnparkTheSameAndParkAnotherNewCar () {
        Slot[] slots = {
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slots);
        Car car1 = new Car("AP31AP0007","Red");
        Car car2 = new Car("WB01KK7777", "White");

        Ticket ticket = parkingFloor.park(car1);
        Car unparkCar1 = parkingFloor.unpark(ticket.getParkingSlot(),ticket.getRegistrationNumber());
        assertEquals(car1,unparkCar1);

        Ticket ticketOfCar2 = parkingFloor.park(car2);
        Ticket expectedTicketOfCar2 = new Ticket(1,0,"WB01KK7777");
        assertEquals(expectedTicketOfCar2, ticketOfCar2);
    }

    @Test
    public void testParkingCarsOnLastEmptySlot_ExpectParkingFloorFull () {
        Slot[] slots = {
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slots);
        Car car1 = new Car("AP31AP0007","Red");
        Car car2 = new Car("WB01KK7777", "White");

        Ticket ticketOfCar1 = parkingFloor.park(car1);
        Ticket expectedTicketOfCar1 = new Ticket(1,0,"AP31AP0007");
        assertEquals(expectedTicketOfCar1,ticketOfCar1);


        Ticket ticketOfCar2 = parkingFloor.park(car2);
        Ticket expectedTicketOfCar2 = new Ticket(1,2,"WB01KK7777");
        assertEquals(expectedTicketOfCar2, ticketOfCar2);

        assertTrue(parkingFloor.isParkingFloorFull());

    }

    @Test
    public void testUnParkingCarsWhenParkingFloorIfFull_ExpectParkingFloorAvailable () {
        Slot[] slots = {
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slots);
        Car car1 = new Car("AP31AP0007","Red");
        Car car2 = new Car("WB01KK7777", "White");

        Ticket ticketOfCar1 = parkingFloor.park(car1);
        Ticket expectedTicketOfCar1 = new Ticket(1,0,"AP31AP0007");
        assertEquals(expectedTicketOfCar1,ticketOfCar1);

        Ticket ticketOfCar2 = parkingFloor.park(car2);
        Ticket expectedTicketOfCar2 = new Ticket(1,2,"WB01KK7777");
        assertEquals(expectedTicketOfCar2, ticketOfCar2);

        assertTrue(parkingFloor.isParkingFloorFull());

        Car unparkedCar1 = parkingFloor.unpark(ticketOfCar1.getParkingSlot(),ticketOfCar1.getRegistrationNumber());
        assertEquals(car1,unparkedCar1);

        assertFalse(parkingFloor.isParkingFloorFull());

    }

}