package org.example.util;

import org.example.model.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CsvEmployeeUtil {

    public static final String CSV_COLUMN_SEPARATOR = ",";
    public static final int ID_COLUMN_INDEX = 0;
    public static final int FIRST_NAME_COLUMN_INDEX = 1;
    public static final int LAST_NAME_COLUMN_INDEX = 2;
    public static final int SALARY_COLUMN_INDEX = 3;
    public static final int MANAGER_ID_COLUMN_INDEX = 4;

    public static Map<Long, Employee> fetchEmployees(String csvFileName) {
        Map<Long, Employee> result = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(csvFileName))) {
            skipCsvHeaderRow(scanner);
            while (scanner.hasNext())
            {
                var employee = CsvEmployeeUtil.createEmployee(scanner.next());
                result.put(employee.id(), employee);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File " + csvFileName + " not found", e);
        }
        return result;
    }

    private static void skipCsvHeaderRow(Scanner scanner) {
        scanner.nextLine();
    }

    private static Employee createEmployee(String csvLine) {
        String[] data = csvLine.split(CSV_COLUMN_SEPARATOR);
        long id = Long.parseLong(data[ID_COLUMN_INDEX]);
        String firstName = data[FIRST_NAME_COLUMN_INDEX];
        String lastName = data[LAST_NAME_COLUMN_INDEX];
        double salary = Double.parseDouble(data[SALARY_COLUMN_INDEX]);

        Long managerId = null;
        if (data.length > MANAGER_ID_COLUMN_INDEX) {
            managerId = Long.parseLong(data[MANAGER_ID_COLUMN_INDEX]);
        }

        return new Employee(id, firstName, lastName, salary, managerId);
    }

    private CsvEmployeeUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }
}
