package org.backend;

import org.main.Constants;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class ParkingSystem {

    Map<Integer, ParkingSpot> parkingSpots = new HashMap<>();
    public ParkingSystem(int totalSpots) {
        for (int i = 1; i <= totalSpots; i++) {
            parkingSpots.put(i, new ParkingSpot());
        }
    }

    public int parkCar(Car car){
        for (Map.Entry<Integer, ParkingSpot> entry: parkingSpots.entrySet()){
            ParkingSpot spot = entry.getValue();
            if (spot.isCarStayed() == null){
                spot.setCar(car);
                return entry.getKey();
            }
        }
        return -1;
    }
    public int parkCarByDateTime(Car car, LocalDateTime parkingData){
        for (Map.Entry<Integer, ParkingSpot> entry: parkingSpots.entrySet()){
            ParkingSpot spot = entry.getValue();
            if (spot.isCarStayed() == null){
                spot.setCarByDateTime(car, parkingData);
                return entry.getKey();
            }
        }
        return -1;
    }

    public ParkingSpot findSpot(String carNumber){
        for (Map.Entry<Integer, ParkingSpot> entry: parkingSpots.entrySet()) {
            ParkingSpot spot = entry.getValue();
            if (carNumber.equals(spot.getCarNumber())) {
                return spot;
            }
        }
        return null;
    }

    public double findByCarNumberAndPay(String carNumber){
        double bill;
        ParkingSpot spot = findSpot(carNumber);
        if (spot != null){
            LocalDateTime parkingData = spot.getParkingData();
            bill = payment(parkingData);
            spot.clearSpot();
            return bill;
        }
        else
            return -1;
    }

    public double payment(LocalDateTime parkingData) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        Duration duration = Duration.between(parkingData, currentDateTime);
        if (duration.toHours() > Constants.STARTDAILYPAYMANT){
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
