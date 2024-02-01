package org.example;
import org.example.Exception.CarNotFoundException;
import org.example.Exception.ParkingLotIsFullException;

public class ParkingFloor implements ParkingArea {
    private int floorId;
    private Slot slots[];
    private int availableSlots;

    public ParkingFloor(int floorId, Slot[] slots) {
        this.floorId = floorId;
        if(slots.length < 1) {
            throw new IllegalArgumentException("Number of slots shouldn't be less than 1");
        }
        this.slots = slots;
        this.availableSlots = numberOfAvailableSlots(slots);
    }

    int numberOfAvailableSlots(Slot[] slots) {
        int count = 0;
        for (Slot slot : slots) {
            if (slot.getStatus() == SlotStatus.EMPTY) {
                count++;
            }
        }
        return count;
    }

    int nearestSlotAvailable() {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i].getStatus() == SlotStatus.EMPTY) {
                return i;
            }
        }
        throw new ParkingLotIsFullException();
    }

    int farthestSlotAvailable() {
        for (int i = slots.length-1; i >= 0; i++) {
            if (slots[i].getStatus() == SlotStatus.EMPTY) {
                return i;
            }
        }
        throw new ParkingLotIsFullException();
    }

    int getCountOfAvailableSlots() {
        return availableSlots;
    }

    boolean isParkingFloorFull() {
        return (slots.length - availableSlots) == slots.length;
    }

    @Override
    public Ticket park(Car car) {
        if (isCarPresent(car.getRegistrationNumber())) {
            throw new IllegalArgumentException("Car is already parked");
        }
        try {
            int slotNumber = nearestSlotAvailable();
            this.slots[slotNumber] = new Slot(SlotStatus.FULL,car);
            availableSlots--;
            return new Ticket(floorId, slotNumber, car.getRegistrationNumber());
        }
        catch (ParkingLotIsFullException e) {
            throw new ParkingLotIsFullException();
        }

    }

    public Ticket park(Car car, int slotNumber) {
        if (isCarPresent(car.getRegistrationNumber())) {
            throw new IllegalArgumentException("Car is already parked");
        }
        this.slots[slotNumber] = new Slot(SlotStatus.FULL,car);
        availableSlots--;
        return new Ticket(floorId, slotNumber, car.getRegistrationNumber());
    }


    boolean isCarPresent(String registrationNumber){
        for (Slot slot : slots) {
            if (slot.getCar() == null) {
                continue;
            }
            else if (slot.getCar().getRegistrationNumber().equals(registrationNumber)) {
                return true;
            }
        }
        return false;
    }

    Car unpark (int slotNumber,String registrationNumber) {
        if (slots[slotNumber].getCar() == null) {
            throw new CarNotFoundException();
        }
        if(slots[slotNumber].getCar().getRegistrationNumber().equals(registrationNumber)) {
            Car car = slots[slotNumber].getCar();
            slots[slotNumber] = new Slot(SlotStatus.EMPTY);
            availableSlots++;
            return car;
        }
        throw new CarNotFoundException();
    }
}