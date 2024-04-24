package org.example.service;

import org.example.model.Employee;
import org.example.model.EmployeeReport;
import org.example.helper.EmployeeReportCalculationsHelper;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service that calculates values for an employees report and prints out the result.
 */
public class EmployeeReportService {
    public static final int MAX_OPTIMAL_REPORTING_LINE_LENGTH = 4;

    private final Map<Long, Employee> employees;
    private final EmployeeReportCalculationsHelper calculationsHelper;

    /**
     * Creates the service.
     *
     * @param employees     employees that should be included in report
     */
    public EmployeeReportService(Map<Long, Employee> employees, EmployeeReportCalculationsHelper calculationsHelper) {
        this.employees = employees;
        this.calculationsHelper = calculationsHelper;
    }

    /**
     * creates report and prints it to the standard output.
     */
    public void printReport() {
        Set<EmployeeReport> employeeReports = createReports();

        printUnderpayedEmployees(employeeReports);
        printEmptyLine();
        printOverpayedEmployees(employeeReports);
        printEmptyLine();
        printEmployeesWithTooLongReportingLine(employeeReports);
    }

    private Set<EmployeeReport> createReports() {
        return employees.values().stream()
                .map(employee ->
                        new EmployeeReport(
                                employee,
                                calculationsHelper.getOptimalSalaryDifference(employee, employees.values()),
                                calculationsHelper.getReportingLineLength(employee, employees)
                        )
                )
                .collect(Collectors.toSet());
    }

    private static void printUnderpayedEmployees(Set<EmployeeReport> employeeReports) {
        System.out.println("Following managers should earn more:");
        employeeReports.stream()
                .filter(r -> r.optimalPayDifference() < 0)
                .forEach(
                        r -> printFormattedLine(
                                "%s %s should earn %s more.",
                                r.employee().firstName(),
                                r.employee().lastName(),
                                Math.abs(r.optimalPayDifference())
                        )
                );
    }

    private static void printOverpayedEmployees(Set<EmployeeReport> employeeReports) {
        System.out.println("Following managers should earn less:");

        employeeReports.stream()
                .filter(r -> r.optimalPayDifference() > 0)
                .forEach(
                        r -> printFormattedLine(
                                "%s %s should earn %s less.",
                                r.employee().firstName(),
                                r.employee().lastName(),
                                r.optimalPayDifference()
                        )
                );
    }

    private static void printEmployeesWithTooLongReportingLine(Set<EmployeeReport> employeeReports) {
        System.out.println("Following employees have too long reporting line:");

        employeeReports.stream()
                .filter(r -> r.reportingLineLength() > MAX_OPTIMAL_REPORTING_LINE_LENGTH)
                .forEach(
                        r -> printFormattedLine(
                                "%s %s reporting line is %d, which is %s more than optimal.",
                                r.employee().firstName(),
                                r.employee().lastName(),
                                r.reportingLineLength(),
                                r.reportingLineLength() - MAX_OPTIMAL_REPORTING_LINE_LENGTH
                        )
                );
    }

    private static void printFormattedLine(String format, Object... args) {
        System.out.format(format, args);
        printEmptyLine();
    }

    private static void printEmptyLine() {
        System.out.println();
    }

}
