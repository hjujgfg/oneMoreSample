package ru.example.lapidus.utils;

import ru.example.lapidus.interfaces.MyNode;
import ru.example.lapidus.model.CustomerList;
import ru.example.lapidus.model.Order;

/**
 * Created by Егор on 30.10.2015.
 * We want to decouple implementation and interface
 */
public class MyNodeFactory {
    public static MyNode buildNode(String name) {
        switch (name){
            case "customers":
                return new CustomerList();
            default:
                return null;
        }
    }
}
