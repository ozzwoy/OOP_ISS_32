package com.lab1.main.vouchers;

import com.lab1.main.vouchers.enums.Meals;
import com.lab1.main.vouchers.enums.Transport;
import com.lab1.main.vouchers.exceptions.NegativeNumberOfDaysException;
import com.lab1.main.vouchers.exceptions.NegativePriceException;

public class ExcursionVoucher extends Voucher {

    public ExcursionVoucher(String destination, Transport transport, Meals meals, int numOfDays, double price,
                     String description) throws NegativeNumberOfDaysException, NegativePriceException {
        super(destination, transport, meals, numOfDays, price, description);
    }

    @Override
    public String toString() {
        return "-----EXCURSION VOUCHER-----\n" + super.toString() + "---------------------------\n";
    }
}
