package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainService {
    public Database db = new Database();
    public static boolean isLiber;
    public ArrayList<Document> fillPage() {
        ArrayList<Document> documents = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT documents.id, documents.title, documents.authors, documents.keywords, documents.document_cover FROM documents;");
            while (rs.next()) {
                Document doc = new Document();
                doc.setId(rs.getInt("id"));
                doc.setTitle(rs.getString("title"));
                doc.setAuthors(rs.getString("authors"));
                doc.setKeywords(rs.getString("keywords"));
                doc.setCover(rs.getString("document_cover"));
                documents.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }
    public boolean isLibrarian(String phone_number, String surname){
        boolean isLib = false;
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dbPhone, dbPass, dbStatus;
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.phone_number, users.surname, users.type FROM users;");

            while(rs.next()){
                dbPhone = rs.getString("phone_number");
                dbPass = rs.getString("surname");
                dbStatus = rs.getString("type");
                if(dbStatus==null){
                    dbStatus = " ";
                }
                if(dbPhone.equals(phone_number) && dbPass.equals(surname) && dbStatus.equals("Librarian")){
                    isLib = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        isLiber = isLib;
        return isLib;
    }


}

//    public void search(String query){
//        try {
//            db.connect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            ResultSet rs = db.runSqlQuery("SELECT * FROM documents WHERE documents.title = '" )
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
