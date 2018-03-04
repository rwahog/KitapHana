package com.kitaphana.Service;

import com.kitaphana.Database.Database;

import java.sql.ResultSet;

public class AddDocumentService {

    Database db = new Database();

    public boolean checkUnique(String title, String authors, String type) {
        boolean unique = false;
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM documents WHERE title = '" + title + "', authors = '" + authors + "', type = '" + type +"';");
            if (!rs.next()) {
                unique = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return unique;
    }

    public void saveBook(String title, String authors, String description, String keywords, String price, String amount, String edition_number, String publisher, String year, String bestseller) {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            db.runSqlUpdate("INSERT INTO documents (title, authors, keywords, description, price, amount, type) VALUES ('" + title + "','" + authors + "', '" + description + "', '" + keywords + "', '" + price + "', '" + amount + "', 'book')");
            db.runSqlUpdate("INSERT INTO books (title, publisher, year, edition_number, best_seller, id_document) VALUES('"+title+"', '"+publisher+"', '"+year+"', '"+edition_number+"', '"+bestseller+"')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveJournalArticle(String title, String authors, String description, String keywords, int price, int amount, String editors, String journal_name, String date) {

    }

    public void saveAV(String title, String authors, String description, String keywords, int price, int amount) {

    }
}
