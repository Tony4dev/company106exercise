package org.example.service;

import org.example.helper.EmployeeReportCalculationsHelper;
import org.example.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeReportServiceTest {

    private static final Employee EMPLOYEE_1_STUB =
            new Employee(1L, "Pepa", "Novak", 0.0, null);
    private static final Employee EMPLOYEE_2_STUB =
            new Employee(2L, "Karel", "Predak", 0.0, null);

    private final EmployeeReportService employeeReportService =
            new EmployeeReportService(
                    Map.of(
                            EMPLOYEE_1_STUB.id(), EMPLOYEE_1_STUB,
                            EMPLOYEE_2_STUB.id(), EMPLOYEE_2_STUB
                    ),
                    new EmployeeReportCalculationsHelperMock()
            );

    private final PrintStream standardOut = System.out;

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void printReport() {
        // GIVEN
        // WHEN
        employeeReportService.printReport();

        // THEN
        String expectedOutput = """
                Following managers should earn more:
                Pepa Novak should earn 20.0 more.
                
                Following managers should earn less:
                Karel Predak should earn 50.0 less.
                
                Following employees have too long reporting line:
                Karel Predak reporting line is 6, which is 2 more than optimal.""";
        String actualOutput = outputStreamCaptor.toString();
        assertStringsEqualsIgnoreWhitespace(expectedOutput, actualOutput);
    }

    public static class EmployeeReportCalculationsHelperMock extends EmployeeReportCalculationsHelper {

        public static final double UNDERPAYED_SALARY_DIFFERENCE = -20.0;
        public static final double OVERPAYED_SALARY_DIFFERENCE = 50.0;
        public static final int OK_REPORTING_LINE_LENGTH = 1;
        public static final int NOK_REPORTING_LINE_LENGTH = 6;

        @Override
        public double getOptimalSalaryDifference(Employee employee, Collection<Employee> allEmployees) {
            return employee.id() == EMPLOYEE_1_STUB.id() ? UNDERPAYED_SALARY_DIFFERENCE : OVERPAYED_SALARY_DIFFERENCE;
        }

        @Override
        public int getReportingLineLength(Employee employee, Map<Long, Employee> allEmployeesById) {
            return employee.id() == EMPLOYEE_1_STUB.id() ? OK_REPORTING_LINE_LENGTH : NOK_REPORTING_LINE_LENGTH;
        }
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    private void assertStringsEqualsIgnoreWhitespace(String one, String two) {
        assertEquals(one.replaceAll("\\s+", ""), two.replaceAll("\\s+", ""));
    }
}