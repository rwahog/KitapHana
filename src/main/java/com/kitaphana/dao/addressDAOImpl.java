package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Address;
import com.kitaphana.exceptions.AddressNotFoundException;
import com.kitaphana.exceptions.OperationFailedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class addressDAOImpl implements addressDAO {

  Database db = Database.getInstance();
  commonDAOImpl commonDAO = new commonDAOImpl();

  private static final String FIND_BY_ID = "SELECT * FROM addresses WHERE id=?";
  private static final String FIND_ALL = "SELECT id FROM addresses";
  private static final String INSERT = "INSERT INTO addresses (country, town, street, house_number, apartment_number, postcode) VALUES (?,?,?,?,?,?)";
  private static final String UPDATE = "UPDATE addresses SET country=?, town=?, street=?, house_number=?, apartment_number=?, postcode=? WHERE id=?";

  public long findLastId() {
    return commonDAO.findLastId("addresses");
  }

  @Override
  public Address findById(long id) {
    Address address;
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID);
      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        address = setVariables(rs);
      } else {
        throw new AddressNotFoundException();
      }

      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }

    return address;
  }

  @Override
  public ArrayList<Address> findAll() {
    ArrayList<Address> addresses = new ArrayList<>();
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_ALL);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Address address = findById(rs.getLong("id"));
        addresses.add(address);
      }

      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return addresses;
  }

  @Override
  public void insert(Address address) {
    try {
      PreparedStatement ps = db.connect().prepareStatement(INSERT);
      setVariables(address, ps);

      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  @Override
  public void update(Address address) {
    try {
      PreparedStatement ps = db.connect().prepareStatement(UPDATE);
      setVariables(address, ps);
      ps.setLong(7, address.getAddressId());

      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  @Override
  public void delete(long id) {
    commonDAO.delete(id, "addresses", "id");
  }

  private Address setVariables(ResultSet rs) {
    Address address;
    try {
      address = new Address();
      address.setAddressId(rs.getLong("id"));
      address.setCountry(rs.getString("country"));
      address.setTown(rs.getString("town"));
      address.setStreet(rs.getString("street"));
      address.setHouseNumber(rs.getInt("house_number"));
      address.setApartmentNumber(rs.getInt("apartment_number"));
      address.setPostcode(rs.getString("postcode"));
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return address;
  }

  private void setVariables(Address address, PreparedStatement ps) {
    try {
      ps.setString(1, address.getCountry());
      ps.setString(2, address.getTown());
      ps.setString(3, address.getStreet());
      ps.setInt(4, address.getHouseNumber());
      ps.setInt(5, address.getApartmentNumber());
      ps.setString(6, address.getPostcode());
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }
}
