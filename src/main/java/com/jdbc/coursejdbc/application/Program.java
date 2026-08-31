package com.jdbc.coursejdbc.application;

import com.jdbc.coursejdbc.model.entities.Department;
import com.jdbc.coursejdbc.model.entities.Seller;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        //Connection conn = DB.getConnection();
       //DB.closeConnection();

        Department department = new Department(1, "Books");
        Seller seller = new Seller(1, "Larissa", "larissa@gmail.com", new Date(), 3000.0, department);

        System.out.println(seller);
        //System.out.println(department.toString());
    }
}
