package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
                    System.out.println("Invalid choice. Please try again.");
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

            Transaction newT = new Transaction(date, time, description, vendor, amount);
            // create a FileWriter
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            // create a BufferedWriter
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            // write to the file
            bufferedWriter.write(newT.toFileFormat());
            bufferedWriter.newLine();
            // close the writer
            bufferedWriter.close();

            System.out.println("Recorded: " + newT.toFileFormat());
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
            System.out.println("\n--- Ledger Screen ---");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home Screen");
            System.out.println("Choose an option:  ");


        }
    }
//*/
}
