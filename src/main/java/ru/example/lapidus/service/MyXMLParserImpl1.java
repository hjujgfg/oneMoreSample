package ru.example.lapidus.service;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.example.lapidus.interfaces.MyNode;
import ru.example.lapidus.interfaces.MyXMLParser;
import ru.example.lapidus.model.Customer;
import ru.example.lapidus.model.CustomerList;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Егор on 27.10.2015.
 */
@Component
public class MyXMLParserImpl1 extends DefaultHandler implements MyXMLParser {
    private String currentElement;
    private MyNode current;
    private MyNode parent;
    private CustomerList top;
    /**
     * One extra run over xml, however helps to decouple
     * @param xml
     * @param xsd
     * @return
     */
    @Override
    public boolean isValid(InputStream xml, InputStream xsd) {
        boolean isValid;
        try{
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            return true;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void startDocument() {
        System.out.print("Parsing started");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        currentElement = qName;
        switch (qName){
            case "customers":
                top = new CustomerList();
                current = top;
                break;
            case "customer":
                if (parent == null)
                    parent = top;
                current = parent.addChild();

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {

    }

    @Override
    public CustomerList parse(InputStream xml, InputStream xsd) {

        return null;
    }
}
