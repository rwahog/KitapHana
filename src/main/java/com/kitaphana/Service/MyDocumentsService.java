package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Author;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.Keyword;
import com.kitaphana.Entities.Patron;
import com.kitaphana.dao.authorDAOImpl;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.keywordDAOImpl;
import com.kitaphana.dao.patronDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class MyDocumentsService {

  Database db = Database.getInstance();
  patronDAOImpl patronDAO = new patronDAOImpl();
  documentDAOImpl documentDAO = new documentDAOImpl();
  authorDAOImpl authorDAO = new authorDAOImpl();
  keywordDAOImpl keywordDAO = new keywordDAOImpl();
  DBService dbService = new DBService();

  public ArrayList<Document> setDocs(String id) {
    ArrayList<Document> docs = new ArrayList<>();
    try {
      ResultSet rs = db.runSqlQuery("SELECT users.documents, users.deadlines, users.fine FROM users WHERE id = '" + id + "'");
      while (rs.next()) {
        String user_id = rs.getString("documents");
        String user_deadlines = rs.getString("deadlines");
        String user_fine = rs.getString("fine");
        if (user_id.length() == 0 || user_deadlines.length() == 0 || user_fine.length() == 0) {
          return null;
        }
        String[] ids = user_id.split(",");
        String[] ids_dead = user_deadlines.split(",");
        String[] user_fines = user_fine.split(",");
        for (int i = 0; i < ids.length; i++) {
          ResultSet rs1 = db.runSqlQuery("SELECT * FROM documents WHERE id = '" + ids[i] + "'");
          Document doc = new Document();
          rs1.next();
          doc.setId(Integer.parseInt(ids[i]));
          doc.setTitle(rs1.getString("title"));
          doc.setAuthorsId(rs1.getString("authors"));
          doc.setType(rs1.getString("type"));
          doc.setFine(Integer.parseInt(user_fines[i]));
          doc.setDeadline(doc.getDeadlineOfDocument(Long.parseLong(ids_dead[i])));
          setAuthors(doc);
          docs.add(doc);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return docs;
  }

  public void setKeywords(Document document) {
    ArrayList<String> keywordsId = dbService.fromDBStringToArray(document.getKeywordsId());
    ArrayList<Keyword> keywords = new ArrayList<>();
    for (String keyId : keywordsId) {
      Keyword keyword = keywordDAO.findById(Long.parseLong(keyId));
      keywords.add(keyword);
    }
    document.setKeywords(keywords);
  }

  public void setAuthors(Document document) {
    ArrayList<String> authorsId = dbService.fromDBStringToArray(document.getAuthorsId());
    ArrayList<Author> authors = new ArrayList<>();
    for (String authorId : authorsId) {
      Author author = authorDAO.findById(Long.parseLong(authorId));
      authors.add(author);
    }
    document.setAuthors(authors);
  }

  public ArrayList<Document> setWaitingsInfo(Long user_id) throws SQLException {
    ArrayList<Document> waitings = new ArrayList<>();
    Patron patron = patronDAO.findById(user_id);
    String user_waitlist = patron.getWaitingListId();
    if (user_waitlist != null && user_waitlist.length() != 0) {
      ArrayList<String> docs = new ArrayList<>(Arrays.asList(user_waitlist.split(",")));
      for (String id_doc : docs) {
        Document document = documentDAO.findById(Long.parseLong(id_doc));
        waitings.add(document);
      }
    }
    return waitings;
  }

  public String renewDoc(String docId, String userId) {
    Patron patron = patronDAO.findById(Long.parseLong(userId));
    String userRenews = patron.getRenewsId();
    String available = dbService.findColumn(docId, "documents", "available");
    if (available.equals("0")) {
      return "This document is reference.";
    }
    ArrayList<String> userRenewsNum = dbService.fromDBStringToArray(dbService.findColumn(userId, "users", "renews_num"));
    ArrayList<String> userDocsId = dbService.fromDBStringToArray(dbService.findColumn(userId, "users", "documents"));
    int index = userDocsId.indexOf(docId);
    String type = patron.getType();
    if (Integer.parseInt(userRenewsNum.get(index)) >= 1 && !type.equals("Visiting Professor")) {
      return "This document was already renewed.";
    }
    if (userRenews.length() != 0) {
      patron.setRenewsId(userRenews.concat("," + docId));
    } else {
      patron.setRenewsId(docId);
    }
    patronDAO.update(patron);
    dbService.sendMessageToLibrarians("You have some work to do (new renew)");
    return "Successfully renewed.";
  }

  public void returnDoc(String docId, String userId) {
    String returns = dbService.findColumn(userId, "users", "returns");
    if (returns.length() != 0) {
      returns = returns.concat("," + docId);
    } else {
      returns = docId;
    }
    dbService.updateColumn(userId, returns, "users", "returns");
    dbService.sendMessageToLibrarians("You have some work to do (new return)");
  }

  public ArrayList<String> setNameAndSurname(String id) {
    ArrayList<String> nameAndSurname = new ArrayList<>();
    try {
      String name = dbService.findColumn(id, "users", "name");
      String surname = dbService.findColumn(id, "users", "surname");
      nameAndSurname.add(name);
      nameAndSurname.add(surname);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return nameAndSurname;
  }
}
