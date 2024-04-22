package org.example.util;

import org.example.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}