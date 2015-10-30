package ru.example.lapidus.model;

import jdk.jfr.events.ExceptionThrownEvent;
import ru.example.lapidus.interfaces.MyNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Егор on 28.10.2015.
 */
public class Order extends MyNode{
    private List<Position> positions;
    int currentPosition;
    public Order(MyNode parent) throws ClassCastException{
        positions = new ArrayList<Position>();
        this.parent = parent;
    }

    public  Position addChild() {
        Position p = new Position(this);
        positions.add(p);
        currentPosition = positions.size() - 1;
        return p;
    }
}
