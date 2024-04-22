package org.example;

import org.example.service.EmployeeReportService;
import org.example.util.CsvEmployeeUtil;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String fileName = "employees.csv";
        if (args.length > 0) {
            fileName = args[0];
        }
        var employees = CsvEmployeeUtil.fetch(fileName);
        new EmployeeReportService(employees).printReport();
    }
}
