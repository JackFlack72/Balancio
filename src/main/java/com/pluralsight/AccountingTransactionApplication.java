// Package structure: Organizes application files.
package com.pluralsight;

// --- Imports: Core utilities for I/O, Time, Collections, and User Input ---
import java.io.*;           // Used for File Input/Output (reading/writing to transactions.csv).
import java.time.LocalDate;     // Date component (YYYY-MM-DD).
import java.time.LocalDateTime; // Combined date and time.
import java.time.LocalTime;     // Time component (HH:MM:SS).
import java.util.ArrayList; // Dynamic array to hold Transaction objects in memory.
import java.util.Scanner;   // Tool for reading user input from the console.
import java.util.regex.Pattern; // Used for splitting the CSV lines safely.

// --- Main Application Class ---
public class AccountingTransactionApplication {
    // Shared Scanner object: Used throughout the application to read user input.
    private static Scanner scanner = new Scanner(System.in);

    // --- Main Method: Application Entry Point ---
    public static void main(String[] args) {

        // Load all existing transactions from the file upon startup.
        ArrayList<Transaction> transaction = readTransactions();

        // Starts the main user interface loop.
        homeScreen();

    }

    // --- homeScreen(): Main User Interface Loop ---
    public static void homeScreen() {
        boolean running = true; // Control flag for the loop.

        while (running) {
            // Display main menu options.
            System.out.println("\n===== HOME SCREEN =====");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim().toUpperCase(); // Read and normalize input.

            // Navigation structure: directs the program flow based on user choice.
            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    displayLedgerScreen(); // Enters the detailed ledger viewing mode.
                    break;
                case "X":
                    System.out.println("Exiting... Goodbye!");
                    running = false; // Terminates the main loop.
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid choice.");
            }
            // Note: Removed the extra System.out.println() for conciseness.
        }
    }

    // --- Deposit/Payment Wrappers ---
    public static void addDeposit() {
        System.out.println("\n--- Add Deposit ---");
        addTransaction(true); // 'true' means it's a deposit.
    }

    public static void makePayment() {
        System.out.println("\n--- Make Payment ---");
        addTransaction(false); // 'false' means it's a payment.
    }

    // --- addTransaction(): Core Transaction Input and File Write ---
    public static void addTransaction(boolean isDeposit) {
        try {
            // 1. Gather User Input
            System.out.println("Description: ");
            String description = scanner.nextLine();

            System.out.println("Vendor: ");
            String vendor = scanner.nextLine();

            System.out.println("Amount: $");
            double amount = Double.parseDouble(scanner.nextLine()); // Safely converts input to a number.

            // 2. Normalize Amount (Crucial Logic)
            if (!isDeposit) {
                amount = -Math.abs(amount); // Ensures payments are stored as negative numbers.
            } else {
                amount = Math.abs(amount); // Ensures deposits are stored as positive numbers.
            }

            // 3. Capture Current Time
            LocalDateTime now = LocalDateTime.now(); // Gets current system time.
            LocalDate date = now.toLocalDate();
            LocalTime time = now.toLocalTime();

            // 4. Create and Populate Transaction Object
            Transaction newT = new Transaction(); // Uses the default constructor.
            newT.setDate(date);                   // Uses the setter methods.
            newT.setTime(time);
            newT.setDescription(description);
            newT.setVendor(vendor);
            newT.setAmount(amount);

            // 5. File I/O: Append to transactions.csv
            // FileWriter with 'true' appends to the file, instead of overwriting.
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Writes the new transaction data line. (Assumes toFileFormat() exists in Transaction.java)
            bufferedWriter.write(newT.toFileFormat());
            bufferedWriter.newLine(); // Adds a newline character.
            bufferedWriter.close(); // Releases file resources (essential step).

            //   System.out.println("Recorded: " + newT.toFileFormat());
        } catch (IOException e) {
            System.out.println("ERROR: An unexpected error occurred");
            e.getStackTrace();
        }
    }

    // --- readTransactions(): Core File Read and Data Load ---
    private static ArrayList<Transaction> readTransactions() {
        ArrayList<Transaction> list = new ArrayList<>(); // List to hold loaded objects.
        try {
            // File I/O Setup (Read)
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            bufferedReader.readLine(); // Skips the header line in the CSV file.

            // Loop to process every line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // Splits the line using the pipe character '|' as a delimiter.
                String[] parts = line.split(Pattern.quote("|"));

                // 1. Create Object
                Transaction transaction = new Transaction();

                // 2. Parse and Set Data (String to Date/Time/Double)
                transaction.setDate(LocalDate.parse(parts[0]));
                transaction.setTime(LocalTime.parse(parts[1]));
                transaction.setDescription(parts[2]);
                transaction.setVendor(parts[3]);
                transaction.setAmount(Double.parseDouble(parts[4]));

                list.add(transaction); // Add the fully constructed object to the list.
            }
            bufferedReader.close(); // Close the reader (essential step).
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list; // Returns the list of all transactions loaded from the file.
    }


    // --- displayLedgerScreen(): Filtering for All, Deposits, or Payments ---
    public static void displayLedgerScreen() {
        while (true) {
            ArrayList<Transaction> transactions = readTransactions(); // Reload data for fresh view.

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
                    displayReportsScreen(); // Navigation to the detailed reports menu.
                    continue; // Jumps back to the top of the 'Ledger' loop.
                case "H":
                    return; // Exits the Ledger screen and returns to the homeScreen loop.
                default:
                    System.out.println("Invalid choice. Please enter a valid choice.");
                    continue; // Skips to the next iteration of the while loop.
            }

            // --- Ledger Display Header ---
            System.out.println("\n--- Ledger Entries (" + nextChoice + ") ---");
            System.out.println("Date|Time|Description|Vendor|Amount");

            // --- Ledger Display Logic (Filtering) ---
            for (Transaction t : transactions) {
                double amount = t.getAmount();
                boolean displays = false;

                // Determines if the current transaction should be displayed based on the filter.
                switch (nextChoice) {
                    case "All":
                        displays = true;
                        break;
                    case "Deposit":
                        if (amount > 0) { // Filter: Only positive amounts.
                            displays = true;
                        }
                        break;
                    case "Payment":
                        if (amount < 0) { // Filter: Only negative amounts.
                            displays = true;
                        }
                        break;
                }

                // Prints the transaction if it matches the current filter.
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

    // --- displayReportsScreen(): Generating Date-Range Reports ---
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

            // --- Navigation and Date Calculation Logic ---
            switch(input) {
                case "1": // Month To Date (MTD)
                    reportName = "Month To Date";
                    startDate = today.withDayOfMonth(1); // Start on the 1st of the current month.
                    endDate = today;
                    break;
                case "2": // Previous Month (PM)
                    reportName = "Previous Month";
                    LocalDate previousMonth = today.minusMonths(1); // Move to the previous month.
                    startDate = previousMonth.withDayOfMonth(1);
                    endDate = previousMonth.withDayOfMonth(previousMonth.lengthOfMonth()); // Last day of previous month.
                    break;
                case "3": // Year To Date (YTD)
                    reportName = "Year To Date";
                    startDate = today.withDayOfYear(1); // January 1st of current year.
                    endDate = today;
                    break;
                case "4": // Previous Year (PY)
                    reportName = "Previous Year";
                    LocalDate previousYear = today.minusYears(1);
                    startDate = previousYear.withDayOfYear(1);
                    endDate = previousYear.withDayOfYear(previousYear.lengthOfYear()); // December 31st of previous year.
                    break;
                case "5": // Search by Vendor
                    searchByVendor(); // Calls the dedicated vendor search method.
                    continue; // Skip the date filtering below and show the reports menu again.
                case "0":
                    return; // Exits the loop and goes back to the Ledger screen.
                default:
                    System.out.println("Invalid choice. Please enter a valid choice.");
                    continue; // Skip all filtering and show the reports menu again.
            }

            // --- Report Filtering and Summary Logic (Runs for cases 1, 2, 3, 4) ---

            double totalDeposits = 0;
            double totalPayments = 0;
            boolean found = false;

            // Display report header based on calculated dates.
            System.out.printf("\n--- %s Report (%s to %s) ---\n",
                    reportName,
                    startDate.toString(),
                    endDate.toString());

            System.out.println("Date|Time|Description|Vendor|Amount");
            System.out.println("-------------------------------------------------------------------------------------------------");

            // Iterates through every Transaction in the list.
            for (Transaction t : allTransactions) {
                LocalDate transactionDate = t.getDate();

                // Core Filter: Check if the transaction date is within the inclusive range: [startDate, endDate]
                if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                    double amount = t.getAmount();
                    found = true; // Set flag to true: we found at least one match.

                    // Display transaction details
                    System.out.println(t.getDate().toString() + "|" +
                            t.getTime().toString() + "|" +
                            t.getDescription() + "|" +
                            t.getVendor() + "|" +
                            String.format("%.2f", t.getAmount()));

                    // Running Totals Calculation
                    if (amount > 0) {
                        totalDeposits += amount;
                    } else {
                        totalPayments += amount; // Payments are usually negative, so we add the negative value.
                    }
                }
            }

            // Final Summary Display
            if (!found) {
                System.out.println("No transactions found in this date range.");
            } else {
                // Print the financial summary section.
                System.out.printf("Total Deposits: $%.2f\n", totalDeposits);
                System.out.printf("Total Payments: $%.2f\n", totalPayments);
                System.out.printf("Net Change: $%.2f\n", totalDeposits + totalPayments); // Deposits + Payments (negative value)
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    // --- searchByVendor(): Specific Vendor Filter ---
    public static void searchByVendor() {
        System.out.println("Enter the Vendor Name to search: ");
        String vendorSearch = scanner.nextLine().trim();

        ArrayList<Transaction> allTransactions = readTransactions();

        System.out.println("\n--- Vendor Results: " + vendorSearch + "---");
        boolean found = false;

        System.out.println("Date\t\tTime\t\tDescription\t\tVendor\t\tAmount");

        for (Transaction t: allTransactions) {
            // Filter: Checks if the transaction's vendor (converted to lowercase)
            // contains the user's search term (converted to lowercase).
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