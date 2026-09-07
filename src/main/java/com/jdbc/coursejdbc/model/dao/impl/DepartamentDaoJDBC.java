package com.jdbc.coursejdbc.model.dao.impl;

import com.jdbc.coursejdbc.db.DB;
import com.jdbc.coursejdbc.db.DbException;
import com.jdbc.coursejdbc.model.dao.DepartamentDao;
import com.jdbc.coursejdbc.model.entities.Department;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartamentDaoJDBC implements DepartamentDao {

    private Department instantiateDepartment (ResultSet rs) throws SQLException {
        Department depart = new  Department();
        depart.setId(rs.getInt("Id"));
        depart.setName(rs.getString("Name"));
        return depart;
    }

    private Connection conn;
    public DepartamentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO department (Name)" +
                            "VALUES (?)"
                    , Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id =  rs.getInt(1);
                    obj.setId(id);
                } else {
                    throw new DbException("Failed to insert department record");
                }
                DB.closeResultSet(rs);
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "UPDATE department SET Name = ? WHERE Id = ?"
                    , Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            st.executeUpdate();
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("DELETE FROM department WHERE Id = ?");
            st.setInt(1, id);
            int rows =  st.executeUpdate();

            if (rows > 0) {
                DB.closeStatement(st);
            } else {
                throw new DbException("Failed to delete department, row does not exist");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT * FROM department WHERE Id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){
                Department depart = instantiateDepartment(rs);
                return depart;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Department> findAll() {
       PreparedStatement st = null;
       ResultSet rs = null;
       try {
           st = conn.prepareStatement("SELECT * FROM department");
           rs = st.executeQuery();
           List<Department> list = new ArrayList<>();
            while (rs.next()) {
                Department department = instantiateDepartment(rs);
                list.add(department);
            }
            return list;
       } catch (SQLException e) {
           throw new DbException(e.getMessage());
       }
       finally {
           DB.closeResultSet(rs);
           DB.closeStatement(st);
       }
    }
}
