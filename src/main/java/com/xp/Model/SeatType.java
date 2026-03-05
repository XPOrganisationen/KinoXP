package com.xp.Model;

public enum SeatType {

    COWBOY_SEATS(0),
    NORMAL(10),
    SOFA_SEATS(25);

    private final double priceAdjustment;

    SeatType (double priceAdjustment) {
        this.priceAdjustment = priceAdjustment;
    }

    public double getPriceAdjustment() {
        return priceAdjustment;
    }

}
