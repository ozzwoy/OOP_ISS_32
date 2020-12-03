package com.main.parsers;

public class CardsParserFactory {
    private static final String DOM = "dom";
    private static final String SAX = "sax";
    private static final String STAX = "stax";

    public AbstractCardsParser createCardsParser(String parserType) {
        parserType = parserType.toLowerCase();
        return switch (parserType) {
            case DOM -> new CardsDOMParser();
            case SAX -> new CardsSAXParser();
            case STAX -> new CardsStAXParser();
            default -> null;
        };
    }
}
