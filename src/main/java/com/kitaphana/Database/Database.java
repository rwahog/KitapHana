package com.kitaphana.Database;

import java.sql.*;

public class Database {

  private static Database db;
  public Connection con;

  private Database() throws SQLException {
    connect();
  }

  public static Database getInstance() {
    if (db == null) {
      try {
        db = new Database();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return db;
  }

  public Connection connect() throws SQLException {
    if (con == null) {
      try {
        Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        throw new SQLException("No database");
      }
      String connectionURL = "jdbc:mysql://trainno.ru:3306/kh?autoReconnect=true&useSSL=false";
      con = DriverManager.getConnection(connectionURL, "remote", "password");
    }
    return con;
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