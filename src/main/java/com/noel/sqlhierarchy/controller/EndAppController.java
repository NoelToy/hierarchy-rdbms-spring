package com.noel.sqlhierarchy.controller;

import com.noel.sqlhierarchy.dto.EmployeeModel;
import com.noel.sqlhierarchy.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "endApp")
public class EndAppController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping(path = "/getAllEmployees")
    public ResponseEntity<List<EmployeeModel>> getAllEmployees(){
        try {
            return ResponseEntity.ok(employeeService.getAllEmployees());
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/getAllEmployeeHierarchyByManagerId/{managerId}")
    public ResponseEntity<List<EmployeeModel>> getAllEmployeeHierarchyByManagerId(@PathVariable("managerId") Integer managerId){
        try {
            return ResponseEntity.ok(employeeService.getEmployeeHierarchy(managerId));
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/getWholeEmployeeHierarchy")
    public ResponseEntity<List<EmployeeModel>> getWholeEmployeeHierarchy(){
        try {
            return ResponseEntity.ok(employeeService.getWholeEmployeeHierarchy());
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/getAllManagers")
    public ResponseEntity<List<EmployeeModel>> getAllManager(){
        try {
            return ResponseEntity.ok(employeeService.getAllManagers());
        }
        catch (Exception exception){
            return ResponseEntity.internalServerError().build();
        }
    }

}
