package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Book;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DocumentService {
    public Database db = new Database();

    public ArrayList<Book> setDocInfo(String id) {
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
            ResultSet rs = db.runSqlQuery("SELECT * FROM documents INNER JOIN books ON documents.id = books.id_document WHERE books.id_doc" +
                    "ument ='" + id + "'");
            while (rs.next()) {
                doc.setId(rs.getInt("id"));
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
                doc.setDescription(rs.getString("description"));
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

    long day = 24*1000*60*60;
    public boolean checkOut(String name, String surname, int id) {
        boolean checkOut = false;
        Date date = new Date();
        long cur = date.getTime();
        String docs = "";

        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Statement statement = db.con.createStatement();
            ResultSet rs = db.runSqlQuery("select * from users where name = '"+name+"' and surname = '"+surname+"'");
            ResultSet dks = db.runSqlQuery("SELECT * FROM documents WHERE id = '"+id+"'");
            String kek = "";
            String users = "";
            boolean best = false;
            while(dks.next()){
                System.out.println(kek);
                if(dks.getString("type").equals("book")){
                    ResultSet books = db.runSqlQuery("select * from books where id_document = '"+id+"'");
                    while(books.next()){
                        if(books.getInt("best_seller")==1){
                            best = true;
                        }
                    }
                }
                kek+=dks.getString("amount");
                if(dks.getString("users")!=null){
                    users=users+dks.getString("users");}
            }
            int kek1 = Integer.parseInt(kek)-1;
            String deadlines = "";
            statement.executeUpdate("UPDATE documents SET amount='"+kek1+"' where id='"+id+"'");
            while (rs.next()) {
                boolean exist = false;
                String[] ids = rs.getString("documents").split(",");

                if(!ids[0].equals("")){
                for (int i = 0; i<ids.length; i++){
                    if(Integer.parseInt(ids[i])==id){
                        exist = true;
                    }
                }}
                if(exist){
                    return false;
                }
                if(best){
                    cur+=14*day;
                }
                else if(rs.getString("type").equals("Student")){
                    cur+=21*day;
                }else cur+=28*day;
                docs = rs.getString("documents");
                deadlines = rs.getString("deadlines");
                if(!deadlines.equals("")){deadlines = deadlines+","+cur;}
                else deadlines = cur+"";
                if(!docs.equals("")){docs = docs+","+id;}
                else docs = id+"";
                if(!users.equals("")) {users = users+","+rs.getString("id");}
                else users = rs.getString("id");
                statement.executeUpdate("Update users SET deadlines='"+deadlines+"' where name='"+name+"' and surname='"+surname+"'");
                System.out.println(docs);
                statement.executeUpdate("Update users SET documents='"+docs+"' where name='"+name+"' and surname='"+surname+"'");
            }
            statement.executeUpdate("UPDATE documents SET users='"+users+"' where id='"+id+"'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}