package com.test;

import com.main.CardsXMLBuilder;
import com.main.card.*;
import com.main.parsers.AbstractCardsParser;
import com.main.parsers.CardsParserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardsParsersTest {
    private final CardsParserFactory factory = new CardsParserFactory();
    private AbstractCardsParser parser;
    private static final String FILE_NAME = "src/test/resources/cards.xml";
    private static final List<Card> initialList = new ArrayList<>() {
        {
            add(new Card("RG90790", Theme.SPORT, new Type(true, Kind.INVITATION), "Berlin",
                    1995, null, Valuable.COLLECTIBLE));
            add(new Card("KU67234", Theme.CITY_SCENERY, new Type(true, Kind.ADVERTISEMENT), "USA",
                    1986, null, null));
            add(new Card("NP37953", Theme.NATURE, new Type(false, Kind.SIMPLE), "Japan", 2007,
                    "O. Kobayashi", Valuable.THEMATIC));
        }
    };

    @BeforeAll
    public static void buildXML() {
        CardsXMLBuilder builder = new CardsXMLBuilder(initialList, FILE_NAME);
        builder.build();
        Collections.sort(initialList);
    }

    @Test
    public void testDOMParser() {
        parser = factory.createCardsParser("dom");
        parser.parse(FILE_NAME);
        List<Card> cards = parser.getCards();
        Assertions.assertIterableEquals(initialList, cards);
    }

    @Test
    public void testSAXParser() {
        parser = factory.createCardsParser("sax");
        parser.parse(FILE_NAME);
        List<Card> cards = parser.getCards();
        Assertions.assertIterableEquals(initialList, cards);
    }

    @Test
    public void testStAXParser() {
        parser = factory.createCardsParser("stax");
        parser.parse(FILE_NAME);
        List<Card> cards = parser.getCards();
        Assertions.assertIterableEquals(initialList, cards);
    }
}
