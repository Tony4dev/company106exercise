package org.example.model;

/**
 * Employee record.
 *
 * @param id            id
 * @param firstName     first name of the employee
 * @param lastName      last name of the employee
 * @param salary        salary of the employee
 * @param managerId     the id of supervising manager
 */
public record Employee(long id, String firstName, String lastName, double salary, Long managerId) {
}
