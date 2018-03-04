package com.kitaphana.Entities;

import java.util.ArrayList;

public class User {
    protected String phone_number, name, surname, password, possible_type, type, email;
    protected Address address;
    protected long card_number, maxdays, day = 24 * 60 * 60 * 1000;
    protected int id;
    protected ArrayList<Document> documents;
    protected ArrayList<Long> deadlines;
    
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCard_number(long card_number) {
        this.card_number = card_number;
    }

    public void setPossible_type(String possible_type) {
        this.possible_type = possible_type;
    }
    
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public long getCard_number() {
        return card_number;
    }

    public String getPossible_type() {
        return possible_type;
    }
    
    public String getPhone_number() {
        return phone_number;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }
}
