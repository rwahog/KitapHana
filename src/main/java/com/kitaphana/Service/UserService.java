package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.addressDAOImpl;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.userDAOImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    Database db = Database.getInstance();
    userDAOImpl userDAO = new userDAOImpl();
    addressDAOImpl addressDAO = new addressDAOImpl();
    documentDAOImpl documentDAO = new documentDAOImpl();
    DBService dbService = new DBService();

    public long getUserId(String phone) {
        return Long.parseLong(dbService.findColumn(phone, "users", "id", "phone_number"));
    }

    public long getUserAddressId(long id) {
        return Long.parseLong(dbService.findColumn(String.valueOf(id), "users", "id_address"));
    }

    public User findUserById(long id) {
        User user;
        user = userDAO.findById(id);
        user.setAddress(addressDAO.findById(user.getAddressId()));
        return user;
    }

    public long getChatId(String phone) {
        final String query = "SELECT users.id FROM users WHERE users.chat_id=?";
        long char_id = 0;
        try {
            PreparedStatement ps = db.connect().prepareStatement(query);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                char_id = rs.getLong("char_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return char_id;
    }

    public User findUserByPhoneNumber(String phone) {
        User user;
        user = userDAO.findByPhoneNumber(phone);
        user.setAddress(addressDAO.findById(user.getAddressId()));
        return user;
    }

    public ArrayList<User> findAll() {
        return userDAO.findAll();
    }


    public void deleteUserAddress(long id) {
        addressDAO.delete(getUserAddressId(id));
    }

    public void deleteUser(long id) {
        User user = userDAO.findById(id);
        if ((user.getDocuments() != null && user.getDocuments().length() != 0) || (user.getFine() != "")) {
            return;
        } else {
            deleteUserAddress(id);
            userDAO.delete(id);
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

    public void editUser(User user) {
        addressDAO.update(user.getAddress());
        userDAO.update(user);
    }

    public void editUserInfo(User user, String type) {
        addressDAO.update(user.getAddress());
        userDAO.updateUserInfo(user, type);
        if (!user.getType().equals(user.getPossibleType())) {
            dbService.sendMessageToLibrarians("User" + user.getName() + " " +
                            user.getSurname() + "(id: " + user.getId() + ")" +
                            "has unconfirmed type.");
        }
    }

    public ArrayList<Document> fillPage(long id) {
        ArrayList<Document> documents = new ArrayList<>();
        String ids = userDAO.findById(id).getDocuments();
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

    public boolean checkIfPossibleToRegister(String phone_number, String password1, String password2) {
        boolean possible = false;
        try {
            db.connect();
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE phone_number = '" + phone_number + "'");
            if (!rs.next() && password1.equals(password2)) {
                possible = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return possible;
    }

    public void saveUser(User user) {
        addressDAO.insert(user.getAddress());
        user.setAddressId(addressDAO.findLastId());
        userDAO.insert(user);
    }

    public boolean isLibrarian(String phone_number) {
        boolean isLib = false;
            User user = userDAO.findByPhoneNumber(phone_number);
            if (user.getType().equals("Librarian")) {
                isLib = true;
            }
        return isLib;
    }
}
