package com.jdbc.coursejdbc.model.dao.impl;

import com.jdbc.coursejdbc.db.DB;
import com.jdbc.coursejdbc.db.DbException;
import com.jdbc.coursejdbc.model.dao.SellerDao;
import com.jdbc.coursejdbc.model.entities.Department;
import com.jdbc.coursejdbc.model.entities.Seller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;
    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Department id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, departament.Name as DepName" +
                            "FROM seller INNER JOIN departament" +
                            "ON seller.DepartmentId = departament.Id" +
                            "WHERE seller.Id = ?"
            );

            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()) {
                Department depart = new Department();
                depart.setId(rs.getInt("DepartamentId"));
                depart.setName(rs.getString("DepName"));
                Seller obj = new Seller();
                obj.setId(rs.getInt("SellerId"));
                obj.setName(rs.getString("Name"));
                obj.setEmail(rs.getString("Email"));
                obj.setBirthDate(rs.getDate("birthDate"));
                obj.setBaseSalary(rs.getDouble("BaseSalary"));
                obj.setDepartment(depart);
                return obj;
            }
            return null;
        }
        catch (SQLException e) {
          throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
