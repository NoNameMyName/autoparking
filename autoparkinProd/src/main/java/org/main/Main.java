package org.main;

import org.backend.Car;
import org.backend.ParkingSystem;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ParkingSystem parkingSystem = new ParkingSystem(20);
        Car car = new Car("Smykov", "0988738471", "KE0708KC",
                "Ford", "Fusion");
        LocalDateTime testdate = LocalDateTime.of(2023, 9, 23, 16, 15, 20);
        parkingSystem.parkCarByDateTime(car, testdate);
        System.out.println(parkingSystem.findByCarNumberAndPay("KE0708KC"));
    }
}