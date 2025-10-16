package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AccountingTransactionApplication {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

       ArrayList<Transaction> transaction = readTransactions();
/*
        for (Transaction t: transaction) {
            System.out.println(t);
        }
*/

        homeScreen();

    }

    public static void homeScreen() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== HOME SCREEN =====");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim().toUpperCase(); // normalize input

            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    displayLedgerScreen();
                    break;
                case "X":
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid choice.");
            }

            System.out.println(); // blank line for spacing
        }
    }

    public static void addDeposit() {
        System.out.println("\n--- Add Deposit ---");
        addTransaction(true);
    }

    public static void makePayment() {
        System.out.println("\n--- Make Payment ---");
        addTransaction(false);
    }

    public static void addTransaction(boolean isDeposit) {
        try {
            System.out.println("Description: ");
            String description = scanner.nextLine();

            System.out.println("Vendor: ");
            String vendor = scanner.nextLine();

            System.out.println("Amount: $");
            double amount = Double.parseDouble(scanner.nextLine());

            if (!isDeposit) {
                amount = -Math.abs(amount);
            } else {
                amount = Math.abs(amount);
            }

            LocalDateTime now = LocalDateTime.now();
            LocalDate date = now.toLocalDate();
            LocalTime time = now.toLocalTime();

            Transaction newT = new Transaction();
            newT.setDate(date);
            newT.setTime(time);
            newT.setDescription(description);
            newT.setVendor(vendor);
            newT.setAmount(amount);
            // create a FileWriter
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            // create a BufferedWriter
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            // write to the file
            bufferedWriter.write(newT.toFileFormat());
            bufferedWriter.newLine();
            // close the writer
            bufferedWriter.close();

         //   System.out.println("Recorded: " + newT.toFileFormat());
        } catch (IOException e) {
            System.out.println("ERROR: An unexpected error occurred");
            e.getStackTrace();
        }
    }

    private static ArrayList<Transaction> readTransactions() {
        ArrayList<Transaction> list = new ArrayList<>();
        try {
            // create a FileReader object connected to the File
            FileReader fileReader = new FileReader("transactions.csv");
            // create a BufferedReader to manage input stream
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            //skip header line
            bufferedReader.readLine();


            // read until there is no more data
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("|"));

                Transaction transaction = new Transaction();

                transaction.setDate(LocalDate.parse(parts[0]));

                transaction.setTime(LocalTime.parse(parts[1]));

                transaction.setDescription(parts[2]);

                transaction.setVendor(parts[3]);

                transaction.setAmount(Double.parseDouble(parts[4]));

                list.add(transaction);
            }
            // close the stream and release the resources
            bufferedReader.close();
        } catch (IOException e) {
            // display stack trace if there was an error
            e.printStackTrace();
        }
        return list;
    }


    public static void displayLedgerScreen() {
        while (true) {
            ArrayList<Transaction> transactions = readTransactions();

            System.out.println("\n--- Ledger Screen ---");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home Screen");
            System.out.println("Choose an option:  ");
            String choice = scanner.nextLine();

            String nextChoice = "";
            switch (choice) {
                case "A":
                    nextChoice = "All";
                    break;
                case "D":
                    nextChoice = "Deposit";
                    break;
                case "P":
                    nextChoice = "Payment";
                    break;
                case "R":
                    displayReportsScreen();
                    continue;
                case "H":
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid choice.");
                    continue;
            }
            System.out.println("\n--- Ledger Entries (" + nextChoice + ") ---");
            System.out.println("Date|Time|Description|Vendor|Amount");

            for (Transaction t : transactions) {
                double amount = t.getAmount();
                boolean displays = false;

                switch (nextChoice) {
                    case "All":
                        displays = true;
                        break;
                    case "Deposit":
                        if (amount > 0) {
                            displays = true;
                        }
                        break;
                    case "Payment":
                        if (amount < 0) {
                            displays = true;
                        }
                        break;
                }
                if (displays) {
                    System.out.println(t.getDate().toString() + "|" +
                            t.getTime().toString() + "|" +
                            t.getDescription() + "|" +
                            t.getVendor() + "|" +
                            t.getAmount());
                }
            }
        }
    }

    public static void displayReportsScreen() {
        while (true) {
            System.out.println("\n--- Reports Screen ---");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back to Ledger");
            System.out.println("Enter your choice: ");

            String input = scanner.nextLine();

            ArrayList<Transaction> allTransactions = readTransactions();
            LocalDate today = LocalDate.now();

            String reportName = "";
            LocalDate startDate = null;
            LocalDate endDate = null;

            switch(input) {
                case "1": // Month To Date (MTD)
                    reportName = "Month To Date";
                    // Start on the 1st of the current month
                    startDate = today.withDayOfMonth(1);
                    endDate = today;
                    break;
                case "2": // Previous Month (PM)
                    reportName = "Previous Month";
                    // Move to the previous month
                    LocalDate previousMonth = today.minusMonths(1);
                    // Start: 1st day of the previous month
                    startDate = previousMonth.withDayOfMonth(1);
                    // End: Last day of the previous month
                    endDate = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth());
                    break;
                case "3": // Year To Date (YTD)
                    reportName = "Year To Date";
                    // Start on the 1st day of the current year
                    startDate = today.withDayOfYear(1);
                    endDate = today;
                    break;
                case "4": // Previous Year (PY)
                    reportName = "Previous Year";
                    // Move to the previous year
                    LocalDate previousYear = today.minusYears(1);
                    // Start: 1st day of the previous year
                    startDate = previousYear.withDayOfYear(1);
                    // End: Last day of the previous year
                    endDate = previousYear.withDayOfYear(previousYear.lengthOfYear());
                    break;
                    //*/
            }
            // --- FILTERING AND DISPLAY LOGIC for MTD, PM, YTD, PY ---

            double totalDeposits = 0;
            double totalPayments = 0;
            boolean found = false;

            System.out.printf("\n--- %s Report (%s to %s) ---\n",
                    reportName,
                    startDate.toString(),
                    endDate.toString());

            System.out.println("Date|Time|Description|Vendor|Amount");
            System.out.println("-------------------------------------------------------------------------------------------------");

            for (Transaction t : allTransactions) {
                LocalDate transactionDate = t.getDate();

                // Check if the transaction date is within the inclusive range: [startDate, endDate]
                if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                    double amount = t.getAmount();
                    found = true;

                    // Display transaction details
                    System.out.println(t.getDate().toString() + "|" +
                            t.getTime().toString() + "|" +
                            t.getDescription() + "|" +
                            t.getVendor() + "|" +
                            String.format("%.2f", t.getAmount()));

                    if (amount > 0) {
                        totalDeposits += amount;
                    } else {
                        totalPayments += amount;
                    }
                }
            }

            if (!found) {
                System.out.println("No transactions found in this date range.");
            } else {
                // Print the summary after the transaction list
                System.out.printf("Total Deposits: $%.2f\n", totalDeposits);
                System.out.printf("Total Payments: $%.2f\n", totalPayments);
                System.out.printf("Net Change: $%.2f\n", totalDeposits + totalPayments);
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();

        }
    }

    public static void searchByVendor() {
        System.out.println("Enter the Vendor Name to search: ");
        String vendorSearch = scanner.nextLine().trim();

        ArrayList<Transaction> allTransactions = readTransactions();

        System.out.println("\n--- Vendor Results: " + vendorSearch + "---");
        boolean found = false;

        System.out.println("Date\t\tTime\t\tDescription\t\tVendor\t\tAmount");

        for (Transaction t: allTransactions) {
            if (t.getVendor().toLowerCase().contains(vendorSearch.toLowerCase())){
                System.out.println(t.getDate().toString() + "\t" +
                        t.getTime().toString() + "\t" +
                        t.getDescription() + "\t\t" +
                        t.getVendor() + "\t\t" + t.getAmount());
            found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions found for : " + vendorSearch);
        }
        System.out.println("\nPress Enter to Continue: ");
        scanner.nextLine();
    }
}
