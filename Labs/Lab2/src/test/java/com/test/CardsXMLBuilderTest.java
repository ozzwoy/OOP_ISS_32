package com.test;

import com.main.CardsXMLBuilder;
import com.main.card.*;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardsXMLBuilderTest {
    @Test
    public void testWithValidator() throws IOException, SAXException {
        String fileName = "src/test/resources/builder_test.xml";
        List<Card> cards = new ArrayList<>() {
            {
                add(new Card("KU67234", Theme.CITY_SCENERY, new Type(true, Kind.ADVERTISEMENT), "USA",
                             1986, null, null));
                add(new Card("NP37953", Theme.NATURE, new Type(false, Kind.SIMPLE), "Japan", 2007,
                             "O. Kobayashi", Valuable.THEMATIC));
                add(new Card("RG90790", Theme.SPORT, new Type(true, Kind.INVITATION), "Berlin",
                             1995, null, Valuable.COLLECTIBLE));
            }
        };
        CardsXMLBuilder builder = new CardsXMLBuilder(cards, fileName);
        builder.build();

        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        String schemaName = "src/main/resources/schema.xsd";
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaName);
        Schema schema = factory.newSchema(schemaLocation);
        Validator validator = schema.newValidator();
        Source source = new StreamSource(fileName);
        validator.validate(source);
    }
}
