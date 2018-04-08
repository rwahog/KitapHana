package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class userDAOImpl implements userDAO {

    Database db = Database.getInstance();
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM users";
    private static final String INSERT = "INSERT INTO users (name, surname, card_number, phone_number, password, email, id_address, documents, deadlines, type, possible_type) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE users SET name=?, surname=?, card_number=?, phone_number=?, password=?, email=?, possible_type=?, waiting_list=?, renews=?, chat_id=? WHERE id=?";
    private static final String DELETE = "DELETE FROM users WHERE id=?";
    private static final String FIND_BY_PHONE_NUMBER = "SELECT * FROM users WHERE phone_number=?";
    private static final String FIND_BY_CHATID = "SELECT * FROM users WHERE chat_id=?";

    @Override
    public User findById(long id) throws SQLException {
        User user = null;
        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCard_number(rs.getInt("card_number"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setId_address(rs.getInt("id_address"));
                user.setId_documents(rs.getString("documents"));
                user.setDeadlines(rs.getString("deadlines"));
                user.setType(rs.getString("type"));
                user.setPossible_type(rs.getString("possible_type"));
                user.setFine(rs.getInt("fine"));
                user.setWaiting_list(rs.getString("waiting_list"));
                user.setRenews(rs.getString("renews"));
                user.setChat_id(rs.getLong("chat_id"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findByPhoneNumber(String phone) throws SQLException {
        User user = null;
        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_PHONE_NUMBER);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCard_number(rs.getInt("card_number"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setId_address(rs.getInt("id_address"));
                user.setId_documents(rs.getString("documents"));
                user.setDeadlines(rs.getString("deadlines"));
                user.setType(rs.getString("type"));
                user.setPossible_type(rs.getString("possible_type"));
                user.setFine(rs.getInt("fine"));
                user.setWaiting_list(rs.getString("waiting_list"));
                user.setRenews(rs.getString("renews"));
                user.setChat_id(rs.getLong("chat_id"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findByCharId(long chatId) throws SQLException {
        User user = null;
        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_CHATID);
            ps.setLong(1, chatId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCard_number(rs.getInt("card_number"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setId_address(rs.getInt("id_address"));
                user.setId_documents(rs.getString("documents"));
                user.setDeadlines(rs.getString("deadlines"));
                user.setType(rs.getString("type"));
                user.setPossible_type(rs.getString("possible_type"));
                user.setFine(rs.getInt("fine"));
                user.setWaiting_list(rs.getString("waiting_list"));
                user.setRenews(rs.getString("renews"));
                user.setChat_id(rs.getLong("chat_id"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public ArrayList<User> findAll() throws SQLException {
        ArrayList<User> users = new ArrayList<>();

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            User user = null;
            while(rs.next()) {
                user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCard_number(rs.getInt("card_number"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setId_address(rs.getInt("id_address"));
                user.setId_documents(rs.getString("documents"));
                user.setDeadlines(rs.getString("deadlines"));
                user.setType(rs.getString("type"));
                user.setPossible_type(rs.getString("possible_type"));
                user.setFine(rs.getInt("fine"));
                user.setWaiting_list(rs.getString("waiting_list"));
                user.setRenews(rs.getString("renews"));
                user.setChat_id(rs.getLong("chat_id"));
                users.add(user);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }

    @Override
    public void insert(User object) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(INSERT);
            ps.setString(1, object.getName());
            ps.setString(2, object.getSurname());
            ps.setLong(3, object.getCard_number());
            ps.setString(4, object.getPhone_number());
            ps.setString(5, object.getPassword());
            ps.setString(6, object.getEmail());
            ps.setLong(7, object.getId_address());
            ps.setString(8, object.getId_documents());
            ps.setString(9, object.getDeadlines());
            ps.setString(10, object.getType());
            ps.setString(11, object.getPossible_type());
//            ps.setLong(12, object.getChat_id());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User object) throws SQLException {
        try{
            PreparedStatement ps = db.con.prepareStatement(UPDATE);
            ps.setString(1, object.getName());
            ps.setString(2, object.getSurname());
            ps.setLong(3, object.getCard_number());
            ps.setString(4, object.getPhone_number());
            ps.setString(5, object.getPassword());
            ps.setString(6, object.getEmail());
            ps.setString(7, object.getPossible_type());
            ps.setString(8, object.getWaiting_list());
            ps.setString(9, object.getRenews());
            ps.setLong(10, object.getChat_id());
            ps.setLong(11, object.getId());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(DELETE);
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
