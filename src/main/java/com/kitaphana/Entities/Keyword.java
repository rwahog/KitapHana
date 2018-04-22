package com.kitaphana.Entities;

import java.util.ArrayList;
import java.util.Arrays;

public class Keyword {
    private long id;
    private String keyword, documentsId;
    private ArrayList<Document> documents;

    public Keyword() {

    }

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setDocumentsId(String documentsId) {
        this.documentsId = documentsId;
    }

    public String getDocumentsId() {
        return documentsId;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public ArrayList<Document> getDocuments(){ return documents; }
}
