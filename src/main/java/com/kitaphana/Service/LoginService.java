package com.kitaphana.Service;
import com.kitaphana.Database.Database;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class LoginService {
    public Database db = new Database();
    public boolean login;
    public boolean loginCheck(String username, String password){
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dbUsername, dbSurname;
        login = false;
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.phone_number, users.password FROM users;");

            while(rs.next()){
                dbUsername = rs.getString("phone_number");
                dbSurname = rs.getString("password");

                if(dbUsername.equals(username) && dbSurname.equals(password)){
                    login = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

    public ArrayList getUserNameAndId(String phone_number){
        ArrayList name = new ArrayList();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.name, users.surname, users.id FROM users WHERE users.phone_number = '"+phone_number+"';");
            while (rs.next()) {
                name.add(rs.getString("name"));
                name.add(rs.getString("surname"));
                name.add(rs.getString("id"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
    public void redirect(HttpServletRequest request, HttpServletResponse response, String red) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String phone_number = (String) session.getAttribute("login");
        String password = (String) session.getAttribute("password");
        loginCheck(phone_number, password);
        if(!login){
            response.sendRedirect("/logout");
        }else {
            request.getRequestDispatcher("/WEB-INF/views/"+red+".jsp").forward(request, response);
        }
    }
}
