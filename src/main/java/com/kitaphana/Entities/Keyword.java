package com.kitaphana.Entities;

import java.util.ArrayList;
import java.util.Arrays;

public class Keyword {
    protected long id;
    protected ArrayList<Document> documents;
    protected String keyword, id_documents;

    public Keyword() {
    }

    public Keyword(String keyword) {
        this.keyword = keyword;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public String getKeyword() {
        return keyword;
    }

    public String getId_documents() {
        return id_documents;
    }

    public ArrayList getDocumentsAsArray() {
        ArrayList<String> documents = new ArrayList<>(Arrays.asList(getId_documents().split(",")));
        return documents;
    }
}
