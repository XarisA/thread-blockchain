package com.unipi.xa_gm;

import java.sql.Connection;
import java.sql.SQLException;

public class CheckConnection {
    Connection conn;

    public CheckConnection() {
        conn = com.unipi.xa_gm.SQLConnection.DBConnector();
        if (conn==null){
            System.out.println("Connection error");
            System.exit(1);
        }
    }
    public boolean isConnected(){
        try {
            return !conn.isClosed();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
    }

}
