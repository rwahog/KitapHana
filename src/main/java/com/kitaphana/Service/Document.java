package com.kitaphana.Service;

import java.util.ArrayList;

public class Document {
    protected String title;
    protected String keywords;
    protected String authors;
    protected String cover;
    protected int price, amount, id;

    public void setId(int id) {
        this.id = id;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getAuthors() {
        return authors;
    }

    public String getKeywords() {
        return keywords;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getCover() {
        return cover;
    }
}
