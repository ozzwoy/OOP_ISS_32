package com.main.card;

public enum Theme {
    CITY_SCENERY("city scenery"),
    NATURE("nature"),
    PEOPLE("people"),
    RELIGION("religion"),
    SPORT("sport"),
    ARCHITECTURE("architecture"),
    OTHER("other");
    private final String value;

    Theme(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
