package com.kitaphana.Entities;

public class Address {
    protected String country, town, postcode, street;
    protected int house_number, apartment_number;
    protected long id_address;

    public Address(String country, String town, String street, int house_number, int apartment_number, String postcode, long id_address) {
        this.country = country;
        this.town = town;
        this.street = street;
        this.house_number = house_number;
        this.apartment_number = apartment_number;
        this.postcode = postcode;
        this.id_address = id_address;
    }

    public Address(String country, String town, String street, int house_number, int apartment_number, String postcode) {
        this.country = country;
        this.town = town;
        this.street = street;
        this.house_number = house_number;
        this.apartment_number = apartment_number;
        this.postcode = postcode;
    }

    public Address() {
    }

    public void setId_address(long id_address) {
        this.id_address = id_address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouse_number(int house_number) {
        this.house_number = house_number;
    }

    public void setApartment_number(int apartment_number) {
        this.apartment_number = apartment_number;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public long getId_address() {
        return id_address;
    }

    public String getCountry() {
        return country;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public int getHouse_number() {
        return house_number;
    }

    public int getApartment_number() {
        return apartment_number;
    }

    public String getPostcode() {
        return postcode;
    }
}
