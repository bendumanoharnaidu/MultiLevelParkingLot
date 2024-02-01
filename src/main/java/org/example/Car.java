package org.example;

import java.util.Objects;

public class Car {
    private String registrationNumber;
    private String color;

    public Car(String registrationNumber, String color) {
        if(!validRegistrationNumber(registrationNumber)) {
            throw new IllegalArgumentException("Car number format is not valid");
        }
        else this.registrationNumber = registrationNumber;
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Car car = (Car) obj;
        return Objects.equals(registrationNumber, car.registrationNumber) && Objects.equals(color, car.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber, color);
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    private boolean validRegistrationNumber(String registrationNumber) {
        String carNumber = "^[A-Z]{2}[0-9]{1,2}[A-Z]{1,2}[0-9]{4}$";
        return registrationNumber.matches(carNumber);
    }
}