package com.kitaphana.algorithm;

import java.sql.*;
import java.util.Scanner;

public class Address {
    protected String country, town, postcode, street;
    protected int house_number, apartment_number, id_address;
    protected Connection connection;
    protected Statement statement;
    protected Scanner in;
    Address(Connection conn, Scanner in) throws SQLException {
        this.connection = conn;
        this.in = in;
        statement = connection.createStatement();
        id_address = -1;
    }
    public void readAddress() throws SQLException {
        System.out.println("Country: ");
        setCountry(in.next());
        System.out.println("Town: ");
        setTown(in.next());
        System.out.println("Street: ");
        setStreet(in.next());
        System.out.println("House number: ");
        setHouse_number(in.nextInt());
        System.out.println("Apartment number: ");
        setApartment_number(in.nextInt());
        System.out.println("Postcode: ");
        setPostcode(in.next());
    }
    public void save() throws SQLException {
        if(id_address >= 0) {
            statement.executeUpdate("update addresses set country = '" + country + "', town = '" + town + "', street = '" + street + "', house_number = '" + house_number + "', apartment_number = '" + apartment_number + "', postcode = '" + postcode + "' where id_address = '"+id_address+"'");
        }
        else{
            statement.executeUpdate("insert into addresses(country, town, street, house_number, apartment_number, postcode) values('"+country+"', '"+town+"', '"+street+"', '"+house_number+"', '"+apartment_number+"','"+postcode+"')");
            ResultSet resultSet = statement.executeQuery("select * from addresses where country = '"+country+"' and town = '"+town+"' and street = '"+street+"' and house_number = '"+house_number+"'and apartment_number = '"+apartment_number+"' and postcode = '"+postcode+"'");
            if(resultSet.next()){
                id_address = resultSet.getInt("id_address");
            }
        }

    }
    public void remove() throws SQLException {
        statement.executeUpdate("delete from addresses where id_address = '"+id_address+"'");
    }
    public void setVariablesKnowingId_address(int id_address) throws SQLException {
        this.id_address = id_address;
        ResultSet resultSet = statement.executeQuery("select * from addresses where id_address = '"+id_address+"'");
        if(resultSet.next()){
            setCountry(resultSet.getString("country"));
            setTown(resultSet.getString("town"));
            setStreet(resultSet.getString("street"));
            setHouse_number(resultSet.getInt("house_number"));
            setApartment_number(resultSet.getInt("apartment_number"));
            setPostcode(resultSet.getString("postcode"));
        }
    }
    //Country
    public void setCountry(String country) throws SQLException {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    //Town
    public void setTown(String town) throws SQLException {
        this.town = town;
    }

    public String getTown() {
        return town;
    }

    //House_number
    public void setHouse_number(int house_number) throws SQLException {
        this.house_number = house_number;
    }

    public int getHouse_number() {
        return house_number;
    }

    //Apartment_number
    public void setApartment_number(int apartment_number) throws SQLException {
        this.apartment_number = apartment_number;
    }

    public int getApartment_number() {
        return apartment_number;
    }

    //Postcode
    public void setPostcode(String postcode) throws SQLException {
        this.postcode = postcode;
    }
    public String getPostcode() {
        return postcode;
    }

    //Street
    public void setStreet(String street) throws SQLException {
        this.street = street;
    }
    public String getStreet() {
        return street;
    }

    //Id_address
    public int getId_address() {
        return id_address;
    }


}
