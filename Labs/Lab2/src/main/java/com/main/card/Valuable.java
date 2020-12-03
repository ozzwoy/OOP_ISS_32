package com.main.card;

public enum Valuable {
    HISTORICAL("historical"),
    COLLECTIBLE("collectible"),
    THEMATIC("thematic");
    private final String value;

    Valuable(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
