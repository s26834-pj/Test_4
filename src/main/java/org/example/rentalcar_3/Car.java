package org.example.rentalcar_3;

public class Car {
    private String marka;
    private String model;
    private String vinNumber;
    private Type carType;


    public Car(String marka, String model, String vinNumber, Type carType) {
        this.marka = marka;
        this.model = model;
        this.vinNumber = vinNumber;
        this.carType = carType;

    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public Type getCarType() {
        return carType;
    }

    public void setCarType(Type carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "marka='" + marka + '\'' +
                ", model='" + model + '\'' +
                ", vinNumber='" + vinNumber + '\'' +
                ", carType=" + carType +
                '}';

    }

}
