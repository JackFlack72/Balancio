package com.pluralsight;


import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public Transaction() {
        this.date = null;
        this.time = null;
        this.description = "";
        this.vendor = "";
        this.amount = 0;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

//Determines if display is Deposit or Payment
    public String getTypeOfTransaction() {
        if (this.amount >= 0) {
            return "DEPOSIT";
        } else {
            return "PAYMENT";
        }
    }

    public String toString() {
        return this.date + "|" + this.time + "|" + this.description + "|" + this.vendor + "|" + this.amount;
    }
}
