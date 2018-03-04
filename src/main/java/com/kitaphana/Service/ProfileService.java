package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Address;
import com.kitaphana.Entities.User;

import java.sql.ResultSet;

public class ProfileService {

    Database db = new Database();

    public User setUserInfo(String id) {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = new User();

        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE id = '" + id + "'");
            int id_address = 0;
            while(rs.next()) {
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setType(rs.getString("type"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                id_address = rs.getInt("id_address");
            }
            rs = db.runSqlQuery("SELECT * FROM addresses WHERE id_address = '" + id_address +"'");
            while(rs.next()) {
                Address address = new Address();
                address.setCountry(rs.getString("country"));
                address.setTown(rs.getString("town"));
                address.setStreet(rs.getString("street"));
                address.setHouse_number(rs.getInt("house_number"));
                address.setApartment_number(rs.getInt("apartment_number"));
                address.setPostcode(rs.getString("postcode"));
                user.setAddress(address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

}
