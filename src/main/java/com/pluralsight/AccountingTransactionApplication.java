package com.pluralsight;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AccountingTransactionApplication {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

       //ArrayList<Transaction> employees = readTransactions();
/*
        for (Employee employee: employees) {
            System.out.println(employee);
        }
*/
    }
/*
    private static void readTransactions() {

        //File not exists code goes here

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
//                System.out.println(line);
                String[] parts = line.split(Pattern.quote("|"));

                Transaction transaction = new Transaction();

                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

            }
            // close the stream and release the resources
            bufferedReader.close();
        } catch (IOException e) {
            // display stack trace if there was an error
            e.printStackTrace();
        }

        return employees;
*/

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
//                System.out.println(line);
                String[] parts = line.split(Pattern.quote("|"));

                Transaction transaction = new Transaction();

                String date = parts[0];

                String time = parts[1];

                String description = parts[2];

                String vendor = parts[3];

                double amount = Double.parseDouble(parts[4]);

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
                    displayLedger();
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
        makeTransaction(true);
    }

    public static void makePayment() {
        makeTransaction(false);
    }

    public static void makeTransaction(boolean isDeposit) {
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
            String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            Transaction newT = new Transaction(date, time, description, vendor, amount);
            try {
                // create a FileWriter
                FileWriter fileWriter = new FileWriter("transactions.csv", true);
                // create a BufferedWriter
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                // write to the file
                bufferedWriter.write(newT.toString());
                bufferedWriter.newLine();
                // close the writer
                bufferedWriter.close();
            }
            System.out.println("Recorded: " + newT.toString());
        } catch (IOException e) {
            System.out.println("ERROR: An unexpected error occurred");
            e.getStackTrace();
        }
    }

    public static void ledgerScreen() {
        while (true) {
            System.out.println("\n--- LEDGER SCREEN ---");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home Screen");
            System.out.println("Choose an option:  ");

            String
        }
    }
}
