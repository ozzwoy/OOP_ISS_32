package com.main.parsers;

import com.main.card.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class CardsHandler extends DefaultHandler {
    private static final String CARD = "card";
    private static final String THEME = "theme";
    private static final String TYPE = "type";
    private static final String KIND = "kind";
    private static final String COUNTRY = "country";
    private static final String YEAR = "year";
    private static final String VALUABLE = "valuable";

    private final List<Card> cards;
    private Card current = null;
    private String currentName = null;
    private final List<String> withText;

    public CardsHandler() {
        cards = new ArrayList<>();
        withText = List.of(THEME, KIND, COUNTRY, YEAR, VALUABLE);
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals(CARD)) {
            current = new Card();
            if (attributes.getLength() == 2) {
                current.setAuthor(attributes.getValue(0));
                current.setId(attributes.getValue(1));
            } else {
                current.setId(attributes.getValue(0));
            }
        } else if (qName.equals(TYPE)) {
            Type type = new Type();
            type.setSent(Boolean.parseBoolean(attributes.getValue(0)));
            current.setType(type);
        } else {
            if (withText.contains(qName)) {
                currentName = qName;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(CARD)) {
            cards.add(current);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String str = new String(ch, start, length).trim();
        if (currentName != null) {
            switch (currentName) {
                case THEME:
                    current.setTheme(Theme.valueOf(str.toUpperCase().replace(' ', '_')));
                    break;
                case KIND:
                    current.getType().setKind(Kind.valueOf(str.toUpperCase()));
                    break;
                case COUNTRY:
                    current.setCountry(str);
                    break;
                case YEAR:
                    current.setYear(Integer.parseInt(str));
                    break;
                case VALUABLE:
                    current.setValuable(Valuable.valueOf(str.toUpperCase()));
                    break;
                default:
                    break;
            }

            currentName = null;
        }
    }
}
