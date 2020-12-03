package com.main.card;

import java.util.Objects;

public class Card implements Comparable<Card> {
    private String id;
    private Theme theme;
    private Type type;
    private String country;
    private int year;
    private String author;
    private Valuable valuable;

    public Card() {
        id = "AA00000";
        theme = Theme.OTHER;
        type = new Type();
        country = "";
        year = 0;
        author = null;
        valuable = null;
    }

    public Card(String id, Theme theme, Type type, String country, int year, String author, Valuable valuable) {
        this.id = id;
        this.theme = theme;
        this.type = type;
        this.country = country;
        this.year = year;
        this.author = author;
        this.valuable = valuable;
    }

    public String getId() {
        return id;
    }

    public Theme getTheme() {
        return theme;
    }

    public Type getType() {
        return type;
    }

    public String getCountry() {
        return country;
    }

    public int getYear() {
        return year;
    }

    public String getAuthor() {
        return author;
    }

    public Valuable getValuable() {
        return valuable;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setValuable(Valuable valuable) {
        this.valuable = valuable;
    }

    @Override
    public int compareTo(Card o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return year == card.year &&
                id.equals(card.id) &&
                theme == card.theme &&
                type.equals(card.type) &&
                country.equals(card.country) &&
                Objects.equals(author, card.author) &&
                valuable == card.valuable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, theme, type, country, year, author, valuable);
    }
}
