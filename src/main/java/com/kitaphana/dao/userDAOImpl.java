package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class userDAOImpl implements userDAO {

    Database db = Database.getInstance();
    commonDAOImpl commonDAO = new commonDAOImpl();
    private static final String FIND_BY_ID = "SELECT * FROM users WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM users";
    private static final String INSERT = "INSERT INTO users (name, surname, card_number, phone_number, password, email, id_address, documents, deadlines, type, possible_type) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE users SET name=?, surname=?, card_number=?, phone_number=?, password=?, email=?, documents=?, deadlines=?, type=?, possible_type=?, waiting_list=?, fine=?, renews=?, returns=?, checkouts=?, chat_id=? WHERE id=?";
    private static final String FIND_BY_PHONE_NUMBER = "SELECT * FROM users WHERE phone_number=?";
    private static final String UPDATE_INFO = "UPDATE users SET name=?, surname=?, card_number=?, phone_number=?, password=?, email=?, possible_type=? WHERE id=?";

    @Override
    public User findById(long id) {
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
                user.setCardNumber(rs.getInt("card_number"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAddressId(rs.getInt("id_address"));
                user.setDocuments(rs.getString("documents"));
                user.setDeadlines(rs.getString("deadlines"));
                user.setType(rs.getString("type"));
                user.setPossibleType(rs.getString("possible_type"));
                user.setFine(rs.getString("fine"));
                user.setWaitingList(rs.getString("waiting_list"));
                user.setRenews(rs.getString("renews"));
                user.setReturns(rs.getString("returns"));
                user.setCheckouts(rs.getString("checkouts"));
                user.setChatId(rs.getLong("chat_id"));
                user.setPriority();
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findByPhoneNumber(String phone) {
        User user = null;
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_BY_PHONE_NUMBER);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCardNumber(rs.getInt("card_number"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAddressId(rs.getInt("id_address"));
                user.setDocuments(rs.getString("documents"));
                user.setDeadlines(rs.getString("deadlines"));
                user.setType(rs.getString("type"));
                user.setPossibleType(rs.getString("possible_type"));
                user.setFine(rs.getString("fine"));
                user.setWaitingList(rs.getString("waiting_list"));
                user.setRenews(rs.getString("renews"));
                user.setChatId(rs.getLong("chat_id"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> users = new ArrayList<>();

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            User user;
            while(rs.next()) {
                user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCardNumber(rs.getInt("card_number"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAddressId(rs.getInt("id"));
                user.setDocuments(rs.getString("documents"));
                user.setDeadlines(rs.getString("deadlines"));
                user.setType(rs.getString("type"));
                user.setPossibleType(rs.getString("possible_type"));
                user.setFine(rs.getString("fine"));
                user.setWaitingList(rs.getString("waiting_list"));
                user.setRenews(rs.getString("renews"));
                user.setChatId(rs.getLong("chat_id"));
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
    public void insert(User object) {
        try {
            PreparedStatement ps = db.con.prepareStatement(INSERT);
            ps.setString(1, object.getName());
            ps.setString(2, object.getSurname());
            ps.setLong(3, object.getCardNumber());
            ps.setString(4, object.getPhoneNumber());
            ps.setString(5, object.getPassword());
            ps.setString(6, object.getEmail());
            ps.setLong(7, object.getAddressId());
            ps.setString(8, object.getDocuments());
            ps.setString(9, object.getDeadlines());
            ps.setString(10, object.getType());
            ps.setString(11, object.getPossibleType());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try {
            PreparedStatement ps = db.con.prepareStatement(UPDATE);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setLong(3, user.getCardNumber());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getDocuments());
            ps.setString(8, user.getDeadlines());
            ps.setString(9, user.getType());
            ps.setString(10, user.getPossibleType());
            ps.setString(11, user.getWaitingList());
            ps.setString(12, user.getFine());
            ps.setString(13, user.getRenews());
            ps.setString(14, user.getReturns());
            ps.setString(15, user.getCheckouts());
            ps.setLong(16, user.getChatId());
            ps.setLong(17, user.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUserInfo(User user) {
        try {
            PreparedStatement ps = db.con.prepareStatement(UPDATE_INFO);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setLong(3, user.getCardNumber());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getPossibleType());
            ps.setLong(8, user.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        commonDAO.delete(id, "users", "id");
    }
}
