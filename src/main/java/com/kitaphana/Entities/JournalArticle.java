package com.kitaphana.Entities;

import java.util.ArrayList;

public class JournalArticle extends Document {
    protected String journalName, date, title;
    protected String editors;
    protected long id, documentId;

    public JournalArticle(String journalName, String date, String title, String editors) {
        this.journalName = journalName;
        this.date = date;
        this.title = title;
        this.editors = editors;
    }

    public JournalArticle(String title, String authors, String keywords, int price,
                          int amount, String type, String description,
                          String journalName, String date, String editors) {
        super(title, authors, keywords, price, amount, type, description);
        this.journalName = journalName;
        this.date = date;
        this.title = title;
        this.editors = editors;
    }

    public JournalArticle(){
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setJournalName(String journal_name) {
        this.journalName = journal_name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public void setDocumentId(long document_id) {
        this.documentId = document_id;
    }

    public String getJournalName() {
        return journalName;
    }

    public String getDate() {
        return date;
    }

    public String getEditors() {
        return editors;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public long getDocumentId() {
        return documentId;
    }
}
