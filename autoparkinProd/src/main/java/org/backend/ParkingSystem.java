package org.backend;

import org.main.Constants;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static org.main.Constants.TOTALSPOTS;

public class ParkingSystem {

    ArrayList<ParkingSpot> parkingSpots = new ArrayList<>(TOTALSPOTS);

    public ParkingSystem() {
        for (int i = 1; i <= TOTALSPOTS; i++) {
            parkingSpots.add(new ParkingSpot(i));
        }
    }

    public ParkingSpot getSpot(int idOfSpot){
        for (ParkingSpot el: parkingSpots){
            if (el.isCarStayed() != null && el.getId() == idOfSpot){
                return el;
            }
        }
        return null;
    }

    public int parkCar(Car car){
        for (ParkingSpot el: parkingSpots){
            if (el.isCarStayed() == null){
                el.setCar(car);
                return el.getId();
            }
        }
        return -1;
    }

    public int parkCarByDateTime(Car car, LocalDateTime parkingData){
        for (ParkingSpot el: parkingSpots){
            if (el.isCarStayed() == null){
                el.setCar(car);
                el.setParkingData(parkingData);
                return el.getId();
            }
        }
        return -1;
    }

    public ParkingSpot findSpot(String carNumber){
        for (ParkingSpot el: parkingSpots) {
            if (carNumber.equals(el.getCarNumber())) {
                return el;
            }
        }
        return null;
    }

    public double findBySpotIdAndPay(int idSpot){
        double bill;
        for (ParkingSpot el: parkingSpots){
            if (el.getId() == idSpot && el.getCar() != null){
                LocalDateTime parkingData = el.getParkingData();
                bill = payment(parkingData);
                el.clearSpot();
                return bill;
            }
        }
        return -1;
    }

    public double findByCarNumberAndPay(String carNumber){
        double bill;
        for (ParkingSpot el: parkingSpots){
            if (el.getCar() != null && el.getCar().getCarNumber().equals(carNumber)){
                LocalDateTime parkingData = el.getParkingData();
                bill = payment(parkingData);
                el.clearSpot();
                return bill;
            }
        }
        return 0;
    }

    public double payment(LocalDateTime parkingData) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(parkingData, currentDateTime);
        if (duration.toMinutes() <= 5){
            return -1;
        }
        else if (duration.toHours() > Constants.STARTDAILYPAYMANT){
            if (duration.toDays() > 1){
                return (duration.toDays() + 1) * Constants.DAILYPAYMANT;
            }
            else return Constants.DAILYPAYMANT;
        }
        else if (parkingData.getHour() > Constants.STARTNIGHTTIME || parkingData.getHour() < Constants.ENDNIGHTTIME){
            if (currentDateTime.getHour() <= 7){
                return Constants.NIGHTPAYMANT;
            }
            else {
                return (duration.toHours() - ((Constants.HOURSINDAY - parkingData.getHour()) +
                        Constants.ENDNIGHTTIME )) * Constants.DAYPAYMANT + Constants.NIGHTPAYMANT;
            }
        }
        else {
            if (currentDateTime.getHour() < Constants.STARTNIGHTTIME || currentDateTime.getHour() > Constants.ENDNIGHTTIME) {
                return duration.toHours() * Constants.DAYPAYMANT;
            }
            else {
                return Constants.STARTNIGHTTIME - parkingData.getHour() * Constants.DAYPAYMANT + Constants.NIGHTPAYMANT;
            }
        }
    }
}
