package com.lab1.test;

import com.lab1.main.vouchers.*;
import com.lab1.main.vouchers.enums.Meals;
import com.lab1.main.vouchers.enums.Transport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VoucherTest {

    @Test
    public void testOnNegativeNumOfDays() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CruiseVoucher("Some destination",
                        Transport.BUS, 0, 300, "Some description"),
                "Number of days must be a positive integer. Provided: 0.");
    }

    @Test
    public void testOnNegativePrice() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new CruiseVoucher("Some destination",
                        Transport.BUS, 1, -300, "Some description"),
                "Price cannot be negative. Provided: -300.");
    }

    @Test
    public void testCommonDescription() {
        Voucher voucher = new CruiseVoucher("Some destination", Transport.PLANE, 2, 300,
                                            "Some description.");
        String description = voucher.toString();
        Assertions.assertTrue(description.contains("Destination: " + voucher.getDestination() + ".") &&
                              description.contains("Transport: " + voucher.getTransport().toString() + ".") &&
                              description.contains("Meals: " + voucher.getMeals().toString() + ".") &&
                              description.contains("Number of days: " + voucher.getNumOfDays() + ".\n") &&
                              description.contains(voucher.getDescription()));
    }

    @Test
    public void testCruiseVoucherToString() {
        Voucher voucher = new CruiseVoucher("Some destination", Transport.PLANE, 2, 300,
                "Some description.");
        String description = voucher.toString().toLowerCase();
        Assertions.assertTrue(description.contains("cruise voucher"));
    }

    @Test
    public void testExcursionVoucherToString() {
        Voucher voucher = new ExcursionVoucher("Some destination", Transport.PLANE, Meals.ONE_MEAL_A_DAY,
                                               2, 300, "Some description.");
        String description = voucher.toString().toLowerCase();
        Assertions.assertTrue(description.contains("excursion voucher"));
    }

    @Test
    public void testShoppingVoucherToString() {
        Voucher voucher = new ShoppingVoucher("Some destination", Transport.PLANE, Meals.ONE_MEAL_A_DAY,
                                              2, 300, "Some description.");
        String description = voucher.toString().toLowerCase();
        Assertions.assertTrue(description.contains("shopping voucher"));
    }

    @Test
    public void testVacationVoucherToString() {
        Voucher voucher = new VacationVoucher("Some destination", Transport.PLANE, Meals.ONE_MEAL_A_DAY,
                                              2, 300, "Some description.");
        String description = voucher.toString().toLowerCase();
        Assertions.assertTrue(description.contains("vacation voucher"));
    }

    @Test
    public void testWellnessVoucherToString() {
        Voucher voucher = new WellnessVoucher("Some destination", Transport.PLANE, Meals.ONE_MEAL_A_DAY,
                                              2, 300, "Some description.");
        String description = voucher.toString().toLowerCase();
        Assertions.assertTrue(description.contains("wellness voucher"));
    }
}
