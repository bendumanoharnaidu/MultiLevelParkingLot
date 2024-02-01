package org.example;

import org.example.Exception.CarNotFoundException;
import org.example.Exception.ParkingLotIsFullException;

public class ParkingLot implements ParkingArea{
    private ParkingFloor parkingFloors[];
    private int currentFloor;

    ParkingLot (int numberOfParkingFloors) {
        if (numberOfParkingFloors < 1) {
            throw new IllegalArgumentException("Number of parking floor can't be less than 1");
        }
        else {
            this.parkingFloors = new ParkingFloor[numberOfParkingFloors];
        }
    }

    void addParkingFloor (int floor, ParkingFloor parkingFloor) {
        if(floor >= parkingFloors.length) {
            throw  new IllegalArgumentException("Cannot add more floor than capacity");
        }
        parkingFloors[floor]=parkingFloor;
    }

    boolean isFull() {
        for (int i = 0; i < parkingFloors.length; i++) {
            if(!parkingFloors[i].isParkingFloorFull()) {
                return false;
            }
        }
        return true;
    }


    ParkingFloor[] getParkingFloors() {
        return parkingFloors;
    }

    ParkingFloor getCurrentFloor() {
        int min = parkingFloors[0].getCountOfAvailableSlots();
        for (int i = 1; i < parkingFloors.length; i++) {
            if(min < parkingFloors[i].getCountOfAvailableSlots()) {
                min = parkingFloors[i].getCountOfAvailableSlots();
            }
        }
        return parkingFloors[min];
    }

    ParkingFloor getNearestAvailableFloor () {
        for (int i = 0; i < parkingFloors.length; i++) {
            if (!parkingFloors[i].isParkingFloorFull()) {
                return parkingFloors[i];
            }
        }
        throw new ParkingLotIsFullException();
    }

    ParkingFloor getFarthestAvailableFloor () {
        for (int i = parkingFloors.length-1; i >=0; i--) {
            if (!parkingFloors[i].isParkingFloorFull()) {
                return parkingFloors[i];
            }
        }
        throw new ParkingLotIsFullException();
    }

    @Override
    public Ticket park(Car car) {
        try {
            ParkingFloor floor = getNearestAvailableFloor();
            return floor.park(car);
        }
        catch (ParkingLotIsFullException e) {
            throw new ParkingLotIsFullException();
        }
    }


    Car unPark(Ticket ticket) {
        try {
            return parkingFloors[ticket.getParkingFloor()].unpark(ticket.getParkingSlot(),ticket.getRegistrationNumber());
        }
        catch (CarNotFoundException e) {
            System.out.println(e.getMessage());
        }
        throw new CarNotFoundException();
    }
}