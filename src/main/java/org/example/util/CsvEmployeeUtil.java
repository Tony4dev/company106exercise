package org.example.util;

import org.example.exception.ApplicationException;
import org.example.model.Employee;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Provides utilities for employees fetch from csv file.
 */
public class CsvEmployeeUtil {

    public static final String CSV_COLUMN_SEPARATOR = ",";
    public static final int ID_COLUMN_INDEX = 0;
    public static final int FIRST_NAME_COLUMN_INDEX = 1;
    public static final int LAST_NAME_COLUMN_INDEX = 2;
    public static final int SALARY_COLUMN_INDEX = 3;
    public static final int MANAGER_ID_COLUMN_INDEX = 4;

    /**
     * fetches employees from given file
     *
     * @param csvFileNameOrPath       employees csv file name or path
     * @return                        employees fetched in an id-employee map
     */
    public static Map<Long, Employee> fetchEmployees(String csvFileNameOrPath) {
        Map<Long, Employee> result = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(csvFileNameOrPath))) {
            verifyNextRowExists(csvFileNameOrPath, scanner);
            skipCsvHeaderRow(scanner);
            verifyNextRowExists(csvFileNameOrPath, scanner);

            while (scanner.hasNext())
            {
                var employeeOptional = CsvEmployeeUtil.createEmployee(scanner.next());
                if (employeeOptional.isPresent()) {
                    var employee = employeeOptional.get();
                    result.put(employee.id(), employee);
                }
            }
        } catch (FileNotFoundException e) {
            throw new ApplicationException("File " + csvFileNameOrPath + " not found", e);
        }
        return result;
    }

    private static void verifyNextRowExists(String csvFileNameOrPath, Scanner scanner) {
        if (!scanner.hasNext()) {
            throw new ApplicationException(String.format("File %s doesn't contain any data.", csvFileNameOrPath));
        }
    }

    private static void skipCsvHeaderRow(Scanner scanner) {
        scanner.nextLine();
    }

    private static Optional<Employee> createEmployee(String csvLine) {
        if (csvLine == null) {
            return Optional.empty();
        }
        try {
            String[] data = csvLine.split(CSV_COLUMN_SEPARATOR);
            long id = Long.parseLong(data[ID_COLUMN_INDEX]);
            String firstName = data[FIRST_NAME_COLUMN_INDEX];
            String lastName = data[LAST_NAME_COLUMN_INDEX];
            double salary = Double.parseDouble(data[SALARY_COLUMN_INDEX]);

            Long managerId = null;
            if (data.length > MANAGER_ID_COLUMN_INDEX) {
                managerId = Long.parseLong(data[MANAGER_ID_COLUMN_INDEX]);
            }

            return Optional.of(new Employee(id, firstName, lastName, salary, managerId));
        } catch (NumberFormatException e) {
            throw new ApplicationException(String.format("Invalid number on line \"%s\".", csvLine), e);
        } catch (IndexOutOfBoundsException e) {
            throw new ApplicationException(String.format("Invalid format of line \"%s\".", csvLine), e);
        }
    }

    private CsvEmployeeUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }
}
