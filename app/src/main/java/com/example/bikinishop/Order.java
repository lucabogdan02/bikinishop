package com.example.bikinishop;

public class Order {
    private int orderNumber;
    private User customer;
    private long timeOfOrder;
    private Bikini orderedBikini;
    private static int ordernumber = 1;

    public Order() {
        this.orderNumber = 0;
        this.timeOfOrder = 0;
        this.orderedBikini = new Bikini();
    }

    public Order(int orderNumber, User customer, long timeOfOrder, Bikini orderedBikini) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.timeOfOrder = timeOfOrder;
        this.orderedBikini = orderedBikini;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public long getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setTimeOfOrder(long timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public Bikini getOrderedBikini() {
        return orderedBikini;
    }

    public void setOrderedBikini(Bikini orderedBikini) {
        this.orderedBikini = orderedBikini;
    }

    public static int getOrdernumber() {
        return ordernumber;
    }

    public static void setOrdernumber(int ordernumber) {
        Order.ordernumber = ordernumber;
    }
}
