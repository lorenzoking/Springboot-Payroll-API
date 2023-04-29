package com.payroll.springboot.controller;

import com.payroll.springboot.exception.ResourceNotFoundException;
import com.payroll.springboot.model.Employee;
import com.payroll.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


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
    return ResponseEntity.status(HttpStatus.OK).body(employee);
  }

  /**
   * Create employee.
   *
   * @param employee the employee
   * @return the employee
   */
  @PostMapping("/employees")
  public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee employee) {
    //If salary is set, check if its in range. If salary is not set then we will randomize the salary
    double salary = employee.getSalary();
    boolean isSalarySet = (salary != 0);
    if (isSalarySet && (employee.getSalary() < 80000) || (employee.getSalary() > 200000)) {
      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put("message", "Salary must be between 80000 and 200000");
      return ResponseEntity.badRequest().body(errorResponse);
    }

    if (isSalarySet) {
      employee.setSalary((int) employee.getSalary());
    } else {
      Random rand = new Random();
      int minSalary = 80000;
      int maxSalary = 200000;
      int randomSalary = rand.nextInt(maxSalary - minSalary) + minSalary;
      employee.setSalary(randomSalary);
    }
    Employee savedEmployee = employeeRepository.save(employee);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
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
  public ResponseEntity<Object> updateEmployee(
          @PathVariable(value = "id") Long employeeId, @Valid @RequestBody Employee employeeDetails)
          throws ResourceNotFoundException {
    Employee employee =
            employeeRepository
                    .findById(employeeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found on :: " + employeeId));

    // Checks if salary was set, if it was then verify validity, if not then do nothing
    double salary = employeeDetails.getSalary();
    boolean isSalarySet = (salary != 0);
    if (isSalarySet && (employeeDetails.getSalary() < 80000 || employeeDetails.getSalary() > 200000)) {
      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put("message", "Salary must be between 80000 and 200000");
      return ResponseEntity.badRequest().body(errorResponse);
    }

    employee.setJobRole(employeeDetails.getJobRole());
    employee.setLastName(employeeDetails.getLastName());
    employee.setFirstName(employeeDetails.getFirstName());
    if (isSalarySet) {
      employee.setSalary((int) employeeDetails.getSalary());
    }
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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
    Map<String, Object> errors = new HashMap<>();
    errors.put("message", "Input validation error");
    errors.put("errors", ex.getBindingResult().getAllErrors());
    return ResponseEntity.badRequest().body(errors);
  }
}
