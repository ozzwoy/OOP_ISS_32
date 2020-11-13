package com.lab1.main.vouchers.enums;

public enum Transport {
    BUS {
        @Override
        public String toString() {
            return "bus";
        }
    },
    PLANE {
        @Override
        public String toString() {
            return "plane";
        }
    },
    TRAIN {
        @Override
        public String toString() {
            return "train";
        }
    }
}
