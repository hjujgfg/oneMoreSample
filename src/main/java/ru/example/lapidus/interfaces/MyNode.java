package ru.example.lapidus.interfaces;

/**
 * General node abstract class
 * Created by Егор on 02.11.2015.
 */
public abstract class MyNode {
    protected int id;
    protected MyNode parent;


    /**
     * Method overridden in children to set their properties
     * @param propertyName
     * @param value
     */
    public void setProperty(String propertyName, String value) {
        id = Integer.parseInt(value);
    }

    public int getProperty(String name) {
        return id;
    }

    /**
     * just for convenience
     * @return id
     */
    public final int getId(){
        return id;
    }

    public final void setId(int id){
        this.id = id;
    }

    /**
     * Encapsulates creation of child nodes
     * @return
     */
    public abstract MyNode addChild();

    public MyNode getParent(){
        return parent;
    }
}
