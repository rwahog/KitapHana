package com.kitaphana.Service;
import com.kitaphana.Database.Database;

import java.sql.*;
import java.util.ArrayList;

public class LoginService {
    public Database db = new Database();

    public boolean loginCheck(String username, String password){
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dbUsername, dbPassword;
        boolean login = false;

        try {
            ResultSet rs = db.runSql("SELECT users.phone_number, users.password FROM users;");

            while(rs.next()){
                dbUsername = rs.getString("phone_number");
                dbPassword = rs.getString("password");

                if(dbUsername.equals(username) && dbPassword.equals(password)){
                    login = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

    public ArrayList getUserName(String phone_number){
        ArrayList name = new ArrayList();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSql("SELECT users.name, users.surname FROM users WHERE users.phone_number = '"+phone_number+"';");
            while (rs.next()) {
                name.add(rs.getString("name"));
                name.add(rs.getString("surname"));
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
}