package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.Librarian;
import com.kitaphana.Entities.Patron;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.addressDAOImpl;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.librarianDAO;
import com.kitaphana.dao.patronDAOImpl;
import com.kitaphana.exceptions.UserNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    Database db = Database.getInstance();
    private patronDAOImpl patronDAO = new patronDAOImpl();
    private addressDAOImpl addressDAO = new addressDAOImpl();
    private documentDAOImpl documentDAO = new documentDAOImpl();
    private librarianDAO librarianDAO = new librarianDAO();
    private DBService dbService = new DBService();

    public long getUserId(String phone) {
        return Long.parseLong(dbService.findColumn(phone, "users", "id", "phone_number"));
    }

    private long getUserAddressId(long id) {
        return Long.parseLong(dbService.findColumn(String.valueOf(id), "users", "id_address"));
    }

    public ArrayList<String> phones() {
        return dbService.findAllPhones();
    }

    public Patron findUserById(long id) {
        Patron patron;
        patron = patronDAO.findById(id);
        patron.setAddress(addressDAO.findById(patron.getAddressId()));
        return patron;
    }

    public long getChatId(String phone) {
        final String query = "SELECT users.id FROM users WHERE users.chat_id=?";
        long chat_id = 0;
        try {
            PreparedStatement ps = db.connect().prepareStatement(query);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                chat_id = rs.getLong("char_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chat_id;
    }

    public Patron findUserByPhoneNumber(String phone) {
        Patron patron;
        patron = patronDAO.findByPhoneNumber(phone);
        patron.setAddress(addressDAO.findById(patron.getAddressId()));
        return patron;
    }

    public ArrayList<Patron> findAll() {
        return patronDAO.findAll();
    }

    public int numberOfUnconfirmedUsers() {
        int count = 0;
        final String statement = "SELECT COUNT(users.id) AS count FROM users WHERE users.possible_type <> users.type";
        try {
            PreparedStatement ps = db.connect().prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    private void deleteUserAddress(long id) {
        addressDAO.delete(getUserAddressId(id));
    }

    public void deletePatron(long id) {
        Patron patron = patronDAO.findById(id);
        if (patron == null) {
            throw new UserNotFoundException();
        } else if ((patron.getDocuments().size() != 0) || (patron.getFines().length() != 0)) {
            return;
        } else {
            deleteUserAddress(id);
            patronDAO.delete(id);
        }
    }

    public boolean isValid(long id, String phone_number, String password1, String password2) {
        try {
            db.connect();
            ResultSet rs = db.runSqlQuery("SELECT users.phone_number FROM users WHERE users.phone_number ='" + phone_number + "' AND users.id <> '" + id + "'");
            if (rs.next()) {
                return false;
            }
            if (password1.equals(password2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void editUser(Patron patron) {
        addressDAO.update(patron.getAddress());
        patronDAO.update(patron);
    }

    public void editUserInfo(Patron patron, String type) {
        addressDAO.update(patron.getAddress());
        patronDAO.updateUserInfo(patron, type);
        if (!patron.getType().equals(patron.getPossibleType())) {
            dbService.sendMessageToLibrarians("User" + patron.getName() + " " +
                            patron.getSurname() + "(id: " + patron.getId() + ")" +
                            "has unconfirmed type.");
        }
    }

    public ArrayList<Document> fillPage(long id) {
        ArrayList<Document> documents = new ArrayList<>();
        String ids = patronDAO.findById(id).getDocumentsId();
        if (ids == null || ids.length() == 0) {
            return null;
        }
        String[] idss = ids.split(",");
        for (int i = 0; i < idss.length; i++) {
            Document doc = null;
            doc = documentDAO.findById(Long.parseLong(idss[i]));
            documents.add(doc);
        }
        return documents;
    }

    public boolean checkIfPossibleToRegister(String phone_number) {
        boolean possible = false;
        try {
            db.connect();
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE phone_number = '" + phone_number + "'");
            if (!rs.next()) {
                possible = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return possible;
    }
    public boolean checkIfPossiblePassword(String password1, String password2){
        boolean possible = false;
        if (password1.equals(password2) && password1.length() > 5)
            possible = true;
        return possible;
    }

    public void savePatron(Patron patron) {
        addressDAO.insert(patron.getAddress());
        patron.setAddressId(addressDAO.findLastId());
        patronDAO.insert(patron);
    }

    public boolean isLibrarian(String phone_number) {
        boolean isLib = false;
        Librarian librarian = librarianDAO.findByPhoneNumber(phone_number);
            if (librarian != null) {
                isLib = true;
            }
        return isLib;
    }
}
