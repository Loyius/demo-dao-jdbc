package application;

import db.DB;
import model.entities.Department;

import java.sql.Connection;

public class Program {
    public static void main(String[] args) {
        //Connection conn = DB.getConnection();
       //DB.closeConnection();

        Department department = new Department(1, "Books");

        System.out.println(department.toString());
    }
}
