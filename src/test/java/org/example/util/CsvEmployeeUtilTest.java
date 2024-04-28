package org.example.util;

import org.example.exception.ApplicationException;
import org.example.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CsvEmployeeUtilTest {

    @Test
    void fetchEmployees() {
        // GIVEN
        String file = CsvEmployeeUtilTest.class.getResource("/employees-test.csv").getFile();

        // WHEN
        Map<Long, Employee> data = CsvEmployeeUtil.fetchEmployees(file);

        // THEN
        assertEquals(2, data.values().size());

        long joeDoeId = 123L;
        Employee joeDoe = new Employee(joeDoeId, "Joe", "Doe", 60000, null);
        long martinChekovId = 124L;
        Employee martinChekov = new Employee(martinChekovId, "Martin", "Chekov", 45000, joeDoeId);

        assertEquals(data.get(joeDoeId), joeDoe);
        assertEquals(data.get(martinChekovId), martinChekov);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/employees-test-invalid-id.csv", "/employees-test-invalid-manager-id.csv", "/employees-test-invalid-salary.csv"})
    void fetchEmployees_numberFormatIsWrong(String fileName) {
        // GIVEN
        String file = CsvEmployeeUtilTest.class.getResource(fileName).getFile();

        // WHEN
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> CsvEmployeeUtil.fetchEmployees(file));

        // THEN
        String expectedMessageStart = "Invalid number on line";
        assertTrue(exception.getMessage().startsWith(expectedMessageStart));
    }


    @Test
    void fetchEmployees_numberFormatIsWrong() {
        // GIVEN
        String file = CsvEmployeeUtilTest.class
                .getResource("/employees-test-invalid-number-of-columns.csv").getFile();

        // WHEN
        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> CsvEmployeeUtil.fetchEmployees(file));

        // THEN
        String expectedMessageStart = "Invalid format of line";
        assertTrue(exception.getMessage().startsWith(expectedMessageStart));
    }
}