package com.noel.sqlhierarchy.repository;

import com.noel.sqlhierarchy.datamodel.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    @Query(value = "WITH employee_hierarchy (id,first_name,last_name,manager_id) AS(\n" +
            "\tSELECT me.id as id,me.first_name,me.last_name,me.manager_id \n" +
            "\tFROM adjacency_model.mst_employee me WHERE me.id = :id\n" +
            "\n" +
            "\tUNION ALL\n" +
            "\t\n" +
            "\tSELECT e.id as id, e.first_name, e.last_name, e.manager_id  \n" +
            "\tFROM adjacency_model.mst_employee e\n" +
            "\tINNER JOIN employee_hierarchy eh ON e.manager_id = eh.id\n" +
            "\t\n" +
            ")\n" +
            "SELECT * FROM employee_hierarchy",nativeQuery = true)
    List<Employee> getEmployeeHierarchyById(@Param("id") int id);


    @Query(value = "WITH employee_hierarchy (id,first_name,last_name,manager_id) AS(\n" +
            "\tSELECT me.id as id,me.first_name,me.last_name,me.manager_id \n" +
            "\tFROM adjacency_model.mst_employee me WHERE me.manager_id IS null\n" +
            "\n" +
            "\tUNION ALL\n" +
            "\t\n" +
            "\tSELECT e.id as id, e.first_name, e.last_name, e.manager_id  \n" +
            "\tFROM adjacency_model.mst_employee e\n" +
            "\tINNER JOIN employee_hierarchy eh ON e.manager_id = eh.id\n" +
            "\t\n" +
            ")\n" +
            "SELECT * FROM employee_hierarchy",nativeQuery = true)
    List<Employee> getWholeEmployeeHierarchy();

    List<Employee> findByManagerId(Integer managerId);
}
