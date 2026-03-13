package com.xp.Model.DTOs;

public class TicketSales {
    private String movieTitle;
    private int ticketsSold;
    private double revenue;

    public TicketSales(String movieTitle, int ticketsSold, double revenue) {
        this.movieTitle = movieTitle;
        this.ticketsSold = ticketsSold;
        this.revenue = revenue;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
