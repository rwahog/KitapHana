package com.kitaphana.Service;

import com.kitaphana.Database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationService {
    public Database db = new Database();
    public boolean checkIfPossibleToRegister(String phone_number, String password1, String password2){
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String dbPhone_Number;
        boolean possible = false;
        try {
            ResultSet rs = db.runSql("SELECT users.phone_number FROM users;");
            while(rs.next()){
                dbPhone_Number = rs.getString("phone_number");
                if(!dbPhone_Number.equals(phone_number) && password1.equals(password2)){
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
                            String house_number, String apart_number, String post_code){
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            long card_number = setCard_number(phone_number);
            db.runSql("insert into addresses(country, town, street, house_number, apartment_number, postcode) values('"+country+"', '"+town+"', '"+street+"', '"+house_number+"', '"+apart_number+"','"+post_code+"')");
            ResultSet resultSet = db.runSql("select * from addresses where country = '"+country+"' and town = '"+town+"' and street = '"+street+"' and house_number = '"+house_number+"'and apartment_number = '"+apart_number+"' and postcode = '"+post_code+"'");
            int id_address = -1;
            if(resultSet.next()){
                id_address = resultSet.getInt("id_address");
            }
            db.runSql("insert into users (name, surname, phone_number, card_number, password, id_address, possible_type) values('" + name + "', '" + surname + "', '" + phone_number + "', '" + card_number + "', '"+password+"', '" + id_address + "', '"+possible_type+"')");
        }catch (Exception e) {
                e.printStackTrace();
            }
    }
    public long setCard_number(String s) throws SQLException {
        long p = 31, hash = 0, mod = 1000000007;
        for(int i = 0; i<s.length(); i++){
            hash*=p;
            hash%=mod;
            hash+=s.charAt(i);
            hash%=mod;
        }
        return hash;
    }
//    public String getUserName(String phone_number){
//        String name = "A";
//        try {
//            db.connect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            ResultSet rs = db.runSql("SELECT users.name FROM users WHERE users.phone_number = '"+phone_number+"';");
//            name = rs.toString();
//            name = "a";
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return name;
//    }
}
