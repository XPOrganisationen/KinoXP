package com.xp.Service;

import com.xp.Exceptions.DataAccessException;
import com.xp.Exceptions.EntityDoesNotExistException;
import com.xp.Model.Employee;
import com.xp.Model.Movie;
import com.xp.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> findAllByName(String name) {
        return employeeRepository.findAllByEmployeeNameContainingIgnoreCase(name);
    }

    @Override
    public Employee findById(Long adminId) {
        var employee =  employeeRepository.findById(adminId);
        return employeeRepository.findById(adminId)
                .orElseThrow(() -> new EntityDoesNotExistException("No Employee found with ID " + adminId));
    }

    @Override
    public Employee findEmployeeByUsername(String username) {
        return employeeRepository.findEmployeeByEmployeeUsername(username);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        if (!employeeRepository.existsById(employee.getEmployeeId())) {
            throw new EntityDoesNotExistException("No Employee found with ID " + employee.getEmployeeId());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long adminId) {
        if (!employeeRepository.existsById(adminId)) {
            throw new EntityDoesNotExistException("No Employee found with ID " + adminId);
        }

        employeeRepository.deleteById(adminId);
    }

    public boolean login(String username, String password) {
        Employee employee = employeeRepository.findEmployeeByEmployeeUsername(username);
        if (employee.getEmployeeRole().equals("admin")) {
            return employee.getEmployeePassword().equals(password);
        }
        throw new DataAccessException("That employee does not have admin privileges");
    }

}

