package com.xp.Model;

public enum SeatType {

    COWBOY_seats(0),
    NORMAL(10),
    SOFA_seats(25);

    private final double priceAdjustment;

    SeatType (double priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public double getPriceAdjustment() {
        return priceAdjustment;
    }

}
