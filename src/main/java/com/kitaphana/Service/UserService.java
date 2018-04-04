package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Address;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.addressDAOImpl;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.userDAOImpl;

import javax.print.Doc;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    Database db = Database.getInstance();
    userDAOImpl userDAO = new userDAOImpl();
    addressDAOImpl addressDAO = new addressDAOImpl();
    documentDAOImpl documentDAO = new documentDAOImpl();

    public ArrayList<User> setUsersInfo() throws SQLException {
        ArrayList<User> users = userDAO.findAll();
        return users;
    }

    public long getUserId(String phone) {
        final String query = "SELECT users.id FROM users WHERE users.phone_number=?";
        long id = 0;
        try {
            PreparedStatement ps = db.connect().prepareStatement(query);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public long getCharId(String phone) {
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

    public long getUserId_address(long id) {
        final String query = "SELECT users.id_address FROM users WHERE users.id=?";
        long id_address = 0;
        try {
            PreparedStatement ps = db.con.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id_address = rs.getLong("id_address");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_address;
    }

    public User createUser(String name, String surname, String phone_number, String password,
                           String email, Address address, String possible_type) {
        return new User(name, surname, phone_number, password, email, address, possible_type);
    }

    public User findUserById(long id) throws SQLException {
        User user = userDAO.findById(id);
        user.setAddress(addressDAO.findById(user.getId_address()));
        return user;
    }
    public User findUserByChatId(long chatId) throws SQLException {
        User user = userDAO.findByCharId(chatId);
        return user;
    }

    public User findUserByPhoneNumber(String phone) throws SQLException {
        User user = userDAO.findByPhoneNumber(phone);
        user.setAddress(addressDAO.findById(user.getId_address()));
        return user;
    }

    public ArrayList<User> findAlL() throws SQLException {
        return userDAO.findAll();
    }

//    public void deleteUserDocs(long id) throws SQLException {
//         User user = userDAO.findById(id);
//         String docs_id = user.getId_documents();
//         if (docs_id == null || docs_id.length() == 0) {
//             return;
//         }
//         ArrayList<String> docs_id_arr = user.getDocumentsAsArray();
//         for (int i = 0; i < docs_id_arr.size(); i++) {
//             Document doc = documentDAO.findById(Long.parseLong(docs_id_arr.get(i)));
//             doc.setAmount(doc.getAmount() + 1);
//             ArrayList<String> docs = doc.getUsersAsArray();
//             docs.remove(String.valueOf(id));
//             String full = "";
//             for (String usr: docs) {
//                 full = full.concat(usr);
//             }
//             doc.setUsers();
//             documentDAO.update(doc);
//         }
//    }

    public void deleteUserAddress(long id) throws SQLException {
        addressDAO.delete(getUserId_address(id));
    }

    public void deleteUser(long id) throws SQLException {
        User user = userDAO.findById(id);
        if ((user.getId_documents() != null && user.getId_documents().length() != 0) || (user.getFine() != 0)) {
            return;
        } else {
            deleteUserAddress(id);
            userDAO.delete(id);
        }
    }

    public User setUserInfo(long id) throws SQLException {
        User user = userDAO.findById(id);
        user.setAddress(addressDAO.findById(user.getId_address()));
        return user;
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

    public void editUser(User user) throws SQLException {
        addressDAO.update(user.getAddress());
        userDAO.update(user);
    }

    public ArrayList<Document> fillPage(long id) throws SQLException {
        ArrayList<Document> documents = new ArrayList<>();
        String ids = userDAO.findById(id).getId_documents();
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

    public ArrayList setNameAndSurname(String id) {
        ArrayList name = new ArrayList();
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.name, users.surname FROM users WHERE users.id = '"+id+"';");
            while (rs.next()) {
                name.add(rs.getString("name"));
                name.add(rs.getString("surname"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return name;
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

    public void saveUser(User user) throws SQLException {
        addressDAO.insert(user.getAddress());
        user.setId_address(addressDAO.findLastId());
        userDAO.insert(user);
    }
}
