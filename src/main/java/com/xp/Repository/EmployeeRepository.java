package com.xp.Repository;

import com.xp.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository <Employee, Long> {
    Employee findEmployeeByEmployeeUsername(String username);

    List<Employee> findAllByEmployeeNameContainingIgnoreCase(String name);

}

