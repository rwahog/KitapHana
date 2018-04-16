package com.kitaphana.Entities;

import java.util.ArrayList;
import java.util.Arrays;

public class Keyword {
    protected long id;
    protected String keyword, documentsId;
    protected ArrayList<Document> documents;
    public Keyword() {

    }

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setDocumentsId(String documentsId) {
        this.documentsId = documentsId;
    }
    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }
    public long getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getDocumentsId() {
        return documentsId;
    }

    public ArrayList<Document> getDocuments(){ return documents; }
}
