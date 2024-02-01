package org.example.Exception;

public class ParkingLotNotFoundException extends RuntimeException{
    public ParkingLotNotFoundException () {
        super("Parking Lot not found");
    }
}