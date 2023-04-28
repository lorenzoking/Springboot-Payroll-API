

# REST API using Spring Boot, H2, and JPA

## Steps to Setup

**1. Clone the application**

```bash
https://github.com/lorenzoking/spring-boot-payroll-api.git
```


**2. Build and run the app using maven**

```bash
mvn package
java -jar target/spring-boot-payroll-api-0.0.1-SNAPSHOT.jar

```

You can also run the app without packaging it by using -

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

