package com.payroll.springboot;

import com.payroll.springboot.model.Employee;
import com.payroll.springboot.repository.EmployeeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Value("${local.server.port}")
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Before
	public void setUp() {
		Employee employee = new Employee();
		employee.setFirstName("John");
		employee.setLastName("Doe");
		employee.setJobRole("Manager");
		employeeRepository.save(employee);
	}

	@Test
	public void testGetAllEmployees() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/employees",
				HttpMethod.GET, entity, String.class);

		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetEmployeeById() {
		Employee employee = restTemplate.getForObject(getRootUrl() + "/employees/1", Employee.class);
		System.out.println(employee.getFirstName());
		Assert.assertNotNull(employee);
	}

	@Test
	public void testCreateEmployee() {
		Employee employee = new Employee();
		employee.setJobRole("Software Developer");
		employee.setFirstName("Lorenzo");
		employee.setLastName("King");

		ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/employees", employee, Employee.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}
//
//	@Test
//	public void testUpdatePost() {
//		int id = 1;
//		Employee employee = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Employee.class);
//		employee.setFirstName("Jenna");
//		employee.setLastName("King");
//
//		restTemplate.put(getRootUrl() + "/employees/" + id, employee);
//
//		Employee updatedEmployee = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Employee.class);
//		Assert.assertNotNull(updatedEmployee);
//	}
//
//	@Test
//	public void testDeletePost() {
//		int id = 2;
//		Employee employee = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Employee.class);
//		Assert.assertNotNull(employee);
//
//		restTemplate.delete(getRootUrl() + "/employees/" + id);
//
//		try {
//			employee = restTemplate.getForObject(getRootUrl() + "/employees/" + id, Employee.class);
//		} catch (final HttpClientErrorException e) {
//			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
//		}
//	}

}
