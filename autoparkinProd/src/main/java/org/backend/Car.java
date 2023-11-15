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

    public String getCarNumberFromClassCar() {
        return carNumber;
    }
    public String getCarNumber() {
        return carNumber;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public String getPersonTelephoneNumber() {
        return personTelephoneNumber;
    }

    public String getCarMark() {
        return carMark;
    }

    public String getCarModel() {
        return carModel;
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
        this.carMark = carMark;
        this.carModel = carModel;
        this.carNumber = carNumber;
    }
    public Car(){}

    @Override
    public String toString() {
        return "Car{" +
                ", carNumber='" + carNumber + '\'' +
                "personSurname='" + personSurname + '\'' +
                ", personTelephoneNumber='" + personTelephoneNumber + '\'' +
                ", carMark='" + carMark + '\'' +
                ", carModel='" + carModel + '\'' +
                '}';
    }
}
