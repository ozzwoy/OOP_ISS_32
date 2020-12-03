package com.main.parsers;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Collections;

public class CardsSAXParser extends AbstractCardsParser {
    private final CardsHandler cardsHandler;
    private SAXParser parser;

    public CardsSAXParser() {
        cardsHandler = new CardsHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(String fullFileName) {
        try {
            parser.parse(fullFileName, cardsHandler);
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        }
        cards = cardsHandler.getCards();
        Collections.sort(cards);
    }
}
