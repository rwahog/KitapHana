package com.kitaphana.Database;
import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Database {

    private Connection con;

    public void connect() throws Exception {

        if (con != null) return;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("No database");
        }
        String connectionURL = "jdbc:mysql://trainno.ru:3306/kh?autoReconnect=true&useSSL=false";
        con = DriverManager.getConnection(connectionURL, "remote", "password");
    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet runSqlQuery(String sql) throws SQLException {
        Statement sta = con.createStatement();
        return sta.executeQuery(sql);
    }

    public void runSqlUpdate(String sql) throws SQLException {
        Statement sta = con.createStatement();
        sta.executeUpdate(sql);
    }
}