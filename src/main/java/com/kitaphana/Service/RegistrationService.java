package com.kitaphana.Service;

import com.kitaphana.Database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationService {
    public Database db = Database.getInstance();

    public boolean checkIfPossibleToRegister(String phone_number, String password1, String password2) {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dbPhone_Number;
        boolean possible = false;
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM users WHERE phone_number = '" + phone_number + "'");
            if (!rs.next() && password1.equals(password2)) {
                possible = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return possible;
    }

    public void saveUser(String name, String surname, String possible_type, String phone_number,
                         String password, String email, String country, String town, String street,
                         String house_number, String apart_number, String post_code) {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            long card_number = setCard_number(phone_number);
            db.runSqlUpdate("INSERT INTO addresses(country, town, street, house_number, apartment_number, postcode) VALUES('" + country + "', '" + town + "', '" + street + "', '" + house_number + "', '" + apart_number + "','" + post_code + "')");
            ResultSet resultSet = db.runSqlQuery("SELECT * FROM addresses WHERE country = '" + country + "' AND town = '" + town + "' AND street = '" + street + "' AND house_number = '" + house_number + "' AND apartment_number = '" + apart_number + "' AND postcode = '" + post_code + "'");
            int id_address = -1;
            if (resultSet.next()) {
                id_address = resultSet.getInt("id_address");
            }
            db.runSqlUpdate("INSERT INTO users (name, surname, phone_number, card_number, password, email, id_address, possible_type) VALUES('" + name + "', '" + surname + "', '" + phone_number + "', '" + card_number + "', '" + password + "', '" + email + "', '" + id_address + "', '" + possible_type + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long setCard_number(String s) throws SQLException {
        long p = 31, hash = 0, mod = 1000000007;
        for (int i = 0; i < s.length(); i++) {
            hash *= p;
            hash %= mod;
            hash += s.charAt(i);
            hash %= mod;
        }
        return hash;
    }

    public String getUserId(String phone_number) {
        String id = "";
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.id FROM users WHERE users.phone_number = '"+phone_number+"';");
            while (rs.next()) {
                id = rs.getString("id");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
