package com.kitaphana.Entities;

import java.util.ArrayList;

public class Author {
    protected long id;
    protected ArrayList<Document> documents;
    protected String name, surname, id_documents;

    public Author(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Author() {
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
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

    public void setId_documents(String id_documents) {
        this.id_documents = id_documents;
    }

    public long getId() {
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

    public String getId_documents() {
        return id_documents;
    }
}
