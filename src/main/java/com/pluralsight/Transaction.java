package com.pluralsight;

public class Transaction {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(String date, String time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public Transaction() {

    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }





/*
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transaction{");
        sb.append("date='").append(date).append('\'');
        sb.append(", time='").append(time).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", vendor='").append(vendor).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
*/
/*
   @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%.2f",
        this.date, this.time, this.description, this.vendor, this.amount);
    }
*/

}
