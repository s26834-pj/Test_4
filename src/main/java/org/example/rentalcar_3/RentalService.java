package org.example.rentalcar_3;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;


@Service
public class RentalService {
    private final CarStorage carStorage;
    private final RentalStorage rentalStorage;

    public RentalService(CarStorage carStorage, RentalStorage rentalStorage) {
        this.carStorage = carStorage;
        this.rentalStorage = rentalStorage;
    }
    public Rental rent(int idUser, String vinNumber, LocalDate rentalStart, LocalDate rentalEnd) {
        if (!isAvailable(vinNumber, rentalStart, rentalEnd)) {
            return null;
        }
        Optional<Car> carOptional = Optional.empty();
        for (Car car : carStorage.getCars()) {
            if (car.getVinNumber().equals(vinNumber)) {
                carOptional = Optional.of(car);
                break;
            }
        }
        //zmienione
        //double estimatePrice = estimatePrice(carOptional.get().getVinNumber(), rentalStart, rentalEnd);
        //
        Rental rental = new Rental(new User(idUser), carOptional.get(), rentalStart, rentalEnd, 500);
        rentalStorage.addRental(rental);
        return rental;
    }

    public boolean isAvailable(String vinNumber, LocalDate rentalStart, LocalDate rentalEnd) {
        boolean carExist = false;
        for (Car car : carStorage.getCars()) {
            if (car.getVinNumber().equals(vinNumber)) {
                carExist = true;
                break;
            }
        }
        if (!carExist) {
            return false;
        }
        if (rentalStorage.getRentals().isEmpty()) {
            return true;
        }

        Optional<Rental> rentalOptional = rentalStorage.getRentals().stream()
                .filter(rental -> rental.getCar().getVinNumber().equals(vinNumber))
                .findFirst();

        if (rentalOptional.isPresent()) {
            Rental rental = rentalOptional.get();
            if (isOverlappingDate(rentalStart, rentalEnd, rental)) {
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    public Optional<Car> carExist(String vinNumber) {
        for (Car car : carStorage.getCars()) {
            if (car.getVinNumber().equals(vinNumber)) {
                return Optional.of(car);
            }
        }
        return Optional.empty();
    }
    public double estimatePrice(String vinNumber, LocalDate rentalStart, LocalDate rentalEnd) {
        Car car = carExist(vinNumber).orElseThrow();
        // stała cena * ilość dni * klasa samochowy
        long daysBetween = Duration.between(rentalStart.atStartOfDay(), rentalEnd.atStartOfDay()).toDays();
        double estimateValue = 500 * daysBetween * car.getCarType().getMultiplier();
        return estimateValue;
    }
    private boolean isOverlappingDate(LocalDate rentalStart, LocalDate rentalEnd, Rental rental) {
        boolean isEndDateBeforeRentalStart = rentalEnd.isBefore(rental.getRentalStart());
        boolean isStartDateAfterRentalEnd = rentalStart.isAfter(rental.getRentalEnd());
        return !(isEndDateBeforeRentalStart || isStartDateAfterRentalEnd);
    }




}