package com.lab1.test;

import com.lab1.main.builders.VoucherComparatorBuilder;
import com.lab1.main.vouchers.*;
import com.lab1.main.vouchers.enums.Meals;
import com.lab1.main.vouchers.enums.Transport;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VoucherComparatorBuilderTest {
    private static final List<Voucher> VOUCHERS = new ArrayList<>() {
        {
            add(new CruiseVoucher("Port Elizabeth, South Africa", Transport.PLANE, 21, 5000,
                                  "Take a chance and try our Trans-Pacific cruise!"));
            add(new ExcursionVoucher("Rivne, Ukraine", Transport.BUS, Meals.ONE_MEAL_A_DAY, 1,
                                     30, "Visit famous Rivne Zoo with your family!"));
            add(new ShoppingVoucher("Warsaw, Poland", Transport.TRAIN, Meals.NO_MEALS, 1, 100,
                                    "Set out for a day-trip to the best boutiques in a capital of Poland!"));
            add(new VacationVoucher("Phuket, Thailand", Transport.PLANE, Meals.THREE_MEALS_A_DAY, 14,
                                    3500, "Spend your vacation on exotic beaches of Thailand!"));
            add(new WellnessVoucher("Bern, Switzerland", Transport.BUS, Meals.TWO_MEALS_A_DAY, 14,
                                    2000, "Best treatment services here!"));
        }
    };
    private static final List<Voucher> VOUCHERS_COPY = new ArrayList<>(VOUCHERS.size());
    private static final VoucherComparatorBuilder BUILDER = new VoucherComparatorBuilder();

    @BeforeAll
    public static void copyVouchers() {
        VOUCHERS_COPY.addAll(VOUCHERS);
    }

    @AfterEach
    public void resetBuilder() {
        BUILDER.reset();
    }

    @Test
    public void testSortingByDestination() {
        Comparator<Voucher> comparator = BUILDER.byDestination()
                                                .getComparator();
        VOUCHERS_COPY.sort(comparator);
        Assertions.assertTrue(VOUCHERS_COPY.get(0) == VOUCHERS.get(4) &&
                              VOUCHERS_COPY.get(1) == VOUCHERS.get(3) &&
                              VOUCHERS_COPY.get(2) == VOUCHERS.get(0) &&
                              VOUCHERS_COPY.get(3) == VOUCHERS.get(1) &&
                              VOUCHERS_COPY.get(4) == VOUCHERS.get(2));
    }

    @Test
    public void testSortingByPriceDescending() {
        Comparator<Voucher> comparator = BUILDER.byPriceDescending()
                                                .getComparator();
        VOUCHERS_COPY.sort(comparator);
        Assertions.assertTrue(VOUCHERS_COPY.get(0) == VOUCHERS.get(0) &&
                              VOUCHERS_COPY.get(1) == VOUCHERS.get(3) &&
                              VOUCHERS_COPY.get(2) == VOUCHERS.get(4) &&
                              VOUCHERS_COPY.get(3) == VOUCHERS.get(2) &&
                              VOUCHERS_COPY.get(4) == VOUCHERS.get(1));
    }

    @Test
    public void testSortingByNumOfDaysAndPrice() {
        Comparator<Voucher> comparator = BUILDER.byNumOfDays()
                                                .byPrice()
                                                .getComparator();
        VOUCHERS_COPY.sort(comparator);
        Assertions.assertTrue(VOUCHERS_COPY.get(0) == VOUCHERS.get(1) &&
                              VOUCHERS_COPY.get(1) == VOUCHERS.get(2) &&
                              VOUCHERS_COPY.get(2) == VOUCHERS.get(4) &&
                              VOUCHERS_COPY.get(3) == VOUCHERS.get(3) &&
                              VOUCHERS_COPY.get(4) == VOUCHERS.get(0));
    }

    @Test
    public void testSortingByNumOfDaysDescendingAndDestinationDescending() {
        Comparator<Voucher> comparator = BUILDER.byNumOfDaysDescending()
                                                .byDestinationDescending()
                                                .getComparator();
        VOUCHERS_COPY.sort(comparator);
        Assertions.assertTrue(VOUCHERS_COPY.get(0) == VOUCHERS.get(0) &&
                              VOUCHERS_COPY.get(1) == VOUCHERS.get(3) &&
                              VOUCHERS_COPY.get(2) == VOUCHERS.get(4) &&
                              VOUCHERS_COPY.get(3) == VOUCHERS.get(2) &&
                              VOUCHERS_COPY.get(4) == VOUCHERS.get(1));
    }
}
