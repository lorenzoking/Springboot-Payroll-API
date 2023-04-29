package com.payroll.springboot;

import com.payroll.springboot.model.Employee;
import com.payroll.springboot.repository.EmployeeRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.JVM)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Value("${local.server.port}")
	private int port;

	private long savedEmployeeId;
	private long savedEmployeeId2;
	private long savedEmployeeId3;

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
		employee.setSalary(100000);
		employeeRepository.save(employee);
		this.savedEmployeeId = employee.getId();

		Employee employee2 = new Employee();
		employee2.setFirstName("Khariim");
		employee2.setLastName("Farrell");
		employee2.setJobRole("Manager");
		employee2.setSalary(200000);
		employeeRepository.save(employee2);
		this.savedEmployeeId2 = employee2.getId();

		Employee employee3 = new Employee();
		employee3.setFirstName("Jessie");
		employee3.setLastName("Bates");
		employee3.setJobRole("Backend Developer");
		employee3.setSalary(130000);
		employeeRepository.save(employee3);
		this.savedEmployeeId3 = employee3.getId();

	}

	@After
	public void tearDown() {
		employeeRepository.deleteAll();
	}

	@Test
	public void testGetAllEmployees() {
		ResponseEntity<String> response = restTemplate.getForEntity(getRootUrl() + "/api/employees", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		System.out.println(response.getBody());
	}

	@Test
	public void testGetEmployeeById() {
		Employee employee = restTemplate.getForObject(getRootUrl() + "/api/employees/" + this.savedEmployeeId, Employee.class);
		assertNotNull(employee);
		System.out.println(employee.getFirstName());
		assertEquals("John", employee.getFirstName());

	}

	@Test
	public void testGetEmployeesByLastName() {
		// Get employees by last name
		ResponseEntity<List<Employee>> response = restTemplate.exchange(
				getRootUrl() + "/api/employees/lastName/Doe",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>(){});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());

		List<Employee> employees = response.getBody();
		for (Employee employee : employees) {
			assertEquals("Doe", employee.getLastName());
		}
		System.out.println(response.getBody());
	}

	@Test
	public void testGetEmployeesByJobRole() {
		// Get employees by job role
		ResponseEntity<List<Employee>> response = restTemplate.exchange(
				getRootUrl() + "/api/employees/jobRole/Manager",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
				});

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());

		List<Employee> employees = response.getBody();
		for (Employee employee : response.getBody()) {
			assertEquals("Manager", employee.getJobRole());
		}
		System.out.println(response.getBody());
	}

	@Test
	public void testCreateEmployee() {
		Employee employee = new Employee();
		employee.setJobRole("Backend Engineer");
		employee.setFirstName("Lorenzo");
		employee.setLastName("King");
		employee.setSalary(130000);

		ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/employees", employee, Employee.class);
		assertNotNull(postResponse.getBody());
		System.out.println(postResponse.getBody());
		assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
		assertEquals("Lorenzo", postResponse.getBody().getFirstName());

	}


	@Test
	public void testCreateEmployeeWithInvalidSalary() {
		Employee createEmployee = new Employee();
		createEmployee.setFirstName("Artist");
		createEmployee.setLastName("King");
		createEmployee.setJobRole("Bulldog Trainer");
		createEmployee.setSalary(250000); // try to set an invalid salary

		ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/employees", createEmployee, Employee.class);
		System.out.println(postResponse.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, postResponse.getStatusCode());
	}

	@Test
	public void testCreateEmployeeWithRandomizedSalary() {
		Employee employee = new Employee();
		employee.setFirstName("Bob");
		employee.setLastName("Armon");
		employee.setJobRole("Surfer");

		ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootUrl() + "/api/employees", employee, Employee.class);
		assertNotNull(postResponse.getBody());
		assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
		assertEquals("Bob", postResponse.getBody().getFirstName());
		assertTrue(postResponse.getBody().getSalary() >= 80000 && postResponse.getBody().getSalary() <= 200000);
	}



	@Test
	public void testUpdateEmployee() {
		Employee employee = restTemplate.getForObject(getRootUrl() + "/api/employees/" + this.savedEmployeeId2, Employee.class);
		assertNotNull(employee);
		employee.setJobRole("Visionary");


		restTemplate.put(getRootUrl() + "/api/employees/" + this.savedEmployeeId2, employee);

		Employee updatedEmployee = restTemplate.getForObject(getRootUrl() + "/api/employees/" + this.savedEmployeeId2, Employee.class);
		assertNotNull(updatedEmployee);
		assertEquals("Khariim", updatedEmployee.getFirstName());
		assertEquals("Visionary", updatedEmployee.getJobRole());
	}


	@Test
	public void testDeletePost() {
		int id = 1;
		Employee employee = restTemplate.getForObject(getRootUrl() + "/api/employees/" + id, Employee.class);
		Assert.assertNotNull(employee);

		restTemplate.delete(getRootUrl() + "/employees/" + id);

		try {
			employee = restTemplate.getForObject(getRootUrl() + "/api/employees/" + id, Employee.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
