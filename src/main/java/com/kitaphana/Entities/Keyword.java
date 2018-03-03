package com.kitaphana.Entities;

import java.util.ArrayList;

public class Keyword {
    protected int id;
    protected ArrayList<Document> documents;
    protected String keyword;

    public void setId(int id) {
        this.id = id;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public String getKeyword() {
        return keyword;
    }
}
