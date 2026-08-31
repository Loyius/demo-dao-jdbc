package com.jdbc.coursejdbc.src.model.dao;

import com.jdbc.coursejdbc.src.model.entities.Department;
import com.jdbc.coursejdbc.src.model.entities.Seller;

import java.util.List;

public interface SellerDao {
    void insert(Seller obj);
    void update(Seller obj);
    void deleteById(Department id);
    Seller findById(Integer id);
    List<Seller> findAll();
}
