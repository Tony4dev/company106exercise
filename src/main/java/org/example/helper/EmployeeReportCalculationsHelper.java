package org.example.helper;

import org.example.model.Employee;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Provides helpers for employees report calculations.
 */
public class EmployeeReportCalculationsHelper {

    public static final double PERCENT_120 = 1.2;
    public static final double PERCENT_150 = 1.5;

    /**
     * Gets salary difference between optimal salary and current salary.
     *
     * @param employee          employee whose salary we are checking
     * @param allEmployees      all company employees
     * @return                  the difference - negative number means the employee is underpayed,
     *                          positive means that the employee is overpayed
     */
    public double getOptimalSalaryDifference(Employee employee, Collection<Employee> allEmployees) {
        double subordinatesAverageSalary = getAverageSalary(getSubordinates(employee, allEmployees));
        double lowerLimit = subordinatesAverageSalary * PERCENT_120;
        double upperLimit = subordinatesAverageSalary * PERCENT_150;

        if (employee.salary() < lowerLimit) {
            return employee.salary() - lowerLimit;
        } else if (employee.salary() > upperLimit) {
            return employee.salary() - upperLimit;
        }
        return 0;
    }

    /**
     * Gets reporting line length between employee and CEO (top employee in the structure).
     *
     * @param employee              employee whose reporting line we are checking
     * @param allEmployeesById      all company employees in an id-employee map
     * @return                      the length of the reporting line.
     */
    public int getReportingLineLength(Employee employee, Map<Long, Employee> allEmployeesById) {
        var currentEmployeeInLine = employee;
        var result = 0;
        while (currentEmployeeInLine.managerId() != null) {
            currentEmployeeInLine = allEmployeesById.get(currentEmployeeInLine.managerId());
            result++;
        }
        return result;
    }

    private static double getAverageSalary(Set<Employee> employeesSet) {
        double sumOfSalaries = employeesSet.stream().map(Employee::salary).reduce(0.0, Double::sum);
        return sumOfSalaries / employeesSet.size();
    }

    private static Set<Employee> getSubordinates(Employee manager, Collection<Employee> allEmployees) {
        return allEmployees.stream().filter(e -> Objects.equals(manager.id(), e.managerId())).collect(Collectors.toSet());
    }
}
