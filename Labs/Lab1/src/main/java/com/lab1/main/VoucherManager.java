package com.lab1.main;

import com.lab1.main.vouchers.Voucher;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VoucherManager {
    private final List<Voucher> vouchers;
    private List<Voucher> selectedVouchers;

    public VoucherManager(List<Voucher> vouchers) {
        this.vouchers = vouchers;
        this.selectedVouchers = vouchers;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public List<Voucher> getSelectedVouchers() {
        return selectedVouchers;
    }

    public void sort(Comparator<Voucher> comparator) {
        selectedVouchers.sort(comparator);
    }

    public void filter(Predicate<Voucher> predicate) {
        selectedVouchers = selectedVouchers.stream().filter(predicate).collect(Collectors.toList());
    }

    public void clearFilters() {
        selectedVouchers = vouchers;
    }

    public void showSelectedVouchers() {
        for (Voucher voucher : selectedVouchers) {
            System.out.println(voucher.toString());
        }
    }
}
