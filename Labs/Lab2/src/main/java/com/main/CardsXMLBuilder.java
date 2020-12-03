package com.main;

import com.main.card.Card;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CardsXMLBuilder {
    static {
        new PropertyConfigurator().doConfigure("log4j.properties", LogManager.getLoggerRepository());
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(CardsXMLBuilder.class);

    List<Card> cards;
    String path;

    public CardsXMLBuilder(List<Card> cards, String path) {
        this.cards = cards;
        this.path = path;
    }

    public void build() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
            Element rootElement = document.createElementNS("http://www.example.com/cards",
                                                           "OldCard");
            rootElement.setPrefix("cns");
            document.appendChild(rootElement);

            for (Card card : cards) {
                Element cardElement = document.createElement("card");
                cardElement.setAttribute("id", card.getId());
                if (card.getAuthor() != null) {
                    cardElement.setAttribute("author", card.getAuthor());
                }

                Element themeElement = document.createElement("theme");
                themeElement.appendChild(document.createTextNode(card.getTheme().toString()));
                cardElement.appendChild(themeElement);

                Element typeElement = document.createElement("type");
                typeElement.setAttribute("sent", Boolean.toString(card.getType().isSent()));
                Element kindElement = document.createElement("kind");
                kindElement.appendChild(document.createTextNode(card.getType().getKind().toString()));
                typeElement.appendChild(kindElement);
                cardElement.appendChild(typeElement);

                Element countryElement = document.createElement("country");
                countryElement.appendChild(document.createTextNode(card.getCountry()));
                cardElement.appendChild(countryElement);

                Element yearElement = document.createElement("year");
                yearElement.appendChild(document.createTextNode(Integer.toString(card.getYear())));
                cardElement.appendChild(yearElement);

                if (card.getValuable() != null) {
                    Element valuableElement = document.createElement("valuable");
                    valuableElement.appendChild(document.createTextNode(card.getValuable().toString()));
                    cardElement.appendChild(valuableElement);
                }

                rootElement.appendChild(cardElement);
            }
        } catch (ParserConfigurationException e) {
            LOGGER.error(e.getMessage(), e);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileWriter(path));
            transformer.transform(source, result);
        } catch (IOException | TransformerException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
