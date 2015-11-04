package ru.example.lapidus.model;

import ru.example.lapidus.interfaces.MyNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Customer node
 * Created by Егор on 02.11.2015.
 */
public class Customer extends MyNode{
    private String name;
    private List<Order> orders;
    public Customer(MyNode parent) {
        orders = new ArrayList<Order>();
        this.parent = (CustomerList) parent;
    }

    public Order addChild() {
        Order o = new Order(this);
        orders.add(o);
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

    public double getMaxOrder() {
        double max = -1;
        for (Order o : orders) {
            double tmp = o.getTotal();
            if (tmp > max)
                max = tmp;
        }
        return max;
    }

    public double getMinOrder() {
        if (orders.isEmpty())
            return -1;
        double min = Double.MAX_VALUE;
        for (Order o : orders) {
            double tmp = o.getTotal();
            if (tmp < min)
                min = tmp;
        }
        return min;
    }

    public double getTotal() {
        double res = 0;
        for (Order o : orders) {
            res += o.getTotal();
        }
        return res;
    }

    public int getOrderNum(){
        return orders.size();
    }
}
