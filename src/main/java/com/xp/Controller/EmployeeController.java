package com.xp.Controller;

import com.xp.Model.Employee;
import com.xp.Service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employees/")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return this.employeeService.findAll();
    }

    @GetMapping("{adminId}")
    public Employee getEmployeeById(@PathVariable Long adminId) {
        return this.employeeService.findById(adminId);
    }

    @GetMapping("by-name/{employeeName}")
    public List<Employee> getEmployeeByName(@PathVariable String employeeName) {
        return this.employeeService.findAllByName(employeeName);
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return this.employeeService.addEmployee(employee);
    }

    @PutMapping
    public Employee updateEmployee(@RequestBody Employee employee) {
        return this.employeeService.updateEmployee(employee);
    }

    @DeleteMapping("{adminId}")
    public void deleteEmployee(@PathVariable Long adminId) {
        this.employeeService.deleteEmployee(adminId);
    }
}
