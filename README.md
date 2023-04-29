# Employee Payroll REST API using Spring Boot, JPA, H2, and Docker

## Steps to Setup

**1. Clone the application**

```bash
https://github.com/lorenzoking/spring-boot-payroll-api.git
```


**2. Run the following commands to build then start a docker container that houses the API**

```bash
make payroll-api
./run-api.sh 1.0.0
```

You can also run the app outside of the docker container by using -

```bash
mvn spring-boot:run
```

The app will start running at <http://localhost:8080>.

## Rest APIs

    GET /api/employees
    
    POST /api/epmloyees
    
    GET /api/employees/{employeeId}
    
    PUT /api/employees/{employeeId}
    
    DELETE /api/employees/{employeeId}

