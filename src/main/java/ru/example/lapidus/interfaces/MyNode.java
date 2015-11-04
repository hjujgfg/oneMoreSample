package ru.example.lapidus.interfaces;

/**
 * Created by Егор on 02.11.2015.
 */
public abstract class MyNode {
    protected int id;
    protected MyNode parent;


    public void setProperty(String propertyName, String value) {
        id = Integer.parseInt(value);
    }

    public int getProperty(String name) {
        return id;
    }

    /**
     * just for convenience
     * @return
     */
    public final int getId(){
        return id;
    }

    public final void setId(int id){
        this.id = id;
    }

    public abstract MyNode addChild();

    public MyNode getParent(){
        return parent;
    }
}
