package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendantTest {

    @Test
    public void testAttendantAssignedToParkingLot() {
        Attendant attendant = new Attendant("Manohar");
        ParkingLot parkingLot = new ParkingLot(1);
        Slot slots[] = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slots);
        parkingLot.addParkingFloor(0,parkingFloor);
        attendant.assignParkingLot(parkingLot);

        assertTrue(attendant.isParkingLotAssigned(parkingLot));
    }

    @Test
    public void TestingAttendantToParkCar() {
        Attendant attendant = new Attendant("Manohar");
        Slot slots[] = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingFloor parkingFloor = new ParkingFloor(1,slots);
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.addParkingFloor(0,parkingFloor);

        attendant.assignParkingLot(parkingLot);

        Car car = new Car("AP31AP0007","black");
        Ticket ticket = attendant.park(parkingLot,car);
        Ticket expectedTicket = new Ticket(1,1,"AP31AP0007");
        assertEquals(expectedTicket,ticket);
    }

    @Test
    public void testByAssigningTwoAttendantsToSameParkingLotAndParkTwoDifferentCars() {
        Attendant attendant1 = new Attendant("Manohar");
        Attendant attendant2 = new Attendant("Naidu");
        Slot slots1[] = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.FULL)
        };
        Slot slots2[] = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingFloor parkingFloor1 = new ParkingFloor(1,slots1);
        ParkingFloor parkingFloor2 = new ParkingFloor(2,slots2);
        parkingLot.addParkingFloor(0,parkingFloor1);
        parkingLot.addParkingFloor(1,parkingFloor2);

        attendant1.assignParkingLot(parkingLot);
        attendant2.assignParkingLot(parkingLot);

        Car car1 = new Car("AP31AP0007","black");
        Car car2 = new Car("AP31AP0008","black");

        Ticket ticket1 = attendant1.park(parkingLot,car1);
        Ticket expectedTicket1 = new Ticket(1,1,"AP31AP0007");
        assertEquals(expectedTicket1,ticket1);

        Ticket ticket2 = attendant2.park(parkingLot,car2);
        Ticket expectedTicket2 = new Ticket(2,2,"AP31AP0008");
        assertEquals(expectedTicket2,ticket2);
    }

    @Test
    public void testUnparkingACar_ParkedByOtherAttendant() {
        Attendant attendant1 = new Attendant("Manohar");
        Attendant attendant2 = new Attendant("Naidu");
        Slot slots[] = {
                new Slot(SlotStatus.FULL),
                new Slot(SlotStatus.EMPTY),
                new Slot(SlotStatus.EMPTY)
        };
        ParkingLot parkingLot = new ParkingLot(1);
        ParkingFloor parkingFloor = new ParkingFloor(0, slots);
        parkingLot.addParkingFloor(0,parkingFloor);

        attendant1.assignParkingLot(parkingLot);
        attendant2.assignParkingLot(parkingLot);

        Car car = new Car("AP31AP0007","black");
        Ticket ticket = attendant1.park(parkingLot,car);
        Car unparkedCar = attendant2.unPark(parkingLot,ticket);
        assertEquals(car,unparkedCar);
    }
}