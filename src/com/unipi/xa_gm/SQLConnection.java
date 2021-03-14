package com.unipi.xa_gm;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLConnection {
    public static Connection DBConnector(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/com/unipi/xa_gm/data/simulations.sqlite3");
            return conn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
