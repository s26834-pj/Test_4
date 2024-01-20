package org.example.rentalcar_3;

import java.time.LocalDate;

public class Rental {
    private User user;
    private Car car;
    private LocalDate rentalStart;
    private LocalDate rentalEnd;
    private double price;

    public Rental(User user, Car car, LocalDate rentalStart, LocalDate rentalEnd, double price) {
        this.user = user;
        this.car = car;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDate getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(LocalDate rentalStart) {
        this.rentalStart = rentalStart;
    }

    public LocalDate getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(LocalDate rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "user=" + user +
                ", car=" + car +
                ", rentalStart=" + rentalStart +
                ", rentalEnd=" + rentalEnd +
                ", price=" + price +
                '}';
    }
}

