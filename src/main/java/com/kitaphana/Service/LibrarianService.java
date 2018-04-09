package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.documentDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class LibrarianService {
    private Database db = Database.getInstance();
    documentDAOImpl documentDAO = new documentDAOImpl();
    DocumentService documentService = new DocumentService();

    public ArrayList<User> setCheckoutsInfo() {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE checkouts != ''");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setType(rs.getString("type"));
                user.setCheckouts(rs.getString("checkouts"));
                ArrayList<String> docs = documentService.fromDBStringToArray(user.getCheckouts());
                ArrayList<Document> tempArr = new ArrayList<>();
                for (String id: docs) {
                    Document document = documentDAO.findById(Long.parseLong(id));
                    tempArr.add(document);
                }
                user.setDocumentsArray(tempArr);
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
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE renews != ''");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setType(rs.getString("type"));
                user.setRenews(rs.getString("renews"));
                ArrayList<String> docs = documentService.fromDBStringToArray(user.getRenews());
                ArrayList<Document> tempArr = new ArrayList<>();
                for (String id: docs) {
                    Document document = documentDAO.findById(Long.parseLong(id));
                    tempArr.add(document);
                }
                user.setDocumentsArray(tempArr);
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
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE returns != ''");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setType(rs.getString("type"));
                user.setReturns(rs.getString("returns"));
                ArrayList<String> docs = documentService.fromDBStringToArray(user.getReturns());
                ArrayList<Document> tempArr = new ArrayList<>();
                for (String id: docs) {
                    Document document = documentDAO.findById(Long.parseLong(id));
                    tempArr.add(document);
                }
                user.setDocumentsArray(tempArr);
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
            ResultSet rs = db.runSqlQuery("SELECT * FROM documents WHERE waiting_list != ''");
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
