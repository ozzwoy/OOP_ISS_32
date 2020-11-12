package com.lab1.main.builders;

import com.lab1.main.vouchers.Voucher;

import java.util.Comparator;
import java.util.function.Function;

public class VoucherComparatorBuilder {
    private Comparator<Voucher> comparator = null;

    private void add(Comparator<Voucher> newComparator) {
        if (comparator == null) {
            comparator = newComparator;
        } else {
            comparator.thenComparing(newComparator);
        }
    }

    public VoucherComparatorBuilder byDestination() {
        add(Comparator.comparing(Voucher::getDestination));
        return this;
    }

    public VoucherComparatorBuilder byNumOfDays() {
        add(Comparator.comparing(Voucher::getNumOfDays));
        return this;
    }

    public VoucherComparatorBuilder byPrice() {
        add(Comparator.comparing(Voucher::getPrice));
        return this;
    }

    public void reset() {
        comparator = null;
    }
}
