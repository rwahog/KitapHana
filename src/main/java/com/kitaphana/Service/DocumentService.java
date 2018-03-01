package com.kitaphana.Service;

import com.kitaphana.Database.Database;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DocumentService {
    public Database db = new Database();

    public ArrayList<Book> setDocInfo(String title) {
        ArrayList<Book> docParam = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Book doc = new Book();
            ResultSet rs = db.runSqlQuery("SELECT * FROM documents INNER JOIN books ON documents.title = books.title WHERE books.title ='"+title+"'" +
                    "");
            while (rs.next()) {
                System.out.println("sdf");
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
            docParam.add(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return docParam;
    }
}

//    public void checkOut() {
//        
//}
