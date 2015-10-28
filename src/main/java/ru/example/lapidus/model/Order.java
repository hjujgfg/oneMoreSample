package ru.example.lapidus.model;

import ru.example.lapidus.interfaces.MyNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Егор on 28.10.2015.
 */
public class Order implements MyNode{
    private int id;
    private List<Position> positions;
    int currentPosition;
    private Customer parent;
    public Order(MyNode parent) throws ClassCastException{
        positions = new ArrayList<Position>();
        this.parent = (Customer) parent; 
    }

    public  Position addChild() {
        Position p = new Position(this);
        positions.add(p);
        currentPosition = positions.size() - 1;
        return p;
    }

    @Override
    public MyNode getParent() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
