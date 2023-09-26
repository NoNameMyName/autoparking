package org.backend;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Car {

    private String personSurname;
    private String personTelephoneNumber;

    private String carNumber;
    private String carMark;
    private String carModel;

    public String getCarNumber() {
        return carNumber;
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

    public Car(String personSurname, String personTelephoneNumber, String carNumber, String carMark, String carModel) {
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
