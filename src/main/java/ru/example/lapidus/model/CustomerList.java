package ru.example.lapidus.model;

import ru.example.lapidus.interfaces.MyNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Егор on 27.10.2015.
 */
public class CustomerList implements MyNode {
    private List<Customer> customers;
    int current;
    public CustomerList() {
        customers = new ArrayList<Customer>();
    }

    /**
     * adds and returns current object
     * @return
     */
    public Customer addChild() {
        Customer c = new Customer(this);
        customers.add(c);
        current = customers.size() - 1;
        return c;
    }

    @Override
    public MyNode getParent() {
        return null;
    }

}
