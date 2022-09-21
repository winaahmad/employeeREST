package com.example.payroll.ModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.payroll.Controller.EmployeeController;
import com.example.payroll.Model.EmployeeEntity;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<EmployeeEntity, EntityModel<EmployeeEntity>> {
	
	@Override
	public EntityModel<EmployeeEntity> toModel(EmployeeEntity employee) {
		
		return EntityModel.of(employee, 
				linkTo(methodOn(EmployeeController.class).getEmployee(employee.getId())).withSelfRel(),
				linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees"));
	}
}
