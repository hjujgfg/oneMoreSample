package ru.example.lapidus.model;

import ru.example.lapidus.interfaces.MyNode;
import sun.security.pkcs11.P11Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Егор on 27.10.2015.
 */
public class CustomerList extends MyNode {
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
    public void setProperty(String name, String value) {
        if (name == "id"){
            id = Integer.parseInt(value);
        }
    }

    public double getTotal() {
        double res = 0;
        for (Customer c : customers) {
            res += c.getTotal();
        }
        return res;
    }

    public double getMinOrder() {
        if (customers.isEmpty())
            return -1;
        double min = Double.MAX_VALUE;
        for (Customer c : customers) {
            double tmp = c.getMinOrder();
            if (tmp < min)
                min = tmp;
        }
        return min;
    }

    public double getMaxOrder() {
        double max = -1;
        for (Customer c : customers) {
            double tmp = c.getMaxOrder();
            if (tmp > max)
                max = tmp;
        }
        return max;
    }

    public String getMaxClient() {
        Customer t = null;
        double max = 0;
        for (Customer c : customers) {
            double tmp = c.getTotal();
            if (tmp > max) {
                max = tmp;
                t = c;
            }
        }
        if (t != null) {
            return t.getName();
        } else {
            return "no customers";
        }
    }

    public int getOrderNum() {
        int res = 0;
        for (Customer c : customers) {
            res += c.getOrderNum();
        }
        return res;
    }

    public double getAvgPrice() {
        double num = getOrderNum();
        if (num == 0)
            return -1;
        return getTotal() / num;
    }

    public String getFilteredCustomers(double n) {
        StringBuilder sb = new StringBuilder();
        for (Customer c : customers) {
            if (c.getTotal() > n)
                sb.append(c.getName() + "; ");
        }
        return sb.toString();
    }
}
