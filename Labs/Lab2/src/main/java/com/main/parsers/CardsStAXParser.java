package com.main.parsers;

import com.main.card.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

public class CardsStAXParser extends AbstractCardsParser {
    private static final String CARD = "card";
    private static final String ID = "id";
    private static final String AUTHOR = "author";
    private static final String THEME = "theme";
    private static final String TYPE = "type";
    private static final String SENT = "sent";
    private static final String KIND = "kind";
    private static final String COUNTRY = "country";
    private static final String YEAR = "year";
    private static final String VALUABLE = "valuable";

    private final XMLInputFactory inputFactory = XMLInputFactory.newInstance();

    @Override
    public void parse(String fullFileName) {
        FileInputStream inputStream = null;
        XMLStreamReader streamReader;
        String tagName;

        try {
            inputStream = new FileInputStream(new File(fullFileName));
            streamReader = inputFactory.createXMLStreamReader(inputStream);

            while (streamReader.hasNext()) {
                int constant = streamReader.next();
                if (constant == XMLStreamConstants.START_ELEMENT) {
                    tagName = streamReader.getLocalName();
                    if (CARD.equals(tagName)) {
                        Card card = buildCard(streamReader);
                        cards.add(card);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(cards);
    }

    private Card buildCard(XMLStreamReader streamReader) throws XMLStreamException {
        Card card = new Card();
        card.setId(streamReader.getAttributeValue(null, ID));
        if (streamReader.getAttributeCount() == 2) {
            card.setAuthor(streamReader.getAttributeValue(null, AUTHOR));
        }

        String tagName;
        while (streamReader.hasNext()) {
            int constant = streamReader.next();
            switch (constant) {
                case XMLStreamConstants.START_ELEMENT:
                    tagName = streamReader.getLocalName();
                    switch (tagName) {
                        case THEME:
                            card.setTheme(Theme.valueOf(getText(streamReader).toUpperCase()
                                    .replace(' ', '_')));
                            break;
                        case TYPE:
                            card.setType(buildType(streamReader));
                            break;
                        case COUNTRY:
                            card.setCountry(getText(streamReader));
                            break;
                        case YEAR:
                            card.setYear(Integer.parseInt(getText(streamReader)));
                            break;
                        case VALUABLE:
                            card.setValuable(Valuable.valueOf(getText(streamReader).toUpperCase()
                                    .replace(' ', '_')));
                            break;
                        default:
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    tagName = streamReader.getLocalName();
                    if (tagName.equals(CARD)) {
                        return card;
                    }
                    break;
                default:
                    break;
            }
        }

        throw new XMLStreamException("Unknown element in tag <card>.");
    }

    private Type buildType(XMLStreamReader streamReader) throws XMLStreamException {
        Type type = new Type();
        int constant;
        String tagName;

        type.setSent(Boolean.parseBoolean(streamReader.getAttributeValue(null, SENT)));
        while (streamReader.hasNext()) {
            constant = streamReader.next();
            switch (constant) {
                case XMLStreamConstants.START_ELEMENT -> {
                    tagName = streamReader.getLocalName();
                    if (tagName.equals(KIND)) {
                        type.setKind(Kind.valueOf(getText(streamReader).toUpperCase()
                                .replace(' ', '_')));
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    tagName = streamReader.getLocalName();
                    if (tagName.equals(TYPE)) {
                        return type;
                    }
                }
            }
        }

        throw new XMLStreamException("Unknown element in tag <type>.");
    }

    private String getText(XMLStreamReader streamReader) throws XMLStreamException {
        String text = null;
        if (streamReader.hasNext()) {
            streamReader.next();
            text = streamReader.getText();
        }
        return text;
    }
}
