package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTest {
    @Test
    public void createNewCarWithInvalidNumber_GivesException() {
        try {
            Car car = new Car("12MH1212","Black");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Car number format is not valid",e.getMessage());
        }

    }

    @Test
    public void testCreateNewCarWithInvalidNumber_ExpectException() {
        assertThrows(IllegalArgumentException.class, () -> new Car("007","Black"));
    }

    @Test
    public void testCreateNewCarWithValidNumber_ExpectCarObject (){
        Car car = new Car("MH12AT3433","Black");
        assertNotNull(car,"Car should not be null");
    }

}