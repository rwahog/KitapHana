package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDocumentsService {

    Database db = new Database();

    public ArrayList<Document> setDocs(String id) {
        ArrayList<Document> docs = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = db.runSqlQuery("SELECT users.documents FROM users WHERE id = '" + id + "'");
            while (rs.next()) {
                String user_id = rs.getString("documents");
                if (user_id.length() == 0) {
                    return null;
                }
                String[] ids = user_id.split(",");
                for (int i = 0; i < ids.length; i++) {
                    ResultSet rs1 = db.runSqlQuery("SELECT * FROM documents WHERE id = '" + ids[i] + "'");
                    Document doc = new Document();
                    rs1.next();
                    doc.setId(Integer.parseInt(ids[i]));
                    doc.setTitle(rs1.getString("title"));
                    doc.setAuthors(rs1.getString("authors"));
                    doc.setType(rs1.getString("type"));
                    docs.add(doc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docs;
    }

    public void returnDoc(String DocId, String id) {
        List<String> deadlines;
        List<String> docs;
        List<String> users;
        int amount;
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = db.runSqlQuery("SELECT users.documents, users.deadlines FROM users WHERE id = '" + id + "'");
            while (rs.next()) {
                docs = new ArrayList<String>(Arrays.asList(rs.getString("documents").split(",")));
                deadlines = new ArrayList<String>(Arrays.asList(rs.getString("deadlines").split(",")));
                deadlines.remove(docs.indexOf(DocId));
                docs.remove(docs.indexOf(DocId));
                String newDocs = String.join(",", docs);
                String newDeadlines = String.join(",", deadlines);
                db.runSqlUpdate("UPDATE users SET documents = '" + newDocs + "', deadlines = '" + newDeadlines + "' WHERE id = '" + id + "'");
            }
            rs = db.runSqlQuery("SELECT documents.users, documents.amount FROM documents WHERE id = '" + DocId + "'");
            while (rs.next()) {
                users = new ArrayList<String>(Arrays.asList(rs.getString("users").split(",")));
                users.remove(users.indexOf(id));
                String newUsers = String.join(",", users);
                amount = rs.getInt("amount") + 1;
                db.runSqlUpdate("UPDATE documents SET users = '" + newUsers + "', amount = '" + amount + "' WHERE id = '" + DocId + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}