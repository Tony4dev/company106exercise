package org.example.util;

import org.example.model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeReportUtilTest {

    @Test
    void getOptimalSalaryDifference_theManagerIsUnderpayed() {
        // GIVEN
        Employee manager = new Employee(1L, "", "", 100, null);
        Employee employee2 = new Employee(2L, "", "", 100, 1L);
        Employee employee3 = new Employee(3L, "", "", 100, 1L);

        // WHEN
        double actualOptimalSalaryDifference =
                EmployeeReportUtil.getOptimalSalaryDifference(manager, Set.of(manager, employee2, employee3));

        // THEN
        Assertions.assertEquals(-20.0, actualOptimalSalaryDifference);
    }


    @Test
    void getOptimalSalaryDifference_theManagerIsOverpayed() {
        // GIVEN
        Employee manager = new Employee(1L, "", "", 200, null);
        Employee employee2 = new Employee(2L, "", "", 100, 1L);
        Employee employee3 = new Employee(3L, "", "", 100, 1L);

        // WHEN
        double actualOptimalSalaryDifference =
                EmployeeReportUtil.getOptimalSalaryDifference(manager, Set.of(manager, employee2, employee3));

        // THEN
        Assertions.assertEquals(50.0, actualOptimalSalaryDifference);
    }


    @Test
    void getOptimalSalaryDifference_theManagerIsPaidOptimally() {
        // GIVEN
        Employee manager = new Employee(1L, "", "", 130, null);
        Employee employee2 = new Employee(2L, "", "", 100, 1L);
        Employee employee3 = new Employee(3L, "", "", 100, 1L);

        // WHEN
        double actualOptimalSalaryDifference =
                EmployeeReportUtil.getOptimalSalaryDifference(manager, Set.of(manager, employee2, employee3));

        // THEN
        assertEquals(0.0, actualOptimalSalaryDifference);
    }

    @Test
    void getReportingLineLength() {
        // GIVEN
        Employee employee = new Employee(1L, "", "", 0, null);
        Employee employee2 = new Employee(2L, "", "", 0, 1L);
        Employee employee3 = new Employee(3L, "", "", 0, 2L);
        Employee employee4 = new Employee(4L, "", "", 0, 3L);

        // WHEN
        Map<Long, Employee> allEmployeesById =
                Map.of(1L, employee, 2L, employee2, 3L, employee3, 4L, employee4);
        int reportingLineLength1 = EmployeeReportUtil.getReportingLineLength(employee, allEmployeesById);
        int reportingLineLength2 = EmployeeReportUtil.getReportingLineLength(employee2, allEmployeesById);
        int reportingLineLength3 = EmployeeReportUtil.getReportingLineLength(employee3, allEmployeesById);
        int reportingLineLength4 = EmployeeReportUtil.getReportingLineLength(employee4, allEmployeesById);

        // THEN
        assertEquals(0, reportingLineLength1);
        assertEquals(1, reportingLineLength2);
        assertEquals(2, reportingLineLength3);
        assertEquals(3, reportingLineLength4);
    }
}