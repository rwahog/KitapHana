package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Address;
import com.kitaphana.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditUserService {

    private Database db = new Database();
    private RegistrationService regService = new RegistrationService();

    public User setUserInfo(int id) {
        User user = new User();
        Address address = new Address();
        int id_address = 0;
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE id = '" + id + "'");
            while (rs.next()) {
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));
                user.setCard_number(rs.getInt("card_number"));
                user.setPhone_number(rs.getString("phone_number"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                id_address = rs.getInt("id_address");
                user.setPossible_type(rs.getString("possible_type"));
            }
            rs = db.runSqlQuery("SELECT * FROM addresses WHERE id_address = '" + id_address + "'");
            while (rs.next()) {
                address.setId_address(rs.getInt("id_address"));
                address.setCountry(rs.getString("country"));
                address.setTown(rs.getString("town"));
                address.setStreet(rs.getString("street"));
                address.setHouse_number(rs.getInt("house_number"));
                address.setApartment_number(rs.getInt("apartment_number"));
                address.setPostcode(rs.getString("postcode"));
            }
            user.setAddress(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean isValid(int id, String phone_number, String password1, String password2) {
        String previousPhoneNumber = "";
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = db.runSqlQuery("SELECT users.phone_number FROM users WHERE id ='" + id + "'");
            while (rs.next()) {
                previousPhoneNumber = rs.getString("phone_number");
            }
            if (phone_number.equals(previousPhoneNumber) && password1.equals(password2)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return regService.checkIfPossibleToRegister(phone_number, password1, password2);
    }

    public void editUser(String name, String surname, String possible_type, String phone_number,
                         String password, String email, String country, String town, String street,
                         String house_number, String apart_number, String post_code, int id, int id_address) throws SQLException {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs = db.runSqlQuery("SELECT users.id, users.id_address, users.name, users.surname, users.card_number, users.possible_type FROM users WHERE id = '"+ id+"';");
        rs.next();
        id_address = rs.getInt("id_address");
        try {
            System.out.println("YA TUT YOBA ALLO "+ town+" "+id_address+" "+id);
            db.runSqlUpdate("UPDATE addresses SET country = '" + country +"', town = '" + town + "', street = '" + street + "', house_number = '" + house_number + "', apartment_number = '" + apart_number + "', postcode = '" + post_code + "' WHERE id_address = '" + id_address + "'");
            db.runSqlUpdate("UPDATE users SET name = '" + name + "', surname = '" + surname + "', phone_number = '" + phone_number + "', password = '" + password + "', email = '" + email + "', type = '" + possible_type + "' WHERE id = '" + id + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
