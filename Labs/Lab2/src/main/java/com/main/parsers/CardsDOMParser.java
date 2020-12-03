package com.main.parsers;

import com.main.card.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collections;

public class CardsDOMParser extends AbstractCardsParser {
    static {
        new PropertyConfigurator().doConfigure("log4j.properties", LogManager.getLoggerRepository());
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(CardsDOMParser.class);

    private DocumentBuilder documentBuilder;

    public CardsDOMParser() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void parse(String fullFileName) {
        try {
            Document document = documentBuilder.parse(fullFileName);
            Element root = document.getDocumentElement();
            NodeList cardsList = root.getElementsByTagName("card");
            for (int i = 0; i < cardsList.getLength(); i++) {
                Element cardElement = (Element) cardsList.item(i);
                Card card = buildCard(cardElement);
                cards.add(card);
            }
            Collections.sort(cards);
        } catch (IOException | SAXException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private Card buildCard(Element cardElement) {
        Card card = new Card();
        card.setId(cardElement.getAttribute("id"));
        if (cardElement.hasAttribute("author")) {
            card.setAuthor(cardElement.getAttribute("author"));
        }
        card.setTheme(Theme.valueOf(getTextContent(cardElement, "theme").toUpperCase()
                .replace(' ', '_')));

        Element typeElement = (Element) cardElement.getElementsByTagName("type").item(0);
        Type type = new Type();
        type.setSent(Boolean.parseBoolean(typeElement.getAttribute("sent")));
        type.setKind(Kind.valueOf(getTextContent(typeElement, "kind").toUpperCase()));
        card.setType(type);

        card.setCountry(getTextContent(cardElement, "country"));
        card.setYear(Integer.parseInt(getTextContent(cardElement, "year")));
        if (cardElement.getElementsByTagName("valuable").getLength() != 0) {
            card.setValuable(Valuable.valueOf(getTextContent(cardElement, "valuable").toUpperCase()));
        }

        return card;
    }

    private static String getTextContent(Element element, String elementName) {
        return element.getElementsByTagName(elementName).item(0).getTextContent();
    }
}
