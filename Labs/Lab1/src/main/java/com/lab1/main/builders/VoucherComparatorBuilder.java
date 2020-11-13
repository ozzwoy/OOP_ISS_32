package com.lab1.main.builders;

import com.lab1.main.vouchers.Voucher;

import java.util.Comparator;

public class VoucherComparatorBuilder {
    private Comparator<Voucher> comparator = null;

    private void add(Comparator<Voucher> newComparator) {
        if (comparator == null) {
            comparator = newComparator;
        } else {
            comparator = comparator.thenComparing(newComparator);
        }
    }

    public Comparator<Voucher> getComparator() {
        return comparator;
    }

    public VoucherComparatorBuilder byDestination() {
        add(Comparator.comparing(Voucher::getDestination, String.CASE_INSENSITIVE_ORDER));
        return this;
    }

    public VoucherComparatorBuilder byDestinationDescending() {
        add(Comparator.comparing(Voucher::getDestination, String.CASE_INSENSITIVE_ORDER).reversed());
        return this;
    }

    public VoucherComparatorBuilder byNumOfDays() {
        add(Comparator.comparing(Voucher::getNumOfDays));
        return this;
    }

    public VoucherComparatorBuilder byNumOfDaysDescending() {
        add(Comparator.comparing(Voucher::getNumOfDays).reversed());
        return this;
    }

    public VoucherComparatorBuilder byPrice() {
        add(Comparator.comparing(Voucher::getPrice));
        return this;
    }

    public VoucherComparatorBuilder byPriceDescending() {
        add(Comparator.comparing(Voucher::getPrice).reversed());
        return this;
    }

    public void reset() {
        comparator = null;
    }
}
