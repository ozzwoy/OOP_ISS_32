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

    public Predicate<Voucher> getPredicate() {
        return predicate;
    }

    public VoucherPredicateBuilder byDestination(String... destinations) {
        Predicate<Voucher> subpredicate = voucher -> false;
        for (String current : destinations) {
            subpredicate = subpredicate.or(voucher -> voucher.getDestination().equals(current));
        }
        predicate = predicate.and(subpredicate);
        return this;
    }

    public VoucherPredicateBuilder byTransport(Transport... transports) {
        Predicate<Voucher> subpredicate = voucher -> false;
        for (Transport current : transports) {
            subpredicate = subpredicate.or(voucher -> voucher.getTransport().equals(current));
        }
        predicate = predicate.and(subpredicate);
        return this;
    }

    public VoucherPredicateBuilder byMeals(Meals... meals) {
        Predicate<Voucher> subpredicate = voucher -> false;
        for (Meals current : meals) {
            subpredicate = subpredicate.or(voucher -> voucher.getMeals().equals(current));
        }
        predicate = predicate.and(subpredicate);
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
