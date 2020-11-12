package com.lab1.main.vouchers.enums;

public enum Meals {
    NO_MEALS {
        @Override
        public String toString() {
            return "self-catering";
        }
    },
    ONE_MEAL_A_DAY {
        @Override
        public String toString() {
            return "one meal a day provided";
        }
    },
    TWO_MEALS_A_DAY {
        @Override
        public String toString() {
            return "two meals a day provided";
        }
    },
    THREE_MEALS_A_DAY {
        @Override
        public String toString() {
            return "three meals a day provided";
        }
    },
    NO_LIMIT {
        @Override
        public String toString() {
            return "free food 24/7";
        }
    }
}
