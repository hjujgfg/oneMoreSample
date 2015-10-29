package ru.example.lapidus.model;

import ru.example.lapidus.interfaces.MyNode;

/**
 * Created by Егор on 28.10.2015.
 */
public class Position implements MyNode{
    private int id;
    private double price;
    private short count;
    private Order parent;

    public Position(MyNode parent) throws ClassCastException {
        this.parent = (Order) parent;
    }

    public MyNode addChild(){
        return null;
    }
    public MyNode getParent() {
        return parent;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setParameter(String name, Object value) {
        switch (name) {
            case "price":
                this.price = (Double)value;
                break;
            case "count":
                this.count = (Short)value;
                break;
            default:
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public short getCount() {
        return count;
    }

    public void setCount(short count) {
        this.count = count;
    }
}
