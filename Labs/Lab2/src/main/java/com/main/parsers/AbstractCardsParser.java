package com.main.parsers;

import com.main.card.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCardsParser {
    protected List<Card> cards;

    public AbstractCardsParser() {
        cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public abstract void parse(String fullFileName);
}
