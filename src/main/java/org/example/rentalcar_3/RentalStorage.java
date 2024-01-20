package org.example.rentalcar_3;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentalStorage {
    private static RentalStorage instance;
    private List<Rental> rentals = new ArrayList<>();

    public RentalStorage(){

    }
    public void addRental(Rental rental) {
        rentals.add(rental);
    }
    public static RentalStorage getInstance(){
        if(instance == null){
            instance = new RentalStorage();
        }
        return instance;
    }
    public List<Rental> getRentals(){
        return rentals;
    }

}