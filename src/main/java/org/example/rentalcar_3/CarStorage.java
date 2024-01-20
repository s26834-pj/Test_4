package org.example.rentalcar_3;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CarStorage {
    private static CarStorage instatce;
    private List<Car> cars = new ArrayList<>();

    public CarStorage() {

    }

    public static CarStorage getInstance() {
        if (instatce == null) {
            instatce = new CarStorage();
        }
        return instatce;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }
}