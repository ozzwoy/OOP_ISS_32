package com.lab1.main.builders;

import com.lab1.main.vouchers.Voucher;
import com.lab1.main.vouchers.enums.Meals;
import com.lab1.main.vouchers.enums.Transport;

import java.util.function.Predicate;

public class VoucherPredicateBuilder {
    private Predicate<Voucher> predicate;

    public VoucherPredicateBuilder() {
        this.predicate = voucher -> true;
    }

    public VoucherPredicateBuilder byDestination(String destination) {
        predicate = predicate.and(voucher -> voucher.getDestination().equals(destination));
        return this;
    }

    public VoucherPredicateBuilder byTransport(Transport transport) {
        predicate = predicate.and(voucher -> voucher.getTransport().equals(transport));
        return this;
    }

    public VoucherPredicateBuilder byMeals(Meals meals) {
        predicate = predicate.and(voucher -> voucher.getMeals().equals(meals));
        return this;
    }

    public VoucherPredicateBuilder byNumOfDays(int lowerBound, int upperBound) {
        predicate = predicate.and(voucher -> voucher.getNumOfDays() >= lowerBound &&
                                             voucher.getNumOfDays() <= upperBound);
        return this;
    }

    public VoucherPredicateBuilder byPrice(double lowerBound, double upperBound) {
        predicate = predicate.and(voucher -> voucher.getPrice() >= lowerBound && voucher.getPrice() <= upperBound);
        return this;
    }

    public void reset() {
        predicate = voucher -> true;
    }
}
