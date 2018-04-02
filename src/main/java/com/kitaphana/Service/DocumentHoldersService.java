package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.documentDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DocumentHoldersService {
    documentDAOImpl documentDAO = new documentDAOImpl();
    Database db = Database.getInstance();

    public ArrayList<User> fillPage(String id) {
        ArrayList<User> users = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = db.runSqlQuery("SELECT users FROM documents WHERE id = '" + id + "' AND users IS NOT NULL AND users != ''");
            if (rs.next()) {
                String user_id = rs.getString("users");
                if (user_id.length() == 0) {
                    return null;
                }
                String[] ids = user_id.split(",");
                for (String id_user : ids) {
                    ResultSet rs1 = db.runSqlQuery("SELECT * FROM users WHERE id = '" + id_user + "'");
                    if (rs1.next()) {
                        User user = new User();
                        user.setId(Long.parseLong(id_user));
                        user.setName(rs1.getString("name"));
                        user.setSurname(rs1.getString("surname"));
                        user.setType(rs1.getString("type"));
                        String doc_id = rs1.getString("documents");
                        ArrayList<String> docs = new ArrayList<>(Arrays.asList(doc_id.split(",")));
                        int index = docs.indexOf(id);
                        String deadlines_id = rs1.getString("deadlines");
                        ArrayList<String> deadlines = new ArrayList<>(Arrays.asList(deadlines_id.split(",")));
                        long deadline = Long.parseLong(deadlines.get(index));
                        Document doc = new Document();
                        doc = documentDAO.findById(Long.parseLong(id));
                        deadline = doc.getDeadlineOfDocument(deadline);
                        user.setDeadline(deadline);
                        users.add(user);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<User> setAwaitersInfo(String doc_id) {
        ArrayList<User> awaiters = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT waiting_list FROM documents WHERE id='" + doc_id + "' AND waiting_list IS NOT NULL AND waiting_list != ''");
            rs.next();
            String awaiters_string = rs.getString("waiting_list");
            String[] waiting_list = awaiters_string.split(",");
            for (String user_id: waiting_list) {
                ResultSet user_db = db.runSqlQuery("SELECT users.id, users.name, users.surname, users.type FROM users WHERE id='" + user_id+"'");
                if (user_db.next()) {
                    User user = new User();
                    user.setId(user_db.getLong("id"));
                    user.setName(user_db.getString("name"));
                    user.setSurname(user_db.getString("surname"));
                    user.setType(user_db.getString("type"));
                    awaiters.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return awaiters;
    }
}
