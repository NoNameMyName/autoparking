package org.backend;

import org.main.Constants;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingSpot {
    private Car car;
    private LocalDateTime parkingData;

    public LocalDateTime getParkingData() {
        return parkingData;
    }
    public void setParkingData(LocalDateTime parkingData) {
        this.parkingData = parkingData;
    }

    public ParkingSpot() {
        car = null;
        parkingData = null;
    }

    public Car isCarStayed() {
        return car;
    }
    public void setCarByDateTime(Car car,LocalDateTime parkingData) {
        this.car = car;
        this.parkingData = parkingData;
    }

    public void setCar(Car car) {
        this.car = car;
        this.parkingData = LocalDateTime.now();
    }

    public String getCarNumber() {
        return car.getCarNumber();
    }

    public Car getCar() {
        return car;
    }

    public void clearSpot(){
        this.car = null;
        this.parkingData = null;
    }
}
