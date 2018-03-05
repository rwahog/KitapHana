package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Author;
import com.kitaphana.Entities.Document;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditDocumentService {

    Database db = new Database();

    public Document setDocInfo(int id) {
        Document doc = new Document();
        Author author = new Author();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM documents WHERE id = '" + id + "'");
            while (rs.next()) {
                doc.setTitle(rs.getString("title"));
                doc.setAuthors(rs.getString("authors"));
                doc.setType(rs.getString("type"));
                doc.setDescription(rs.getString("description"));
                doc.setKeywords(rs.getString("keywords"));
                doc.setPrice(rs.getInt("price"));
                doc.setAmount(rs.getInt("amount"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public ArrayList<Author> getAuthors(String authors) {
        ArrayList<Author> authorsList = new ArrayList<>();
        List<String> list = new ArrayList<String>(Arrays.asList(authors.split(", ")));
        for (String value : list) {
            String[] splited = value.split("\\s+");
            Author author = new Author();
            author.setName(splited[0]);
            author.setSurname(splited[1]);
            authorsList.add(author);
        }
        return authorsList;
    }
}
