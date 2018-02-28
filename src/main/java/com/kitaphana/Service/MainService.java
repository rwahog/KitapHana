package com.kitaphana.Service;

import com.kitaphana.Database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainService {
    public Database db = new Database();

    public ArrayList<Document> fillPage() {
        ArrayList<Document> documents = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT documents.title, documents.authors, documents.keywords, documents.document_cover FROM documents;");
            int i = 0;
            while (rs.next()) {
                Document doc = new Document();
                doc.setTitle(rs.getString("title"));
                doc.setAuthors(rs.getString("authors"));
                doc.setKeywords(rs.getString("keywords"));
                doc.setCover(rs.getString("document_cover"));
                documents.add(doc);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
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
