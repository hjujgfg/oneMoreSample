package ru.example.lapidus.model;

import ru.example.lapidus.interfaces.MyNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Егор on 28.10.2015.
 */
public class Customer extends MyNode{
    private String name;
    private List<Order> orders;
    private int currentOrder;
    public Customer(MyNode parent) {
        orders = new ArrayList<Order>();
        this.parent = (CustomerList) parent;
    }

    public Order addChild() {
        Order o = new Order(this);
        orders.add(o);
        currentOrder = orders.size() - 1;
        return o;
    }

    @Override
    public void setProperty(String name, String value) {
        if (name == "name") {
            this.name = (String)value;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
