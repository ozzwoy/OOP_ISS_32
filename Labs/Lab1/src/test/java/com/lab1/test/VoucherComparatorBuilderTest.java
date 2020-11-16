package com.lab1.test;

import com.lab1.main.builders.VoucherComparatorBuilder;
import com.lab1.main.vouchers.*;
import com.lab1.main.vouchers.enums.Meals;
import com.lab1.main.vouchers.enums.Transport;
import com.lab1.main.vouchers.exceptions.NegativeNumberOfDaysException;
import com.lab1.main.vouchers.exceptions.NegativePriceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VoucherComparatorBuilderTest {
    static {
        new PropertyConfigurator().doConfigure("log4j.properties", LogManager.getLoggerRepository());
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherComparatorBuilderTest.class);
    private final List<Voucher> vouchers = new ArrayList<>() {
        {
            try {
                add(new CruiseVoucher("Port Elizabeth, South Africa", Transport.PLANE, 21,
                        5000, "Take a chance and try our Trans-Pacific cruise!"));
                add(new ExcursionVoucher("Rivne, Ukraine", Transport.BUS, Meals.ONE_MEAL_A_DAY, 1,
                        30, "Visit famous Rivne Zoo with your family!"));
                add(new ShoppingVoucher("Warsaw, Poland", Transport.TRAIN, Meals.NO_MEALS, 1,
                        100, "Set out for a day-trip to the best boutiques in a capital of Poland!"));
                add(new VacationVoucher("Phuket, Thailand", Transport.PLANE, Meals.THREE_MEALS_A_DAY,
                        14, 3500, "Spend your vacation on exotic beaches of Thailand!"));
                add(new WellnessVoucher("Bern, Switzerland", Transport.BUS, Meals.TWO_MEALS_A_DAY,
                        14, 2000, "Best treatment services here!"));
            } catch (NegativeNumberOfDaysException | NegativePriceException e) {
                LOGGER.error("Error while creating test data.", e);
            }
        }
    };
    private final List<Voucher> vouchersCopy = new ArrayList<>(vouchers.size()) {
        {
            addAll(vouchers);
        }
    };
    private final VoucherComparatorBuilder builder = new VoucherComparatorBuilder();

    @AfterEach
    public void resetBuilder() {
        builder.reset();
    }

    @Test
    public void testOnSortingByDestination() {
        Comparator<Voucher> comparator = builder.byDestination()
                                                .getComparator();
        vouchersCopy.sort(comparator);
        Assertions.assertTrue(vouchersCopy.get(0) == vouchers.get(4) &&
                              vouchersCopy.get(1) == vouchers.get(3) &&
                              vouchersCopy.get(2) == vouchers.get(0) &&
                              vouchersCopy.get(3) == vouchers.get(1) &&
                              vouchersCopy.get(4) == vouchers.get(2));
    }

    @Test
    public void testOnSortingByPriceDescending() {
        Comparator<Voucher> comparator = builder.byPriceDescending()
                                                .getComparator();
        vouchersCopy.sort(comparator);
        Assertions.assertTrue(vouchersCopy.get(0) == vouchers.get(0) &&
                              vouchersCopy.get(1) == vouchers.get(3) &&
                              vouchersCopy.get(2) == vouchers.get(4) &&
                              vouchersCopy.get(3) == vouchers.get(2) &&
                              vouchersCopy.get(4) == vouchers.get(1));
    }

    @Test
    public void testOnSortingByNumOfDaysAndPrice() {
        Comparator<Voucher> comparator = builder.byNumOfDays()
                                                .byPrice()
                                                .getComparator();
        vouchersCopy.sort(comparator);
        Assertions.assertTrue(vouchersCopy.get(0) == vouchers.get(1) &&
                              vouchersCopy.get(1) == vouchers.get(2) &&
                              vouchersCopy.get(2) == vouchers.get(4) &&
                              vouchersCopy.get(3) == vouchers.get(3) &&
                              vouchersCopy.get(4) == vouchers.get(0));
    }

    @Test
    public void testOnSortingByNumOfDaysDescendingAndDestinationDescending() {
        Comparator<Voucher> comparator = builder.byNumOfDaysDescending()
                                                .byDestinationDescending()
                                                .getComparator();
        vouchersCopy.sort(comparator);
        Assertions.assertTrue(vouchersCopy.get(0) == vouchers.get(0) &&
                              vouchersCopy.get(1) == vouchers.get(3) &&
                              vouchersCopy.get(2) == vouchers.get(4) &&
                              vouchersCopy.get(3) == vouchers.get(2) &&
                              vouchersCopy.get(4) == vouchers.get(1));
    }
}
