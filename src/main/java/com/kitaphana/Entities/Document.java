package com.kitaphana.Entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Document {
    private String title;
    private String keywordsId;
    private String authorsId;
    private String cover;
    private String type;
    private String users;
    private String description, awaiters;
    protected long id, deadline, fine;
    private int price, amount, requests;
    private ArrayList<Keyword> keywords;
    private ArrayList<Author> authors;

    public Document(String title, String authorsId, String keywordsId, int price,
                    int amount, String type, String description) {
        this.title = title;
        this.authorsId = authorsId;
        this.keywordsId = keywordsId;
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

    public void setAuthorsId(String authorsId) {
        this.authorsId = authorsId;
    }

    public void setKeywordsId(String keywordsId) {
        this.keywordsId = keywordsId;
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

    public void setKeywords(ArrayList<Keyword> keywords) {
        this.keywords = keywords;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public long getId() {
        return id;
    }

    public String getAuthorsId() {
        return authorsId;
    }
    
    public String getType() {
        return type;
    }

    public String getKeywordsId() {
        return keywordsId;
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

    public ArrayList<Keyword> getKeywords() {
        return keywords;
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public String getKeywordsAsString() {
        String keys = "";
        if (keywords.size() == 1) {
            keys = keywords.get(0).getKeyword();
        } else if (keywords.size() == 0) {
            keys = "";
        }
        else {
            for (int i = 0; i < keywords.size() - 1; i++) {
                keys = keys.concat(keywords.get(i).getKeyword() + ", ");
            }
            keys = keys.concat(keywords.get(keywords.size() - 1).getKeyword());
        }
        return keys;
    }

    public String getAuthorsAsString() {
        String authorsStr = "";
        if (authors.size() == 1) {
            authorsStr = authors.get(0).getName() + " " + authors.get(0).getSurname();
        } else if (authors.size() == 0) {
            authorsStr = "";
        } else {
            for (int i = 0; i < authors.size() - 1; i++) {
                authorsStr = authorsStr.concat(authors.get(i).getName() + " " + authors.get(i).getSurname() + ", ");
            }
            authorsStr = authorsStr.concat(authors.get(authors.size() - 1).getName() + " " + authors.get(authors.size() - 1).getSurname());
        }
        return authorsStr;
    }



    public long getDeadlineOfDocument(long deadline){
        long day = 24*60*60*1000;
        long left;
        Date date = new Date();
        left = deadline - date.getTime();
        if (left > 0) {
            return 0;
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