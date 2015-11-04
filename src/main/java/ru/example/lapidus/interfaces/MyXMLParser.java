package ru.example.lapidus.interfaces;

import ru.example.lapidus.model.CustomerList;

import java.io.InputStream;

/**
 * Created by Егор on 02.11.2015.
 */
public interface MyXMLParser {

    public boolean isValid(InputStream xml, InputStream xsd);

    public MyNode getTop();
}
