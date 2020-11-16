package com.lab1.main.vouchers;

import com.lab1.main.vouchers.enums.Meals;
import com.lab1.main.vouchers.enums.Transport;
import com.lab1.main.vouchers.exceptions.NegativeNumberOfDaysException;
import com.lab1.main.vouchers.exceptions.NegativePriceException;

public abstract class Voucher {
    protected String destination;
    protected Transport transport;
    protected Meals meals;
    protected int numOfDays;
    protected double price;
    protected String description;

    Voucher(String destination, Transport transport, Meals meals, int numOfDays, double price, String description)
            throws NegativeNumberOfDaysException, NegativePriceException {

        if (numOfDays < 1) {
            throw new NegativeNumberOfDaysException("Number of days must be a positive integer. Provided: " + numOfDays
                                                    + ".");
        }
        if (price < 0) {
            throw  new NegativePriceException("Price cannot be negative. Provided: " + price + ".");
        }

        this.destination = destination;
        this.transport = transport;
        this.meals = meals;
        this.numOfDays = numOfDays;
        this.price = price;
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public Transport getTransport() {
        return transport;
    }

    public Meals getMeals() {
        return meals;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Destination: " + destination + ".\n" +
                "Transport: " + transport.toString() + ".\n" +
                "Meals: " + meals.toString() + ".\n" +
                "Number of days: " + numOfDays + ".\n" +
                "Price: $" + price + ".\n" +
                description + "\n";
    }
}