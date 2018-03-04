package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.User;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DocumentHoldersService {

    Database db = new Database();

    public ArrayList<User> fillPage(String id) {
        ArrayList<User> users = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = db.runSqlQuery("SELECT users FROM documents WHERE id = '" + id + "'");
            while (rs.next()) {
                String user_id = rs.getString("users");
                String[] ids = user_id.split(",");
                System.out.println(user_id);
                for (int i = 0; i < ids.length; i++) {
                    ResultSet rs1 = db.runSqlQuery("SELECT * FROM users WHERE id = '" + ids[i] + "'");
                    User user = new User();
                    rs1.next();
                    System.out.println(ids[i]);
                    user.setId(Integer.parseInt(ids[i]));
                    user.setName(rs1.getString("name"));
                    user.setSurname(rs1.getString("surname"));
                    user.setPossible_type(rs1.getString("type"));
                    users.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
