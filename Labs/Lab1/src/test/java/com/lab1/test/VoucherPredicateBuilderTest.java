package com.lab1.test;

import com.lab1.main.builders.VoucherPredicateBuilder;
import com.lab1.main.vouchers.*;
import com.lab1.main.vouchers.enums.Meals;
import com.lab1.main.vouchers.enums.Transport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class VoucherPredicateBuilderTest {
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
    private static final VoucherPredicateBuilder BUILDER = new VoucherPredicateBuilder();

    @AfterEach
    public void resetBuilder() {
        BUILDER.reset();
    }

    @Test
    public void testOnFilteringWithEmptyPredicate() {
        Predicate<Voucher> predicate = BUILDER.byDestination().getPredicate();
        List<Voucher> result = VOUCHERS.stream().filter(predicate).collect(Collectors.toList());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testOnFilteringByDestination() {
        Predicate<Voucher> predicate = BUILDER.byDestination("Port Elizabeth, South Africa", "Rivne, Ukraine")
                                              .getPredicate();
        List<Voucher> result = VOUCHERS.stream().filter(predicate).collect(Collectors.toList());
        Assertions.assertTrue(result.size() == 2 && result.get(0) == VOUCHERS.get(0) &&
                              result.get(1) == VOUCHERS.get(1));
    }

    @Test
    public void testOnFilteringByTransportAndMeals() {
        Predicate<Voucher> predicate = BUILDER.byTransport(Transport.PLANE, Transport.TRAIN)
                                              .byMeals(Meals.NO_LIMIT, Meals.THREE_MEALS_A_DAY)
                                              .getPredicate();
        List<Voucher> result = VOUCHERS.stream().filter(predicate).collect(Collectors.toList());
        Assertions.assertTrue(result.size() == 2 && result.get(0) == VOUCHERS.get(0) &&
                result.get(1) == VOUCHERS.get(3));
    }

    @Test
    public void testOnFilteringByNumOfDaysAndPrice() {
        Predicate<Voucher> predicate = BUILDER.byNumOfDays(7, 14)
                                              .byPrice(0, 2000)
                                              .getPredicate();
        List<Voucher> result = VOUCHERS.stream().filter(predicate).collect(Collectors.toList());
        Assertions.assertTrue(result.size() == 1 && result.get(0) == VOUCHERS.get(4));
    }
}
