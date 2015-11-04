package ru.example.lapidus.model;

import jdk.jfr.events.ExceptionThrownEvent;
import ru.example.lapidus.interfaces.MyNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents Order node
 * Created by Егор on 02.11.2015.
 */
public class Order extends MyNode{
    private List<Position> positions;
    public Order(MyNode parent) throws ClassCastException{
        positions = new ArrayList<Position>();
        this.parent = parent;
    }

    public  Position addChild() {
        Position p = new Position(this);
        positions.add(p);
        return p;
    }

    public double getTotal() {
        double res = 0;
        for (Position p : positions) {
            res += p.getTotalCost();
        }
        return res;
    }

    public double getAverage() {
        if (positions.isEmpty())
            return 0;
        double res = 0;
        for (Position p : positions) {
            res += p.getTotalCost();
        }
        return res / positions.size();
    }
}
