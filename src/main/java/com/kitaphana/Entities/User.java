package com.kitaphana.Entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class User {
    protected String phone_number, name, surname, password, possible_type, type, email, id_documents, deadlines, waiting_list, renews;
    protected Address address;
    protected long deadline;
    protected long card_number, maxdays, day = 24 * 60 * 60 * 1000;
    protected long id, id_address;
    protected ArrayList<Document> documents;
    //    protected ArrayList<Long> deadlines;
    protected int priority;
    protected int fine;

    public User(String name, String surname, String phone_number, String password,
                String email, Address address, String possible_type) {
        this.name = name;
        this.surname = surname;
        this.phone_number = phone_number;
        this.password = password;
        this.email = email;
        this.possible_type = possible_type;
        this.card_number = setCard_number(phone_number);
        this.address = address;
    }

    public User() {
    }

    public void setId(long id) {
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

    public void setId_address(long id_address) {
        this.id_address = id_address;
    }

    public void setDeadlines(String deadlines) {
        this.deadlines = deadlines;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId_documents(String id_documents) {
        this.id_documents = id_documents;
    }

    public void setRenews(String renews) {
        this.renews = renews;
    }

    public void setPriority() {
        switch (type) {
            case "Student":
                this.priority = 5;
            case "Instructor":
                this.priority = 4;
            case "Teacher Assistant":
                this.priority = 3;
            case "Visiting Professor":
                this.priority = 2;
            case "Professor":
                this.priority = 1;
        }

    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public void setWaiting_list(String waiting_list) {
        this.waiting_list = waiting_list;
    }

    public long getId() {
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

    public long getId_address() {
        return id_address;
    }

    public String getDeadlines() {
        return deadlines;
    }

    public int getPriority() {
        return priority;
    }

    public String getType() {
        return type;
    }

    public String getId_documents() {
        return id_documents;
    }

    public String getRenews() {
        return renews;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public long getDeadline() {
        return deadline;
    }

    public int getFine() {
        return fine;
    }

    public String getWaiting_list() {
        return waiting_list;
    }

    public ArrayList<String> getDocumentsAsArray() {
        ArrayList<String> documents = null;
        if (id_documents.length() > 0) {
            documents = new ArrayList<>(Arrays.asList(getId_documents().split(",")));
        }
        return documents;
    }

    public long setCard_number(String phone_number) {
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
