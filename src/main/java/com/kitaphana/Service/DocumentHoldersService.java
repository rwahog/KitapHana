package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.userDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DocumentHoldersService {
    documentDAOImpl documentDAO = new documentDAOImpl();
    userDAOImpl userDAO = new userDAOImpl();
    Database db = Database.getInstance();
    DBService dbService = new DBService();
    DocumentService documentService = new DocumentService();
    public ArrayList<User> fillPage(String id) {
        ArrayList<User> users = new ArrayList<>();
        try {
            String usersStr = dbService.findColumn(id, "documents", "users");
            if (usersStr == null) {
                return null;
            }
            ArrayList<String> usersId = documentService.fromDBStringToArray(usersStr);
                for (String userId : usersId) {
                    User user = userDAO.findById(Long.parseLong(userId));
                        if (user != null) {
                            ArrayList<String> docs = documentService.fromDBStringToArray(user.getDocuments());
                            ArrayList<String> deadlines = documentService.fromDBStringToArray(user.getDeadlines());
                            int index = docs.indexOf(id);
                            long deadline = Long.parseLong(deadlines.get(index));
                            Document doc = documentDAO.findById(Long.parseLong(id));
                            deadline = doc.getDeadlineOfDocument(deadline);
                            user.setDeadline(deadline);
                            users.add(user);
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
