package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.Patron;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.patronDAOImpl;
import com.kitaphana.exceptions.OperationFailedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class LibrarianPanelService {
  private Database db = Database.getInstance();
  private static final String FIND_CHECKOUTS = "SELECT id FROM users WHERE checkouts != ''";
  private static final String FIND_RENEWS = "SELECT id FROM users WHERE renews != ''";
  private static final String FIND_RETURNS = "SELECT id FROM users WHERE returns != ''";
  private static final String FIND_WAITINGS = "SELECT * FROM documents WHERE waiting_list != ''";
  private documentDAOImpl documentDAO = new documentDAOImpl();
  private patronDAOImpl userDAO = new patronDAOImpl();
  private DocumentService documentService = new DocumentService();

  public ArrayList<Patron> setCheckoutsInfo() {
    ArrayList<Patron> patrons = new ArrayList<>();
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_CHECKOUTS);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Patron patron = userDAO.findById(rs.getLong("id"));
        ArrayList<String> docs = documentService.fromDBStringToArray(patron.getCheckoutsId());
        ArrayList<Document> tempArr = new ArrayList<>();
        for (String id : docs) {
          Document document = documentDAO.findById(Long.parseLong(id));
          tempArr.add(document);
        }
        patron.setCheckouts(tempArr);
        patrons.add(patron);
      }

    } catch (SQLException e) {
      System.out.println("checkouts");
      throw new OperationFailedException();
    }
    return patrons;
  }

  public ArrayList<Patron> setRenewsInfo() {
    ArrayList<Patron> patrons = new ArrayList<>();
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_RENEWS);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Patron patron = userDAO.findById(rs.getLong("id"));
        ArrayList<String> docs = documentService.fromDBStringToArray(patron.getRenewsId());
        ArrayList<Document> tempArr = new ArrayList<>();
        for (String id : docs) {
          Document document = documentDAO.findById(Long.parseLong(id));
          tempArr.add(document);
        }
        patron.setRenews(tempArr);
        patrons.add(patron);
      }
    } catch (SQLException e) {
      System.out.println("renews");
      throw new OperationFailedException();
    }
    return patrons;
  }

  public ArrayList<Patron> setReturnsInfo() {
    ArrayList<Patron> patrons = new ArrayList<>();
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_RETURNS);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Patron patron = userDAO.findById(rs.getLong("id"));
        ArrayList<String> docs = documentService.fromDBStringToArray(patron.getReturnsId());
        ArrayList<Document> tempArr = new ArrayList<>();
        for (String id : docs) {
          Document document = documentDAO.findById(Long.parseLong(id));
          tempArr.add(document);
        }
        patron.setReturns(tempArr);
        patrons.add(patron);
      }

    } catch (SQLException e) {
      System.out.println("returns");
      throw new OperationFailedException();
    }
    return patrons;
  }

  public ArrayList<Document> setWaintingsInfo() {
    ArrayList<Document> docs = new ArrayList<>();
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_WAITINGS);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Document doc = new Document();
        doc.setId(rs.getLong("id"));
        doc.setTitle(rs.getString("title"));
        doc.setType(rs.getString("type"));
        String waitings = rs.getString("waiting_list");
        ArrayList tempArr = new ArrayList<>(Arrays.asList(waitings.split(",")));
        doc.setRequests(tempArr.size());
        docs.add(doc);
      }

    } catch (SQLException e) {
      System.out.println("waitings");
      throw new OperationFailedException();
    }
    return docs;
  }
}
