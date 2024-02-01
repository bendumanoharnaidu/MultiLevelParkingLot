package org.example;

import org.example.Exception.ParkingLotNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class Attendant {
    private String name;
    private Set<ParkingArea> assignedParkingArea;

    public Attendant(String name) {
        this.name = name;
        this.assignedParkingArea = new HashSet<>();
    }
    void assignParkingLot(ParkingLot parkingLot) {
        assignedParkingArea.add(parkingLot);
    }
    boolean isParkingLotAssigned(ParkingLot parkingLot) {
        return assignedParkingArea.contains(parkingLot);
    }

    public Ticket park(ParkingLot parkingLot, Car car) {
        if (isParkingLotAssigned(parkingLot)) {
            return parkingLot.park(car);
        }
        else {
            throw new ParkingLotNotFoundException();
        }
    }

    public Car unPark(ParkingLot parkingLot, Ticket ticket) {
        if (isParkingLotAssigned(parkingLot)) {
            return parkingLot.unPark(ticket);
        }
        else {
            throw new ParkingLotNotFoundException();
        }
    }
}
