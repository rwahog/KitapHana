package com.kitaphana.Entities;

import java.util.ArrayList;

public class JournalArticle extends Document {
    protected String journal_name, date, title;
    protected String editors;
    protected long id, document_id;

    public JournalArticle(String journal_name, String date, String title, String editors) {
        this.journal_name = journal_name;
        this.date = date;
        this.title = title;
        this.editors = editors;
    }

    public JournalArticle(String title, String authors, String keywords, int price,
                          int amount, String type, String description,
                          String journal_name, String date, String editors) {
        super(title, authors, keywords, price, amount, type, description);
        this.journal_name = journal_name;
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

    public void setJournal_name(String journal_name) {
        this.journal_name = journal_name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public void setDocument_id(long document_id) {
        this.document_id = document_id;
    }

    public String getJournal_name() {
        return journal_name;
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

    public long getDocument_id() {
        return document_id;
    }
}
