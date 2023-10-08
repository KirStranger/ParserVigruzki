package ru.kirstranger;

public class SingleString {
    private String date;
    private double amount;
    private String ID;
    private static final int minID =  500350560;
    private static final long maxID = 2599999999L;

    public SingleString(String date, double amount, String ID) {
        this.date = date;
        this.amount = amount;
        this.ID = ID;
    }

    public int getMinID() {
        return minID;
    }

    public long getMaxID() {
        return maxID;
    }
    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getID() {
        return ID;
    }
}
