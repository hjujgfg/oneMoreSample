package ru.example.lapidus.model;

import ru.example.lapidus.interfaces.MyNode;

/**
 * Created by Егор on 28.10.2015.
 */
public class Position extends MyNode{
    private double price;
    private short count;

    public Position(MyNode parent){
        this.parent = parent;
    }

    public MyNode addChild(){
        return null;
    }

    @Override
    public void setProperty(String name, String value) {
        switch (name) {
            case "price":
                this.price = Double.parseDouble(value);
                break;
            case "count":
                this.count = Short.parseShort(value);
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
