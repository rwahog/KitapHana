package com.kitaphana.Service;

import com.kitaphana.Database.Database;

import java.sql.ResultSet;

public class DeleteUserService {

    Database db = new Database();

    public void deleteUser(String id) {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE id = '" + id + "'");
            rs.next();
            String id_address = rs.getString("id_address");
            String id_docs = rs.getString("documents");
            db.runSqlUpdate("DELETE FROM users WHERE id ='" + id + "'");
            db.runSqlUpdate("DELETE FROM addresses WHERE id_address = '" + id_address + "'");
            String[] ids = id_docs.split(",");
            System.out.println(id_docs);
            for (int i = 0; i < ids.length; i++) {
                rs = db.runSqlQuery("SELECT * FROM documents WHERE id = '" + ids[i] + "'");
                rs.next();
                int amount = rs.getInt("amount");
                String users = rs.getString("users");
                System.out.println(amount);
                System.out.println(users);
                users = users.replace(id, "");
                amount += 1;
                System.out.println(amount);
                System.out.println(users);
                db.runSqlUpdate("UPDATE documents SET users = '" + users + "', amount = '" + amount + "' WHERE id = '" + ids[i] + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
