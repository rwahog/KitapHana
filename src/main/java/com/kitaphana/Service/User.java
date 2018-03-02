package com.kitaphana.Service;

import com.kitaphana.algorithm.Address;
import com.kitaphana.algorithm.Document;

import java.util.ArrayList;

public class User {
    protected String phone_number, name, surname, password, possible_type;
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
}
