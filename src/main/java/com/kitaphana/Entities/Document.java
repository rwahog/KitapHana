package com.kitaphana.Entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Document {
    protected String title;
    protected String keywords;
    protected String authors;
    protected String cover;
    protected String type;
    protected String users;
    protected String description, awaiters;
    protected long id, deadline, fine;
    protected int price, amount, requests;

    public Document(String title, String authors, String keywords, int price,
                    int amount, String type, String description) {
        this.title = title;
        this.authors = authors;
        this.keywords = keywords;
        this.price = price;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public Document() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
    
    public void setType(String type) {
        this.type = type;
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
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public void setAwaiters(String awaiters) {
        this.awaiters = awaiters;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public void setFine(long fine) {
        this.fine = fine;
    }

    public long getId() {
        return id;
    }

    public String getAuthors() {
        return authors;
    }
    
    public String getType() {
        return type;
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
    
    public String getDescription() {
        return description;
    }

    public long getDeadline() {
        return deadline;
    }

    public String getUsers() {
        return users;
    }

    public long getFine() {
        return fine;
    }

    public int getRequests() {
        return requests;
    }

    public String getAwaiters() {
        return awaiters;
    }

    public ArrayList<String> getAuthorsAsArray() {
        ArrayList<String> authors = new ArrayList<>(Arrays.asList(getAuthors().split(",")));
        return authors;
    }

    public ArrayList<String> getKeywordsAsArray() {
        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(getKeywords().split(",")));
        return keywords;
    }

    public long getDeadlineOfDocument(long deadline){
        long day = 24*60*60*1000;
        long left;
        Date date = new Date();
        left = deadline - date.getTime();
        if (left > 0) {
            return (long) Math.ceil((double)left / (double)day);
        } else {
            return getFine(left);
        }
    }

    public long getFine(long left) {
        long day = 24*60*60*1000;
        long docPrice = getPrice();
        long fine = (long) Math.ceil((double)left / (double)day)*(-1)*100;
        if (fine > docPrice) {
            setFine(docPrice);
            return docPrice;
        } else {
            setFine(fine);
            return fine;
        }
    }
}
