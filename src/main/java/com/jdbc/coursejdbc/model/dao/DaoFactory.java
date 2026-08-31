package com.jdbc.coursejdbc.model.dao;

import com.jdbc.coursejdbc.model.dao.impl.DepartamentDaoJDBC;
import com.jdbc.coursejdbc.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC();
    }

    public static DepartamentDao createDepartamentDao() {
        return new DepartamentDaoJDBC();
    }

}
