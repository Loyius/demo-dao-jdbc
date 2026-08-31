package com.jdbc.coursejdbc.model.dao;

import com.jdbc.coursejdbc.model.entities.Department;

import java.util.List;

public interface DepartamentDao {

    void insert(Department obj);
    void update(Department obj);
    void deleteById(Department id);
    Department findById(Integer id);
    List<Department> findAll();
}
