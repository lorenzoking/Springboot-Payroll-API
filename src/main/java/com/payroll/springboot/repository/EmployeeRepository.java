package com.payroll.springboot.repository;

import com.payroll.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByJobRole(String jobRole);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);
}
