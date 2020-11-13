package com.lab1.main.vouchers;

import com.lab1.main.vouchers.enums.Meals;
import com.lab1.main.vouchers.enums.Transport;

public class WellnessVoucher extends Voucher {

    public WellnessVoucher(String destination, Transport transport, Meals meals, int numOfDays, double price,
                    String description) {
        super(destination, transport, meals, numOfDays, price, description);
    }

    @Override
    public String toString() {
        return "-----WELLNESS VOUCHER-----\n" + super.toString() + "--------------------------\n";
    }
}
