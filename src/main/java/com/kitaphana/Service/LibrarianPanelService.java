package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;

import java.sql.ResultSet;
import java.util.ArrayList;

public class LibrarianPanelService {
    private Database db = new Database();

    public ArrayList<User> setUsersInfo() {
        ArrayList<User> users = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.id, users.name, users.surname, users.card_number, users.possible_type FROM users;");
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCard_number(rs.getLong("card_number"));
                user.setPossible_type(rs.getString("possible_type"));
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
}
