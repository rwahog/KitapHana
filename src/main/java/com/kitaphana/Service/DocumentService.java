package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Book;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.bookDAOImpl;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.userDAOImpl;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.resource.spi.RetryableUnavailableException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DocumentService {
    public Database db = Database.getInstance();
    private documentDAOImpl documentDAO = new documentDAOImpl();
    private bookDAOImpl bookDAO = new bookDAOImpl();
    private userDAOImpl userDAO = new userDAOImpl();

    public Document setDocInfo(String id) {
        Document docParam = new Document();
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
            ResultSet rs = db.runSqlQuery("SELECT * FROM documents INNER JOIN books ON documents.id = books.document_id WHERE books.doc" +
                    "ument_id ='" + id + "'");
            if (rs.next()) {
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

            docParam = doc;
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
        return docParam;
    }

    long day = 24*1000*60*60;
    public boolean checkOut(long id_user, int id) {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Statement statement = db.con.createStatement();
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE id = '" + id_user + "'");
            ResultSet dks = db.runSqlQuery("SELECT * FROM documents WHERE id = '"+id+"'");
            String kek = "";
            while(dks.next()){
                kek+=dks.getString("amount");

            }
            int kek1 = Integer.parseInt(kek)-1;
            statement.executeUpdate("UPDATE documents SET amount='"+kek1+"' where id='"+id+"'");
            while (rs.next()) {
                boolean exist = false;
                String[] ids = rs.getString("documents").split(",");
                if(!ids[0].equals("")){
                    for (int i = 0; i<ids.length; i++) {
                        if(Integer.parseInt(ids[i])==id) {
                            exist = true;
                        }
                    }
                }
                if (exist) {
                    return false;
                }
                String checkouts = rs.getString("checkouts");
                if (checkouts.length() == 0) {
                    statement.executeUpdate("UPDATE users SET checkouts='" + checkouts.concat(String.valueOf(id)) + "' WHERE id ='" + id_user +"'");
                } else {
                    statement.executeUpdate("UPDATE users SET checkouts='" + checkouts.concat("," + String.valueOf(id)) + "' WHERE id ='" + id_user +"'");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkout_approval(String id_user, String id_document) {
        Date date = new Date();
        long cur = date.getTime();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String users = "";
            boolean best = false;
            ResultSet dks = db.runSqlQuery("SELECT * FROM documents WHERE id = '"+id_document+"'");
            while (dks.next()) {
                if(dks.getString("type").equals("book")){
                    ResultSet books = db.runSqlQuery("select * from books where id_document = '"+id_document+"'");
                    while(books.next()){
                        if(books.getInt("best_seller")==1){
                            best = true;
                        }
                    }
                }
                if (dks.getString("users")!=null) {
                    users = dks.getString("users");
                }
            }
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE id ='" + id_user +"'");
            while (rs.next()) {
                if (best) {
                    cur += 14 * day;
                } else if (rs.getString("type").equals("Student")) {
                    cur += 21 * day;
                } else if (rs.getString("type").equals("Visiting Professor")) {
                    cur += 7 * day;
                } else {
                    cur += 28 * day;
                }
                Statement statement = db.con.createStatement();
                String docs = "";
                String deadlines = "";
                String checkouts = "";
                docs = rs.getString("documents");
                deadlines = rs.getString("deadlines");
                checkouts = rs.getString("checkouts");
                if (!deadlines.equals("")) {
                    deadlines = deadlines + "," + cur;
                } else {
                    deadlines = cur + "";
                }
                if (!docs.equals("")) {
                    docs = docs + "," + id_document;
                } else {
                    docs = id_document + "";
                }
                ArrayList<String> arrayList = new ArrayList(Arrays.asList(checkouts.split(",")));
                arrayList.remove(arrayList.indexOf(id_document));
                checkouts = "";
                if (arrayList.size() == 1) {
                    checkouts = arrayList.get(0);
                } else {
                    for (String id : arrayList) {
                        checkouts = checkouts.concat("," + id);
                    }
                }
                if (!users.equals("")) {
                    users = users + "," + rs.getString("id");
                } else {
                    users = rs.getString("id");
                }
                statement.executeUpdate("UPDATE users SET deadlines='"+deadlines+"' WHERE id = '" +id_user + "'");
                statement.executeUpdate("UPDATE users SET documents='"+docs+"' WHERE id = '" +id_user + "'");
                statement.executeUpdate("UPDATE documents SET users='"+users+"' WHERE id='"+id_document+"'");
                statement.executeUpdate("UPDATE  users SET checkouts='" + checkouts + "' WHERE id = '" + id_user +"'");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void deleteBook(long id) throws SQLException {
        bookDAO.deleteByDocumentId(id);
    }

    public void deleteJournalArticle(long id) throws SQLException {

    }

    public void deleteAV(long id) throws SQLException {

    }

    public void deleteDocument(long id) throws SQLException {
        Document document = documentDAO.findById(id);
        if (document.getUsers() != null && document.getUsers().length() != 0) {
            return;
        } else {
            if (document.getAwaiters() != null && document.getAwaiters().length() != 0) {
                String[] awaiters = document.getAwaiters().split(",");
                for (String user_id: awaiters) {
                    User user = userDAO.findById(Long.parseLong(user_id));
                    ArrayList<String> waiting_list = new ArrayList(Arrays.asList(user.getWaiting_list().split(",")));
                    waiting_list.remove(waiting_list.indexOf(String.valueOf(id)));
                    String waiting = "";
                    if (waiting_list.size() == 1) {
                        waiting = waiting_list.get(0);
                    } else {
                        for (String id_doc : waiting_list) {
                            waiting = waiting.concat(","+id_doc);
                        }
                    }
                    user.setWaiting_list(waiting);
                    userDAO.update(user);
                }
            }
            switch (document.getType()) {
                case "book":
                    deleteBook(id);
                    documentDAO.delete(id);
                    break;
                case "journal article":
                    deleteJournalArticle(id);
                    documentDAO.delete(id);
                    break;
                case "av":
                    deleteAV(id);
                    documentDAO.delete(id);
                    break;
            }
        }
    }
    public long getChatId(long id) {
        long chat_id = 0;
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.chat_id FROM users WHERE users.id = '"+id+"';");
            while (rs.next()) {
                chat_id = rs.getLong("chat_id");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return chat_id;
    }
    public static void sendMsg(long chatId, String message) throws Exception {
        String postURL = "https://api.telegram.org/bot577066011:AAFK2TXevqQRFXkJjS_eAIEEaPH2MOcXJ_s/sendMessage";

        HttpPost post = new HttpPost(postURL);

        HttpClient client = new DefaultHttpClient();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("chat_id", Long.toString(chatId)));
        params.add(new BasicNameValuePair("text", message));

        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(ent);
        HttpResponse responsePOST = client.execute(post);
        System.out.println(chatId + message);
    }
}