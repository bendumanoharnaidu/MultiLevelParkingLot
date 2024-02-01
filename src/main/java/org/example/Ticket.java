package org.example;

import java.util.Objects;

public class Ticket {
    private int parkingFloor;
    private int parkingSlot;

    private String registrationNumber;

    public Ticket(int parkingFloor, int parkingSlot, String registrationNumber) {
        this.parkingFloor = parkingFloor;
        this.parkingSlot = parkingSlot;
        this.registrationNumber = registrationNumber;
    }

    public int getParkingFloor() {
        return parkingFloor;
    }

    public int getParkingSlot() {
        return parkingSlot;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) obj;
        return parkingFloor == ticket.parkingFloor && parkingSlot == ticket.parkingSlot && Objects.equals(registrationNumber, ticket.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingFloor, parkingSlot, registrationNumber);
    }
}