package com.main.parsers;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Collections;

public class CardsSAXParser extends AbstractCardsParser {
    static {
        new PropertyConfigurator().doConfigure("log4j.properties", LogManager.getLoggerRepository());
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(CardsSAXParser.class);

    private final CardsHandler cardsHandler;
    private SAXParser parser;

    public CardsSAXParser() {
        cardsHandler = new CardsHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void parse(String fullFileName) {
        try {
            parser.parse(fullFileName, cardsHandler);
        } catch (IOException | SAXException e) {
            LOGGER.error(e.getMessage(), e);
        }
        cards = cardsHandler.getCards();
        Collections.sort(cards);
    }
}
