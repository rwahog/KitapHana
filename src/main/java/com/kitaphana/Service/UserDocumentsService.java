package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDocumentsService {

    Database db = new Database();

    public ArrayList<Document> fillPage(String id) {
        ArrayList<Document> documents = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = db.runSqlQuery("SELECT documents FROM users WHERE id = '" + id + "'");
            while (rs.next()) {
                String docs_id = rs.getString("documents");
                String[] ids = docs_id.split(",");
                for (int i = 0; i < ids.length; i++) {
                    ResultSet rs1 = db.runSqlQuery("SELECT * FROM documents WHERE id = '" + ids[i] + "'");
                    Document doc = new Document();
                    rs1.next();
                    doc.setTitle(rs1.getString("title"));
                    doc.setAuthors(rs1.getString("authors"));
                    doc.setType(rs1.getString("type"));
                    doc.setId(Integer.parseInt(ids[i]));
                    documents.add(doc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }
}
