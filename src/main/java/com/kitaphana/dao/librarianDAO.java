package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Librarian;
import com.kitaphana.exceptions.OperationFailedException;
import com.kitaphana.exceptions.UserNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class librarianDAO {

    Database db = Database.getInstance();
    commonDAOImpl commonDAO = new commonDAOImpl();

    private static final String FIND_BY_ID = "SELECT * FROM librarians WHERE id=?";
    private static final String FIND_ALL = "SELECT id FROM librarians";
    private static final String INSERT = "INSERT INTO librarians (name, surname, card_number, phone_number, password, email, address_id, privilege) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE librarians SET name=?, surname=?, card_number=?, phone_number=?, password=?, email=?, chat_id=?, privilege=? WHERE id=?";
    private static final String FIND_BY_PHONE_NUMBER = "SELECT * FROM librarians WHERE phone_number=?";

    public long findLastId() {
        return commonDAO.findLastId("librarians");
    }

    public Librarian findById(long id) {
        Librarian librarian;
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                librarian = setVariables(rs);
            } else {
                throw new UserNotFoundException();
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return librarian;
    }

    public Librarian findByPhoneNumber(String phoneNumber) {
        Librarian librarian = null;
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_BY_PHONE_NUMBER);
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                librarian = setVariables(rs);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return librarian;
    }

    public ArrayList<Librarian> findAll() {
        ArrayList<Librarian> librarians = new ArrayList<>();
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Librarian librarian = findById(rs.getLong("id"));
                librarians.add(librarian);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return librarians;
    }

    public void insert(Librarian librarian) {
        try {
            PreparedStatement ps;
            ps = db.connect().prepareStatement(INSERT);

            setVariables(librarian, ps);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    public void update(Librarian librarian) {
        try {
            PreparedStatement ps;
            ps = db.connect().prepareStatement(UPDATE);
            setVariables(librarian, ps);
            ps.setLong(9, librarian.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    public void delete(long id) {
        commonDAO.delete(id, "librarians", "id");
    }

    private Librarian setVariables(ResultSet rs) {
        Librarian librarian;
        try {
            librarian = new Librarian();
            librarian.setId(rs.getLong("id"));
            librarian.setName(rs.getString("name"));
            librarian.setSurname(rs.getString("surname"));
            librarian.setCardNumber(rs.getLong("card_number"));
            librarian.setPhoneNumber(rs.getString("phone_number"));
            librarian.setPassword(rs.getString("password"));
            librarian.setEmail(rs.getString("email"));
            librarian.setAddressId(rs.getLong("address_id"));
            librarian.setChatId(rs.getLong("chat_id"));
            librarian.setPrivilege(rs.getInt("privilege"));
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return librarian;
    }

    private void setVariables(Librarian librarian, PreparedStatement ps) {
        try {
            ps.setString(1, librarian.getName());
            ps.setString(2, librarian.getSurname());
            ps.setLong(3, librarian.getCardNumber());
            ps.setString(4, librarian.getPhoneNumber());
            ps.setString(5, librarian.getPassword());
            ps.setString(6, librarian.getEmail());
            ps.setLong(7, librarian.getAddressId());
            ps.setInt(8, librarian.getPrivilege());
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }
}
