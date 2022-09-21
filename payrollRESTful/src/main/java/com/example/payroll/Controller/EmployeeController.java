package com.example.payroll.Controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.payroll.Exception.EmployeeNotFoundException;
import com.example.payroll.Model.*;
import com.example.payroll.ModelAssembler.EmployeeModelAssembler;
import com.example.payroll.Repository.EmployeeRepository;

@RestController
public class EmployeeController {
	
	private final EmployeeRepository repository;
	private final EmployeeModelAssembler assembler;
	
	public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
		this.repository = repository;  
		this.assembler = assembler;
	}
	
	// get all employees
	@GetMapping("/employee")
	public CollectionModel<EntityModel<EmployeeEntity>> getEmployees() {
		
//		List<EntityModel<EmployeeEntity>> employees = repository.findAll().stream()
//				.map(employee -> EntityModel.of(employee, 
//						linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId())).withSelfRel(),
//						linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees")))
//				.collect(Collectors.toList());
		List<EntityModel<EmployeeEntity>> employees = repository.findAll().stream()
				.map(assembler::toModel)  // assembler::toModel refer to method toModel in class assembler
				.collect(Collectors.toList());
				
		return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).getEmployees()).withSelfRel());
	}
	
	// create employee
	@PostMapping("/employee")
	public ResponseEntity<?> createEmployee(@RequestBody EmployeeEntity newEmployee) {
		
		EntityModel<EmployeeEntity> entityModel = assembler.toModel(repository.save(newEmployee));
		
		// to return the model-based version of the saved object.
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	
	// get employee by id
	@GetMapping("/employee/{id}")
	public EntityModel<EmployeeEntity> getEmployee(@PathVariable Long id) {
		
		EmployeeEntity employee = repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
		
//		return EntityModel.of(employee, 
//				linkTo(methodOn(EmployeeController.class).getEmployee(id)).withSelfRel(),
//				linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees"));
		
		return assembler.toModel(employee);
	}
	
	// update an employee
	@PutMapping("/employee/{id}")
	public ResponseEntity<?>  updateEmployee(@RequestBody EmployeeEntity newEmployee, @PathVariable Long id) {
		EmployeeEntity updatedEmployee = repository.findById(id)
				.map(employee -> {
					employee.setfirstName(newEmployee.getfirstName());
					employee.setlastName(newEmployee.getlastName());
					employee.setRole(newEmployee.getRole());
					return repository.save(employee);
				})
				.orElseGet(() ->{
					newEmployee.setId(id);
					return repository.save(newEmployee);
				});
		EntityModel<EmployeeEntity> entityModel = assembler.toModel(updatedEmployee);

		  return ResponseEntity //
		      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
		      .body(entityModel);
	}
	
	// delete an employee
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
		EmployeeEntity employee = repository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException(id));
		repository.delete(employee);
		
		return ResponseEntity.noContent().build();
	}
}
