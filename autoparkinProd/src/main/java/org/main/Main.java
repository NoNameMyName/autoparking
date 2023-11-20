package org.main;

import org.backend.Car;
import org.backend.ParkingSystem;
import org.frontend.CarParkingApp;
import org.frontend.*;

import javax.swing.*;
import java.time.LocalDateTime;

import static org.main.Constants.TOTALSPOTS;

public class Main {
    public static void main(String[] args) {
        ParkingSystem parkingSystem = new ParkingSystem();
        Car car1 = new Car("Smykov", "0988738471",
                "Ford", "Fusion", "КЕ0708НТ");
        Car car2 = new Car("Savluk", "0991599936",
                "Mazda", "Miata", "АЕ6942КІ");
        LocalDateTime testdate = LocalDateTime.of(2023, 9, 23, 16, 15, 20);
        System.out.println(parkingSystem.parkCarByDateTime(car1, testdate));
        System.out.println(parkingSystem.parkCar(car2));

        SwingUtilities.invokeLater(() -> {
            try {
                new CarParkingApp(parkingSystem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}