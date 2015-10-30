package ru.example.lapidus.service;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.example.lapidus.interfaces.MyNode;
import ru.example.lapidus.interfaces.MyXMLParser;
import ru.example.lapidus.utils.MyNodeFactory;

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
    //actually after parsing completes top should be equal to current
    private MyNode top;
    /**
     * Validates XML against schema
     * One extra run over xml, however we will not try to parse in case it is not valid
     * @param xml InputStream to validate
     * @param xsd InputStream of schema
     * @return true if valid, false - otherwise
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
                top = MyNodeFactory.buildNode(qName);
                current = top;
                break;
            case "customer":
                if (parent == null)
                    parent = top;
                current = parent.addChild();
                break;
            case "position":
            case "order":
                parent = current;
                current = current.addChild();
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        switch (currentElement) {
            case "id":
            case "name":
            case "price":
            case "count":
                current.setProperty(currentElement, new String(ch, start, length));
                break;
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        currentElement = "";
        switch (qName) {
            case "position":
            case "order":
            case "customer":
                current = current.getParent();
                parent = current.getParent();
                break;
            case "customers":
                System.out.print("Seems like we are out");
        }
    }

    public MyNode getTop() {
        return top;
    }
}
