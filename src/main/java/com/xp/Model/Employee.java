package com.xp.Model;

import jakarta.persistence.*;

@Entity
@Table(name="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String employeeUsername;
    private String employeeName;
    private String employeePassword;
    private String employeeRole;

    public Employee() {
    }

    public Employee(String username, String name, String password, String role) {
        this.employeeUsername = username;
        this.employeeName = name;
        this.employeePassword = password;
        this.employeeRole = role;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long adminId) {
        this.employeeId = adminId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String adminName) {
        this.employeeName = adminName;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String adminPassword) {
        this.employeePassword = adminPassword;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String adminUsername) {
        this.employeeUsername = adminUsername;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

}