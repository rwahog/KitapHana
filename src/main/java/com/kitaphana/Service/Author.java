package com.kitaphana.Service;

import java.util.ArrayList;

public class Author {
    protected int id;
    protected ArrayList<Document> documents;
    protected String name, surname;

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
