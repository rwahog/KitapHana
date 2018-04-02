package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Address;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class addressDAOImpl implements addressDAO {

    Database db = Database.getInstance();

    private static final String FIND_BY_ID = "SELECT * FROM addresses WHERE id_address=?";
    private static final String FIND_ALL = "SELECT * FROM addresses";
    private static final String INSERT = "INSERT INTO addresses (country, town, street, house_number, apartment_number, postcode) VALUES (?,?,?,?,?,?)";
    private static final String DELETE = "DELETE FROM addresses WHERE id_address=?";
    private static final String UPDATE = "UPDATE addresses SET country=?, town=?, street=?, house_number=?, apartment_number=?, postcode=? WHERE id_address=?";
    private static final String FIND_LAST_ID = "SELECT MAX(id_address) AS maxID FROM addresses";

    public long findLastId() throws SQLException {
        long id_address = 0;
        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_LAST_ID);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                id_address = rs.getInt("maxID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id_address;
    }

    @Override
    public Address findById(long id) throws SQLException {
        Address address = null;

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                address = new Address();
                address.setCountry(rs.getString("country"));
                address.setTown(rs.getString("town"));
                address.setStreet(rs.getString("street"));
                address.setHouse_number(rs.getInt("house_number"));
                address.setApartment_number(rs.getInt("apartment_number"));
                address.setPostcode(rs.getString("postcode"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return address;
    }

    @Override
    public ArrayList<Address> findAll() throws SQLException {
        ArrayList<Address> addresses = new ArrayList<>();

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Address address = new Address();

                address.setCountry(rs.getString("country"));
                address.setTown(rs.getString("town"));
                address.setStreet(rs.getString("street"));
                address.setHouse_number(rs.getInt("house_number"));
                address.setApartment_number(rs.getInt("apartment_number"));
                address.setPostcode(rs.getString("postcode"));
                address.setId_address(rs.getInt("id"));

                addresses.add(address);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return addresses;
    }

    @Override
    public void insert(Address object) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(INSERT);
            ps.setString(1, object.getCountry());
            ps.setString(2, object.getTown());
            ps.setString(3, object.getStreet());
            ps.setInt(4, object.getHouse_number());
            ps.setInt(5, object.getApartment_number());
            ps.setString(6, object.getPostcode());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Address object) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(UPDATE);
            ps.setString(1, object.getCountry());
            ps.setString(2, object.getTown());
            ps.setString(3, object.getStreet());
            ps.setInt(4, object.getHouse_number());
            ps.setInt(5, object.getApartment_number());
            ps.setString(6, object.getPostcode());
            ps.setLong(7, object.getId_address());

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
