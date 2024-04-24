package org.example.model;

/**
 * Employee report.
 *
 * @param employee                  employee to whom this report is relevant
 * @param optimalPayDifference      difference between optimal salary range and the current salary
 * @param reportingLineLength       length of reporting between the employee and the CEO of the company.
 */
public record EmployeeReport(Employee employee, double optimalPayDifference, int reportingLineLength) {
}
