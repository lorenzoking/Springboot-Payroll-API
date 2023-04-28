package com.payroll.springboot.controller;

import com.payroll.springboot.exception.ResourceNotFoundException;
import com.payroll.springboot.model.Employee;
import com.payroll.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class EmployeeController {

  @Autowired
  private EmployeeRepository employeeRepository;

  /**
   * Get all employees list.
   *
   * @return the list
   */
  @GetMapping("/employees")
  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }


  /**
   * Gets employees by id.
   *
   * @param employeeId the employee id
   * @return the employees by id
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/employees/{id}")
  public ResponseEntity<Employee> getEmployeesById(@PathVariable(value = "id") Long employeeId)
      throws ResourceNotFoundException {
    Employee employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found on :: " + employeeId));
    return ResponseEntity.ok().body(employee);
  }

  /**
   * Create employee.
   *
   * @param employee the employee
   * @return the employee
   */
  @PostMapping("/employees")
  public Employee createEmployee(@Valid @RequestBody Employee employee) {
    return employeeRepository.save(employee);
  }

  /**
   * Update employee response entity.
   *
   * @param employeeId the employee id
   * @param employeeDetails the employee details
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @PutMapping("/employees/{id}")
  public ResponseEntity<Employee> updateEmployee(
      @PathVariable(value = "id") Long employeeId, @Valid @RequestBody Employee employeeDetails)
      throws ResourceNotFoundException {

    Employee employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found on :: " + employeeId));

    employee.setJobRole(employeeDetails.getJobRole());
    employee.setLastName(employeeDetails.getLastName());
    employee.setFirstName(employeeDetails.getFirstName());
    employee.setUpdatedAt(new Date());
    final Employee updatedEmployee = employeeRepository.save(employee);
    return ResponseEntity.ok(updatedEmployee);
  }

  /**
   * Delete employee map.
   *
   * @param employeeId the employee id
   * @return the map
   * @throws Exception the exception
   */
  @DeleteMapping("/employees/{id}")
  public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws Exception {
    Employee employee =
        employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found on :: " + employeeId));

    employeeRepository.delete(employee);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
