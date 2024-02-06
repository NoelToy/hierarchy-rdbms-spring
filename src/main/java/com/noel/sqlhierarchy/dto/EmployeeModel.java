package com.noel.sqlhierarchy.dto;


import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    private int id;
    private String firstName;
    private String lastName;
    private Integer managerId;
    private List<EmployeeModel> children;

    public EmployeeModel(int id, String firstName, String lastName, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerId = managerId;
        children = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public List<EmployeeModel> getChildren() {
        return children;
    }

    public void setChildren(List<EmployeeModel> children) {
        this.children = children;
    }
}
