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
import java.util.List;

public class MyDocumentsService {

    Database db = Database.getInstance();
    userDAOImpl userDAO = new userDAOImpl();
    documentDAOImpl documentDAO = new documentDAOImpl();
    DBService dbService = new DBService();

    public ArrayList<Document> setDocs(String id) {
        ArrayList<Document> docs = new ArrayList<>();
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.documents, users.deadlines FROM users WHERE id = '" + id + "'");
            while (rs.next()) {
                String user_id = rs.getString("documents");
                String user_deadlines = rs.getString("deadlines");
                if (user_id.length() == 0 || user_deadlines.length() == 0) {
                    return null;
                }
                String[] ids = user_id.split(",");
                String[] ids_dead = user_deadlines.split(",");
                for (int i = 0; i < ids.length; i++) {
                    ResultSet rs1 = db.runSqlQuery("SELECT * FROM documents WHERE id = '" + ids[i] + "'");
                    Document doc = new Document();
                    rs1.next();
                    doc.setId(Integer.parseInt(ids[i]));
                    doc.setTitle(rs1.getString("title"));
                    doc.setAuthorsId(rs1.getString("authors"));
                    doc.setType(rs1.getString("type"));
                    doc.setPrice(rs1.getInt("price"));
                    doc.setDeadline(doc.getDeadlineOfDocument(Long.parseLong(ids_dead[i])));
                    docs.add(doc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docs;
    }

    public ArrayList<Document> setWaitingsInfo(Long user_id) throws SQLException {
        ArrayList<Document> waitings = new ArrayList<>();
        User user = userDAO.findById(user_id);
        String user_waitlist = user.getWaitingList();
        if (user_waitlist != null && user_waitlist.length() != 0) {
            ArrayList<String> docs = new ArrayList<>(Arrays.asList(user_waitlist.split(",")));
            for (String id_doc : docs) {
                Document document = documentDAO.findById(Long.parseLong(id_doc));
                waitings.add(document);
            }
        }
        return waitings;
    }

    public void renewDoc(String doc_id, String user_id) {
        User user = userDAO.findById(Long.parseLong(user_id));
        String user_renews = user.getRenews();
        String available = dbService.findColumn(doc_id, "documents", "available");
        if (available.equals("0")) {
            return;
        }
        if (user_renews != null && user_renews.length() != 0) {
            user.setRenews(user_renews.concat("," + doc_id));
        } else {
            user.setRenews(doc_id);
        }
        userDAO.update(user);
        dbService.sendMessageToLibrarians("You have some work to do (new renew)");
    }

    public void returnDoc(String docId, String userId) {
        String returns = dbService.findColumn(userId, "users", "returns");
        if (returns != null && returns.length() != 0) {
            returns = returns.concat("," + docId);
        } else {
            returns = docId;
        }
        dbService.updateColumn(userId, returns, "users", "returns");
        dbService.sendMessageToLibrarians("You have some work to do (new return)");
    }

    public ArrayList<String> setNameAndSurname(String id) {
        ArrayList<String> nameAndSurname = new ArrayList<>();
        try {
            String name = dbService.findColumn(id, "users", "name");
            String surname = dbService.findColumn(id, "users", "surname");
            nameAndSurname.add(name);
            nameAndSurname.add(surname);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return nameAndSurname;
    }
}
