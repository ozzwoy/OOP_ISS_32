package com.example.linesgame.model;

public enum Color {
    RED("red"),
    ORANGE("orange"),
    YELLOW("yellow"),
    GREEN("green"),
    BLUE("blue"),
    INDIGO("indigo"),
    VIOLET("violet");
    private final String name;

    private Color(String name) {
        this.name = name;
    }
}
