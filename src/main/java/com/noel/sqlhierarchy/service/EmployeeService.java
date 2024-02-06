package com.noel.sqlhierarchy.service;

import com.noel.sqlhierarchy.datamodel.Employee;
import com.noel.sqlhierarchy.dto.EmployeeModel;
import com.noel.sqlhierarchy.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    private final Function<List<Employee>,List<EmployeeModel>> dtoConvertor;

    public EmployeeService(){
        dtoConvertor = (List<Employee> employees)->{
            List<EmployeeModel> employeeModels = new ArrayList<>();
            employees.forEach(employee -> {
                employeeModels.add(new EmployeeModel(employee.getId(),employee.getFirstName(),employee.getLastName(),employee.getManagerId()));
            });
            return employeeModels;
        };
    }

    public List<EmployeeModel> getAllEmployees(){
        return dtoConvertor.apply(employeeRepository.findAll());
    }

    public List<EmployeeModel> getEmployeeHierarchy(int rootId){
        List<Employee> employeeHierarchyById = employeeRepository.getEmployeeHierarchyById(rootId);
        List<Employee> employeeList = employeeHierarchyById.stream().filter(employee -> employee.getId() == rootId).toList();
        List<EmployeeModel> result = new ArrayList<>();
        employeeList.forEach(employee -> {
            EmployeeModel employeeModel = new EmployeeModel(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getManagerId());
            employeeModel.setChildren(buildHierarchyOnLevel(employeeHierarchyById,employee.getId()));
            result.add(employeeModel);

        });
        return result;
        //return buildHierarchy(employeeRepository.getEmployeeHierarchyById(rootId),null);
    }

    public List<EmployeeModel> getWholeEmployeeHierarchy(){
        return buildHierarchy(employeeRepository.getWholeEmployeeHierarchy(),null);
    }

    public List<EmployeeModel> getAllManagers(){
        return dtoConvertor.apply(employeeRepository.findByManagerId(null));
    }

    private List<EmployeeModel> buildHierarchy(List<Employee> employees,Integer mangerId){
        List<EmployeeModel> result = new ArrayList<>();
        employees
                .stream()
                .filter(employee -> mangerId==null && employee.getManagerId()==null || employee.getManagerId()!=null && employee.getManagerId().equals(mangerId))
                .forEach(employee -> {
                    EmployeeModel employeeModel = new EmployeeModel(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getManagerId());
                    employeeModel.setChildren(buildHierarchy(employees,employee.getId()));
                    result.add(employeeModel);
                });
        return result;
    }

    private List<EmployeeModel> buildHierarchyOnLevel(List<Employee> employees,Integer mangerId){
        List<EmployeeModel> result = new ArrayList<>();
        employees
                .stream()
                .filter(employee -> employee.getManagerId()!=null && employee.getManagerId().equals(mangerId))
                .forEach(employee -> {
                    EmployeeModel employeeModel = new EmployeeModel(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getManagerId());
                    employeeModel.setChildren(buildHierarchy(employees,employee.getId()));
                    result.add(employeeModel);
                });
        return result;
    }
}
