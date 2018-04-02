package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class LibrarianPanelService {
    private Database db = Database.getInstance();

    public ArrayList<User> setUsersInfo() {
        ArrayList<User> users = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.id, users.name, users.surname, users.card_number, users.possible_type, users.type FROM users;");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCard_number(rs.getLong("card_number"));
                user.setPossible_type(rs.getString("possible_type"));
                user.setType(rs.getString("type"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<Document> setDocsInfo() {
        ArrayList<Document> docs = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT documents.id, documents.title, documents.authors, documents.type, documents.amount FROM documents;");
            while (rs.next()) {
                Document doc = new Document();
                doc.setId(rs.getInt("id"));
                doc.setTitle(rs.getString("title"));
                doc.setAuthors(rs.getString("authors"));
                doc.setType(rs.getString("type"));
                doc.setAmount(rs.getInt("amount"));
                docs.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docs;
    }

    public ArrayList<User> setCheckoutsInfo() {
        ArrayList<User> users = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE checkouts IS NOT NULL AND checkouts != ''");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setType(rs.getString("type"));
                user.setId_documents(rs.getString("checkouts"));
                ArrayList<String> docs = user.getDocumentsAsArray();
                ArrayList<Document> tempArr = new ArrayList<>();
                for (String id: docs) {
                    ResultSet user_doc = db.runSqlQuery("SELECT documents.id, documents.title, documents.type FROM documents WHERE id = '" + id +"'");
                    while (user_doc.next()) {
                        Document document = new Document();
                        document.setId(user_doc.getLong("id"));
                        document.setTitle(user_doc.getString("title"));
                        document.setType(user_doc.getString("type"));
                        tempArr.add(document);
                    }
                }
                user.setDocuments(tempArr);
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public ArrayList<User> setRenewsInfo() {
        ArrayList<User> users = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE renews IS NOT NULL AND renews != ''");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setType(rs.getString("type"));
                user.setId_documents(rs.getString("renews"));
                ArrayList<String> docs = user.getDocumentsAsArray();
                ArrayList<Document> tempArr = new ArrayList<>();
                for (String id: docs) {
                    ResultSet user_doc = db.runSqlQuery("SELECT documents.id, documents.title, documents.type FROM documents WHERE id = '" + id +"'");
                    while (user_doc.next()) {
                        Document document = new Document();
                        document.setId(user_doc.getLong("id"));
                        document.setTitle(user_doc.getString("title"));
                        document.setType(user_doc.getString("type"));
                        tempArr.add(document);
                    }
                }
                user.setDocuments(tempArr);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public ArrayList<User> setReturnsInfo() {
        ArrayList<User> users = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE returns IS NOT NULL AND returns != ''");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setType(rs.getString("type"));
                user.setId_documents(rs.getString("returns"));
                ArrayList<String> docs = user.getDocumentsAsArray();
                ArrayList<Document> tempArr = new ArrayList<>();
                for (String id: docs) {
                    ResultSet user_doc = db.runSqlQuery("SELECT documents.id, documents.title, documents.type FROM documents WHERE id = '" + id +"'");
                    while (user_doc.next()) {
                        Document document = new Document();
                        document.setId(user_doc.getLong("id"));
                        document.setTitle(user_doc.getString("title"));
                        document.setType(user_doc.getString("type"));
                        tempArr.add(document);
                    }
                }
                user.setDocuments(tempArr);
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public ArrayList<Document> setWaintingsInfo() {
        ArrayList<Document> docs = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM documents WHERE waiting_list IS NOT NULL AND waiting_list != ''");
            while (rs.next()) {
                Document doc = new Document();
                doc.setId(rs.getLong("id"));
                doc.setTitle(rs.getString("title"));
                doc.setType(rs.getString("type"));
                String waitings = rs.getString("waiting_list");
                ArrayList tempArr = new ArrayList<>(Arrays.asList(waitings.split(",")));
                doc.setRequests(tempArr.size());
                docs.add(doc);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return docs;
    }
}
