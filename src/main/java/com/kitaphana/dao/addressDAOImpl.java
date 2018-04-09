package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Address;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class addressDAOImpl implements addressDAO {

    Database db = Database.getInstance();
    commonDAOImpl commonDAO = new commonDAOImpl();

    private static final String FIND_BY_ID = "SELECT * FROM addresses WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM addresses";
    private static final String INSERT = "INSERT INTO addresses (country, town, street, house_number, apartment_number, postcode) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE addresses SET country=?, town=?, street=?, house_number=?, apartment_number=?, postcode=? WHERE id=?";

    public long findLastId(){
        return commonDAO.findLastId("addresses");
    }

    @Override
    public Address findById(long id) {
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
                address.setHouseNumber(rs.getInt("house_number"));
                address.setApartmentNumber(rs.getInt("apartment_number"));
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
    public ArrayList<Address> findAll() {
        ArrayList<Address> addresses = new ArrayList<>();

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Address address = new Address();

                address.setCountry(rs.getString("country"));
                address.setTown(rs.getString("town"));
                address.setStreet(rs.getString("street"));
                address.setHouseNumber(rs.getInt("house_number"));
                address.setApartmentNumber(rs.getInt("apartment_number"));
                address.setPostcode(rs.getString("postcode"));
                address.setAddressId(rs.getInt("id"));

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
    public void insert(Address object) {
        try {
            PreparedStatement ps = db.con.prepareStatement(INSERT);
            ps.setString(1, object.getCountry());
            ps.setString(2, object.getTown());
            ps.setString(3, object.getStreet());
            ps.setInt(4, object.getHouseNumber());
            ps.setInt(5, object.getApartmentNumber());
            ps.setString(6, object.getPostcode());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Address object) {
        try {
            PreparedStatement ps = db.con.prepareStatement(UPDATE);
            ps.setString(1, object.getCountry());
            ps.setString(2, object.getTown());
            ps.setString(3, object.getStreet());
            ps.setInt(4, object.getHouseNumber());
            ps.setInt(5, object.getApartmentNumber());
            ps.setString(6, object.getPostcode());
            ps.setLong(7, object.getAddressId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        commonDAO.delete(id, "addresses", "id");
    }
}
