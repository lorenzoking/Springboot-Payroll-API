package com.payroll.springboot.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "job_role", nullable = false)
    private String jobRole;

    @Min(80000)
    @Max(200000)
    @Column(name = "salary")
    private int salary;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;


    /**
   * Gets id.
   *
   * @return the id
   */
  public long getId() {
        return id;
    }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(long id) {
        this.id = id;
    }

  /**
   * Gets first name.
   *
   * @return the first name
   */
  public String getFirstName() {
        return firstName;
    }

  /**
   * Sets first name.
   *
   * @param firstName the first name
   */
  public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

  /**
   * Gets last name.
   *
   * @return the last name
   */
  public String getLastName() {
        return lastName;
    }

  /**
   * Sets last name.
   *
   * @param lastName the last name
   */
  public void setLastName(String lastName) {
        this.lastName = lastName;
    }

  /**
   * Gets jobRole.
   *
   * @return the jobRole
   */
  public String getJobRole() {
        return jobRole;
    }

  /**
   * Sets jobRole.
   *
   * @param jobRole the jobRole
   */
  public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    /**
     * Gets salary.
     *
     * @return the salary
     */
  public int getSalary() {
        return salary;
  }

    /**
     * Sets salary.
     *
     * @param salary the salary
     */
  public void setSalary(Integer salary) {this.salary = salary;}

    /**
     * Gets created at.
     *
     * @return the created at
     */

  public Date getCreatedAt() {
        return createdAt;
    }

  /**
   * Sets created at.
   *
   * @param createdAt the created at
   */
  public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


  /**
   * Gets updated at.
   *
   * @return the updated at
   */
  public Date getUpdatedAt() {
        return updatedAt;
    }

  /**
   * Sets updated at.
   *
   * @param updatedAt the updated at
   */
  public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jobRole='" + jobRole + '\'' +
                ", salary=" + salary + '\'' +
                ", createdAt=" + createdAt + '\'' +
                ", updatedAt=" + updatedAt + '\'' +
                '}';
    }


}
