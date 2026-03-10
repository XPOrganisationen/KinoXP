package com.xp.Service;

import com.xp.Model.Employee;
import com.xp.Model.Movie;

import java.util.List;

public interface EmployeeService {
    public List<Employee> findAll();
    public List<Employee> findAllByName(String name);
    public Employee findEmployeeByUsername(String username);
    public Employee findById(Long adminId);
    public Employee updateEmployee(Employee employee);
    public Employee addEmployee(Employee employee);
    public void deleteEmployee(Long adminId);
}
