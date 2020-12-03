package com.main.card;

public enum Kind {
    INVITATION("invitation"),
    ADVERTISEMENT("advertisement"),
    SIMPLE("simple");
    private final String value;

    Kind(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
