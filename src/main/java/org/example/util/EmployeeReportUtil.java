package org.example.util;

import org.example.model.Employee;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class EmployeeReportUtil {

    public static double getOptimalSalaryDifference(Employee employee, Collection<Employee> allEmployees) {
        double subordinatesAverageSalary = getAverageSalary(getSubordinates(employee, allEmployees));
        double lowerLimit = subordinatesAverageSalary * 1.2;
        double upperLimit = subordinatesAverageSalary * 1.5;

        if (employee.salary() < lowerLimit) {
            return employee.salary() - lowerLimit;
        } else if (employee.salary() > upperLimit) {
            return employee.salary() - upperLimit;
        }
        return 0;
    }

    public static int getReportingLineLength(Employee employee, Map<Long, Employee> allEmployeesById) {
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

    private EmployeeReportUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated.");
    }
}
