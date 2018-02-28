package com.kitaphana.Service;

import com.kitaphana.Database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationService {
    public Database db = new Database();

    public boolean checkIfPossibleToRegister(String phone_number, String password1, String password2) {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dbPhone_Number;
        boolean possible = false;
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.phone_number FROM users;");
            while (rs.next()) {
                dbPhone_Number = rs.getString("phone_number");
                if (!dbPhone_Number.equals(phone_number) && password1.equals(password2)) {
                    possible = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return possible;
    }

    public void saveUser(String name, String surname, String possible_type, String phone_number,
                         String password, String country, String town, String street,
                         String house_number, String apart_number, String post_code) {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            long card_number = setCard_number(phone_number);
            db.runSqlUpdate("insert into addresses(country, town, street, house_number, apartment_number, postcode) values('" + country + "', '" + town + "', '" + street + "', '" + house_number + "', '" + apart_number + "','" + post_code + "')");
            ResultSet resultSet = db.runSqlQuery("select * from addresses where country = '" + country + "' and town = '" + town + "' and street = '" + street + "' and house_number = '" + house_number + "'and apartment_number = '" + apart_number + "' and postcode = '" + post_code + "'");
            int id_address = -1;
            if (resultSet.next()) {
                id_address = resultSet.getInt("id_address");
            }
            db.runSqlUpdate("insert into users (name, surname, phone_number, card_number, password, id_address, possible_type) values('" + name + "', '" + surname + "', '" + phone_number + "', '" + card_number + "', '" + password + "', '" + id_address + "', '" + possible_type + "')");
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
}