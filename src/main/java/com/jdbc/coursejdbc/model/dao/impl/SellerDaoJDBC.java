package com.jdbc.coursejdbc.model.dao.impl;

import com.jdbc.coursejdbc.db.DB;
import com.jdbc.coursejdbc.db.DbException;
import com.jdbc.coursejdbc.model.dao.SellerDao;
import com.jdbc.coursejdbc.model.entities.Department;
import com.jdbc.coursejdbc.model.entities.Seller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Seller instantiateSeller(ResultSet rs, Department depart) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("SellerId"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBirthDate(rs.getDate("birthDate"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setDepartment(depart);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department depart = new Department();
        depart.setId(rs.getInt("DepartamentId"));
        depart.setName(rs.getString("DepName"));
        return depart;
    }

    private Connection conn;
    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement(
                    "INSERT INTO seller" +
                            "(Name, Email, BirthDate, BaseSalary, DepartamentId)" +
                            "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();
            if(rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                else {
                    throw new DbException("Failed to insert seller");
                }
                DB.closeResultSet(rs);
            }
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
              "UPDATE seller"+
                      "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, Department = ?"+
                      "WHERE Id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
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
                            "WHERE seller.Id = ?");

            st.setInt(1, id);
            rs = st.executeQuery();

            if(rs.next()) {
                Department depart = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs, depart);
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
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, departament.Name as DepName"+
                        "FROM seller INNER JOIN departament"+
                        "ON seller.DepartamentId = departament.Id");

            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(rs.next()) {

                Department depart = map.get(rs.getInt("DepartamentId"));
                if(depart == null) {
                    depart = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartamentId"), depart);
                }
                Seller obj = instantiateSeller(rs, depart);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
//referencia ao mesmo objeto
    @Override
    public List<Seller> findByDepartament(Department departament) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement(
                    "SELECT seller.*, departament.Name as DepName" +
                            "FROM seller INNER JOIN departament" +
                            "ON seller.DepartmentId = departament.Id" +
                            "WHERE DepartmentId = ?" +
                            "Order by Name");

            st.setInt(1, departament.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while(rs.next()){
                Department depart = map.get(rs.getInt("DepartamentId"));

                if(depart == null){
                    depart = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartamentId"), depart);
                }
                Seller obj = instantiateSeller(rs, depart);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
