package com.kitaphana.Service;

import com.kitaphana.Entities.Address;
import com.kitaphana.dao.addressDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddressService {

    addressDAOImpl addressDAO = new addressDAOImpl();

    public void deleteAddress(int id) throws SQLException {
        addressDAO.delete(id);
    }

    public void updateAddress(Address address) throws SQLException {
        addressDAO.update(address);
    }

    public void addAddress(Address address) throws SQLException {
        addressDAO.insert(address);
    }

    public Address findAddressById(int id) throws SQLException {
        return addressDAO.findById(id);
    }

    public ArrayList<Address> findAllAddresses() throws SQLException {
        return addressDAO.findAll();
    }

    public Address createAddress(String country, String town, String street,
                                 int house_number, int apartment_number, String postcode, long id_address) {
        return new Address(country, town, street, house_number, apartment_number, postcode, id_address);
    }

    public Address createAddressWithoutId(String country, String town, String street,
                                          int house_number, int apartment_number, String postcode) {
        return new Address(country, town, street, house_number, apartment_number, postcode);
    }
}