package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Book;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DocumentService {
    public Database db = new Database();

    public ArrayList<Book> setDocInfo(String title) {
        ArrayList<Book> bookParam = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
         try {
//            String type = "";
//            ResultSet rs = db.runSqlQuery("SELECT documents.type FROM documents WHERE title = '"+title+"'");
//            while (rs.next()) {
//                type = rs.getString("type");
//            }

//            switch (type) {
//                case "book":
            Book doc = new Book();
            ResultSet rs = db.runSqlQuery("SELECT * FROM documents INNER JOIN books ON documents.title = books.title WHERE books.title ='" + title + "'");
            while (rs.next()) {
                doc.setTitle(rs.getString("title"));
                doc.setAuthors(rs.getString("authors"));
                doc.setCover(rs.getString("document_cover"));
                doc.setAmount(rs.getInt("amount"));
                doc.setPrice(rs.getInt("price"));
                doc.setType(rs.getString("type"));
                doc.setBest_seller(rs.getInt("best_seller"));
                doc.setEdition_number(rs.getInt("edition_number"));
                doc.setPublisher(rs.getString("publisher"));
                doc.setYear(rs.getInt("year"));
            }

             bookParam.add(doc);
//                case "ja":
//                    ArrayList<JournalArticle> jaParam = new ArrayList<>();
//                    JournalArticle article = new JournalArticle();
//                    rs = db.runSqlQuery("SELECT * FROM ja WHERE ja.title ='" + title + "'");
//                    while (rs.next()) {
//                        article.setJournal_name(rs.getString("journal_name"));
//                        article.setEditors(rs.getString("editors"));
//                        article.setTitle(rs.getString("title"));
//                        article.setDate(rs.getString("date"));
//                    }
//                    jaParam.add(article);
//                    return jaParam;
//                case "av":
//                    ArrayList<AVMaterial> avParam = new ArrayList<>();
//                    AVMaterial av = new AVMaterial();
//                    rs = db.runSqlQuery("SELECT * FROM av WHERE av.title ='" + title + "'");
//                    while (rs.next()) {
//                        av.setTitle(rs.getString("title"));
//                        av.setId(rs.getInt("id"));
//                    }
//                    avParam.add(av);
//                    return avParam;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookParam;
    }


    public boolean checkOut(String name, String surname, String title) {
        boolean checkOut = false;
        String docs = "";
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE name = '"+name+" AND surname = '"+surname+"'");
            while (rs.next()) {
                docs = rs.getString("documents");
            }
            checkOut = !docs.contains(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkOut;
    }
}
