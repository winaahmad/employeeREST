package com.example.payroll.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class EmployeeEntity {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;
	private String role;

	public EmployeeEntity() {}

	public EmployeeEntity(String firstName, String lastName, String role) {
		
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.role = role;
	}  
	public Long getId() {
	    return id;
	}
	
	public void setId(Long id) {
	    this.id = id;
	}
	
	@Column(name = "first_name", nullable = false)
	public String getfirstName() {
		return firstName;
	}
	
	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "last_name", nullable = false)
	public String getlastName() {
		return lastName;
	}
	
	public void setlastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "role", nullable = false)
	public String getRole() {
	    return role;
	}
	
	public void setRole(String role) {
	    this.role = role;
	} 
	
//	@Override
//	  public boolean equals(Object o) {
//
//	    if (this == o)
//	      return true;
//	    if (!(o instanceof EmployeeEntity))
//	      return false;
//	    EmployeeEntity employee = (EmployeeEntity) o;
////	    return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
////	        && Objects.equals(this.role, employee.role);
//	    return Objects.equals(this.id, employee.id) && Objects.equals(this.firstName, employee.firstName)
//	    		&& Objects.equals(this.lastName, employee.lastName) && Objects.equals(this.role, employee.role);
//	  }
//
//	  @Override
//	  public int hashCode() {
//	    return Objects.hash(this.id, this.firstName, this.lastName, this.role);
//	  }
//
//	  @Override
//	  public String toString() {
//		  return "Employee{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName
//			        + '\'' + ", role='" + this.role + '\'' + '}';	  }
}
