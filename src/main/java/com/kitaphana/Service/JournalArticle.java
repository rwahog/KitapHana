package com.kitaphana.Service;

import java.util.ArrayList;

public class JournalArticle extends Document {
    protected String journal_name, date, title;
    protected String editors;
    protected int id;

    public void setId(int id) {
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

    public int getId() {
        return id;
    }
}
