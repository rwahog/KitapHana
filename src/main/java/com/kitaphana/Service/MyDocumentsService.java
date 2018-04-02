package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.userDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDocumentsService {

    Database db = Database.getInstance();
    userDAOImpl userDAO = new userDAOImpl();

    public ArrayList<Document> setDocs(String id) {
        ArrayList<Document> docs = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    doc.setAuthors(rs1.getString("authors"));
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

    public void renewDoc(String doc_id, String user_id) throws SQLException {
        User user = userDAO.findById(Long.parseLong(user_id));
        ArrayList<String> docs = new ArrayList(Arrays.asList(user.getId_documents().split(",")));
        ArrayList<String> deadlines = new ArrayList<>(Arrays.asList(user.getDeadlines().split(",")));
        int index = docs.indexOf(doc_id);
        docs.remove(index);
        deadlines.remove(index);
        String docs_str = "";
        String deadlines_str = "";
        if (docs.size() == 1) {
            docs_str = docs.get(0);
            deadlines_str = deadlines.get(0);
        } else {
            for (int i = 0; i < docs.size(); i++) {
                docs_str = docs_str.concat(","+docs.get(i));
                deadlines_str = deadlines_str.concat(","+deadlines.get(i));
            }
        }
        user.setId_documents(docs_str);
        String user_renews = user.getRenews();
        if (user_renews != null && user_renews.length() != 0) {
            user.setRenews(user_renews.concat("," + doc_id));
        } else {
            user.setRenews(doc_id);
        }
        userDAO.update(user);
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
    public ArrayList setNameAndSurname(String id) {
        ArrayList name = new ArrayList();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.name, users.surname FROM users WHERE users.id = '"+id+"';");
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