// Package structure: This organizes our code files.
package com.pluralsight;

// --- Imports: Bringing in necessary tools ---
import java.time.LocalDate; // Tool for handling the date component.
import java.time.LocalTime; // Tool for handling the time component.

// --- Class Definition: The blueprint for a Transaction object ---
public class Transaction {

    // --- Private Fields: Encapsulated Data ---
    // These five fields represent the data stored by every single Transaction object.
    // 'private' ensures data integrity (Encapsulation).
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;       // Stored as a 'double' for precision.

    // --- Constructor 1: Full-Parameter Initialization ---
    // Used to create a Transaction object with all data provided at once.
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        // 'this.' distinguishes the class field from the local parameter.
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // --- Constructor 2: Default Initialization (Empty) ---
    // Allows creation of an object with default/zeroed values, to be set later.
    public Transaction() {
        this.date = null;
        this.time = null;
        this.description = "";
        this.vendor = "";
        this.amount = 0;
    }

    // -------------------------------------------------------------------
    // --- Getters (Accessors): Read-Only access to private data ---
    // These methods provide the only *public* way to read the data.

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

    // -------------------------------------------------------------------
    // --- Setters (Mutators): Write access to private data ---
    // These methods allow the object's state to be modified safely.

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

    // --- Business Logic: Transaction Type Classification ---
    // This method determines the transaction type based on the amount's sign.
    public String getTypeOfTransaction() {
        if (this.amount >= 0) {
            return "DEPOSIT"; // Amount is positive or zero.
        } else {
            return "PAYMENT"; // Amount is negative.
        }
    }

    // Overrides the default method to provide a custom, human-readable string.
    public String toFileFormat() {
        // Formats the data into a pipe-separated string for easy output/storage.
        return date.toString() + "|" + time.toString() + "|" + this.description + "|" + this.vendor + "|" + this.amount;
    }
}