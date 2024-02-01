package org.example.Exception;

public class ParkingLotIsFullException extends RuntimeException{
    public ParkingLotIsFullException() {
        super("Parking Lot is Full");
    }
}