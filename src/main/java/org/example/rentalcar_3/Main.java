package org.example.rentalcar_3;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        CarStorage carStorage = CarStorage.getInstance();
        RentalStorage rentalStorage = RentalStorage.getInstance();
        Car car1 = new Car("VW", "aaa", "111", Type.ECONOMY);
        Car car2 = new Car("VW", "bbb", "222", Type.STANDARD);
        Car car3 = new Car("VW", "ccc", "333", Type.PREMIUM);
        Car car4 = new Car("VW", "ddd", "444", Type.ECONOMY);
        carStorage.addCar(car1);
        carStorage.addCar(car2);
        carStorage.addCar(car3);
        carStorage.addCar(car4);

        RentalService rentalService = new RentalService(carStorage, rentalStorage);
        Rental rental1 = rentalService.rent(1, "111", LocalDate.now().minusDays(3), LocalDate.now());
        System.out.println(rental1);
        boolean isAvailable1 = rentalService.isAvailable("111", LocalDate.now().plusDays(1),LocalDate.now().plusDays(2));
        System.out.println(isAvailable1);
        double estimatePrice = rentalService.estimatePrice("111", LocalDate.now().plusDays(1), LocalDate.now().plusDays(2));
        System.out.println(estimatePrice);
    }
}