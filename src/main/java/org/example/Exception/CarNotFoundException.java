package org.example.Exception;

public class CarNotFoundException extends RuntimeException{

    public CarNotFoundException() {
        super("Car not found");
    }
}