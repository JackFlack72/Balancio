package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AccountingTransactionApplication {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

       ArrayList<Employee> employees = readEmployees();

        for (Employee employee: employees) {
            System.out.println(employee);
        }

    }

    private static ArrayList<Employee> readEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();

        try {
            // create a FileReader object connected to the File
            FileReader fileReader = new FileReader("employees.csv");
            // create a BufferedReader to manage input stream
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            //skip header line
            bufferedReader.readLine();


            // read until there is no more data
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                String[] parts = line.split(Pattern.quote("|"));


                Employee employee = new Employee();

                String employeeIdAsString = parts[0];
                int employeeId = Integer.parseInt(employeeIdAsString);
                employee.setEmployeeId(employeeId);
//                OR
//                  employee.setEmployeeId(Integer.parseInt(parts[0]));

                String name = parts[1];
                employee.setName(name);
//                OR
//                employee.setName(parts[1]);


                String hoursWorkedAsString = parts[2];
                double hoursWorked = Double.parseDouble(hoursWorkedAsString);
                employee.setHoursWorked(hoursWorked);
//                OR
//                employee.setHoursWorked(Double.parseDouble(parts[2]));


                String payRateAsString = parts[3];
                double payRate = Double.parseDouble(payRateAsString);
                employee.setPayRate(payRate);
//                OR
//                employee.setPayRate(Double.parseDouble(parts[3]));

                employees.add(employee) ;
//                System.out.println(employee);

            }
            // close the stream and release the resources
            bufferedReader.close();
        } catch (IOException e) {
            // display stack trace if there was an error
            e.printStackTrace();
        }

        return employees;


    }
}
