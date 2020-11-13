package com.lab1.main.vouchers;

import com.lab1.main.vouchers.enums.Meals;
import com.lab1.main.vouchers.enums.Transport;

public class CruiseVoucher extends Voucher {

    public CruiseVoucher(String destination, Transport transport, int numOfDays, double price, String description) {
        super(destination, transport, Meals.NO_LIMIT, numOfDays, price, description);
    }

    @Override
    public String toString() {
        return "------CRUISE VOUCHER------\n" + super.toString() + "--------------------------\n";
    }
}
