package org.example;

import org.example.exception.ApplicationException;
import org.example.service.EmployeeReportService;
import org.example.util.CsvEmployeeUtil;
import org.example.helper.EmployeeReportCalculationsHelper;

/**
 * Provides main method for application run.
 */
public class App {

    public static final String DEFAULT_EMPLOYEES_CSV_FILE_NAME = "employees.csv";
    public static final int EMPLOYEES_CSV_FILE_NAME_ARG_INDEX = 0;

    public static void main(String[] args) {
        String fileName = DEFAULT_EMPLOYEES_CSV_FILE_NAME;
        if (args.length > EMPLOYEES_CSV_FILE_NAME_ARG_INDEX) {
            fileName = args[EMPLOYEES_CSV_FILE_NAME_ARG_INDEX];
        }
        try {
            var employees = CsvEmployeeUtil.fetchEmployees(fileName);
            new EmployeeReportService(employees, new EmployeeReportCalculationsHelper()).printReport();
        } catch (ApplicationException e) {
            System.out.println(e.getMessage());
        }
    }
}
