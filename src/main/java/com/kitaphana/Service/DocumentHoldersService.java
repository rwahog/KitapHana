package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.Patron;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.patronDAOImpl;
import com.kitaphana.exceptions.OperationFailedException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DocumentHoldersService {
    Database db = Database.getInstance();
    patronDAOImpl patronDAO = new patronDAOImpl();
    DBService dbService = new DBService();
    DocumentService documentService = new DocumentService();

    public ArrayList<Patron> fillPage(String id) {
        ArrayList<Patron> patrons = new ArrayList<>();
        try {
            String usersStr = dbService.findColumn(id, "documents", "users");
            if (usersStr.length() == 0) {
                return null;
            }
            ArrayList<String> usersId = documentService.fromDBStringToArray(usersStr);
                for (String userId : usersId) {
                    Patron patron = patronDAO.findById(Long.parseLong(userId));
                    ArrayList<String> docs = documentService.fromDBStringToArray(patron.getDocumentsId());
                    ArrayList<String> deadlines = documentService.fromDBStringToArray(patron.getDeadlines());
                    ArrayList<String> fines = documentService.fromDBStringToArray(patron.getFines());
                    int index = docs.indexOf(id);
                    int fine = Integer.parseInt(fines.get(index));
                    if (fine != 0) {
                        patron.setDocumentFine(fine);
                    } else {
                        long deadline = Long.parseLong(deadlines.get(index));
                        patron.setDocumentDeadline(deadline);
                    }
                    patrons.add(patron);
                }
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperationFailedException();
        }
        return patrons;
    }

    public ArrayList<Patron> setAwaitersInfo(String doc_id) {
        ArrayList<Patron> awaiters = new ArrayList<>();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT waiting_list FROM documents WHERE id='" + doc_id + "' AND waiting_list != ''");
            rs.next();
            String awaiters_string = rs.getString("waiting_list");
            String[] waiting_list = awaiters_string.split(",");
            for (String user_id: waiting_list) {
                ResultSet user_db = db.runSqlQuery("SELECT users.id, users.name, users.surname, users.type FROM users WHERE id='" + user_id+"'");
                if (user_db.next()) {
                    Patron patron = new Patron();
                    patron.setId(user_db.getLong("id"));
                    patron.setName(user_db.getString("name"));
                    patron.setSurname(user_db.getString("surname"));
                    patron.setType(user_db.getString("type"));
                    awaiters.add(patron);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return awaiters;
    }
}
