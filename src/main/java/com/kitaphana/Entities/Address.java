package com.kitaphana.Entities;

public class Address {
    protected String country, town, postcode, street;
    protected int houseNumber, apartmentNumber;
    protected long addressId;

    public Address(String country, String town, String street, int houseNumber, int apartmentNumber, String postcode, long addressId) {
        this.country = country;
        this.town = town;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.postcode = postcode;
        this.addressId = addressId;
    }

    public Address(String country, String town, String street, int houseNumber, int apartmentNumber, String postcode) {
        this.country = country;
        this.town = town;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.postcode = postcode;
    }

    public Address() {
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
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

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public long getAddressId() {
        return addressId;
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

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public String getPostcode() {
        return postcode;
    }
}
