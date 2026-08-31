package com.jdbc.coursejdbc.application;

import com.jdbc.coursejdbc.model.dao.DaoFactory;
import com.jdbc.coursejdbc.model.dao.SellerDao;
import com.jdbc.coursejdbc.model.dao.impl.SellerDaoJDBC;
import com.jdbc.coursejdbc.model.entities.Department;
import com.jdbc.coursejdbc.model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        //Connection conn = DB.getConnection();
       //DB.closeConnection();

        Department department = new Department(1, "Books");
        System.out.println("Departament toString: "+ department.toString());
        Seller seller = new Seller(1, "Larissa", "larissa@gmail.com", new Date(), 3000.0, department);

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(seller);
        System.out.println(department.toString());
    }
}
