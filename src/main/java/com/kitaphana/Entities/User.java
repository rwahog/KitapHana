package com.kitaphana.Entities;

public class User {
    private String name, surname, phoneNumber, email, password, type;
    private Address address;
    private long cardNumber, id, addressId, chatId;

    public User(long id, String name, String surname, String phone_number, String password,
                String email, long addressId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phone_number;
        this.password = password;
        this.email = email;
        this.cardNumber = setCardNumber(phone_number);
        this.addressId = addressId;
    }

    public User(String name, String surname, String phone_number, String password,
                String email, Address address) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phone_number;
        this.password = password;
        this.email = email;
        this.cardNumber = setCardNumber(phone_number);
        this.address = address;
    }

    public User() {
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.cardNumber = setCardNumber(phoneNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getChatId() {
        return chatId;
    }

    public long setCardNumber(String phone_number) {
        long p = 31, hash = 0, mod = 1000000007;
        for (int i = 0; i < phone_number.length(); i++) {
            hash *= p;
            hash %= mod;
            hash += phone_number.charAt(i);
            hash %= mod;
        }
        return hash;
    }
}
