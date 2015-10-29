package ru.example.lapidus.interfaces;

/**
 * Created by Егор on 28.10.2015.
 */
public interface MyNode {
    public MyNode addChild();
    public MyNode getParent();
    public void setId(int id);
    public void setParameter(String name, Object value);
}
