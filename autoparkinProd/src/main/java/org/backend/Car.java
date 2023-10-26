package org.backend;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Car {
    
    private String personSurname;
    private String personTelephoneNumber;
    private String carMark;
    private String carModel;
    private String carNumber;
    private LocalDateTime parkingDateTime;
    private int parkingSpot;

    public String getPersonSurname() {
        return personSurname;
    }

    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }

    public String getPersonTelephoneNumber() {
        return personTelephoneNumber;
    }

    public void setPersonTelephoneNumber(String personTelephoneNumber) {
        this.personTelephoneNumber = personTelephoneNumber;
    }

    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public LocalDateTime getParkingDateTime() {
        return parkingDateTime;
    }

    public void setParkingDateTime(LocalDateTime parkingDateTime) {
        this.parkingDateTime = parkingDateTime;
    }

    public int getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(int parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carNumber, car.carNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carNumber);
    }

    public Car(String personSurname, String personTelephoneNumber, String carMark, String carModel, String carNumber) {
        this.personSurname = personSurname;
        this.personTelephoneNumber = personTelephoneNumber;
        this.carNumber = carNumber;
        this.carMark = carMark;
        this.carModel = carModel;
    }
    public Car(){}

    @Override
    public String toString() {
        return "Car{" +
                "personSurname='" + personSurname + '\'' +
                ", personTelephoneNumber='" + personTelephoneNumber + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", carMark='" + carMark + '\'' +
                ", carModel='" + carModel + '\'' +
                '}';
    }
}
