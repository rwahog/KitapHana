package com.kitaphana.Service;
import com.kitaphana.Database.Database;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.userDAOImpl;

import java.sql.*;

public class LoginService {
    public Database db = Database.getInstance();
    userDAOImpl userDAO = new userDAOImpl();

    public boolean loginCheck(String phone_number, String password) {
        boolean login = false;
        User user = userDAO.findByPhoneNumber(phone_number);
        if (user != null && user.getPassword().equals(password)) {
            login = true;
        }
        return login;
    }

    public long getChatId(String phone_number) {
        long chat_id = 0;
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.chat_id FROM users WHERE users.phone_number = '"+phone_number+"';");
            while (rs.next()) {
                chat_id = rs.getLong("chat_id");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return chat_id;


    }
}
