package com.kitaphana.Entities;

import java.util.ArrayList;
import java.util.Date;

public class Document {
  private String title, type, cover, description, authorsId, keywordsId,
          usersId, awaitersId;
  private long id, deadline;
  private int price, amount, requests, available, fine, renewCount;
  private ArrayList<Keyword> keywords;
  private ArrayList<Author> authors;
  private ArrayList<User> users, awaiters;

  public Document(String title, int price, int amount, String type,
                  String description) {
    this.title = title;
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

  public long getId() {
    return id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setAuthorsId(String authorsId) {
    this.authorsId = authorsId;
  }

  public String getAuthorsId() {
    return authorsId;
  }

  public void setAuthors(ArrayList<Author> authors) {
    this.authors = authors;
  }

  public ArrayList<Author> getAuthors() {
    return authors;
  }

  public void setKeywordsId(String keywordsId) {
    this.keywordsId = keywordsId;
  }

  public String getKeywordsId() {
    return keywordsId;
  }

  public void setKeywords(ArrayList<Keyword> keywords) {
    this.keywords = keywords;
  }

  public ArrayList<Keyword> getKeywords() {
    return keywords;
  }

  public void setUsersId(String usersId) {
    this.usersId = usersId;
  }

  public String getUsersId() {
    return usersId;
  }

  public void setUsers(ArrayList<User> users) {
    this.users = users;
  }

  public ArrayList<User> getUsers() {
    return users;
  }

  public void setAwaitersId(String awaitersId) {
    this.awaitersId = awaitersId;
  }

  public String getAwaitersId() {
    return awaitersId;
  }

  public void setAwaiters(ArrayList<User> awaiters) {
    this.awaiters = awaiters;
  }

  public ArrayList<User> getAwaiters() {
    return awaiters;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public int getPrice() {
    return price;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public int getAmount() {
    return amount;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getCover() {
    return cover;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setDeadline(long deadline) {
    this.deadline = deadline;
  }

  public long getDeadline() {
    return deadline;
  }

  public void setRenewCount(int renewCount) {
    this.renewCount = renewCount;
  }

  public int getRenewCount() {
    return renewCount;
  }

  public void setRequests(int requests) {
    this.requests = requests;
  }

  public int getRequests() {
    return requests;
  }

  public void setFine(int fine) {
    this.fine = fine;
  }

  public long getFine() {
    return fine;
  }

  public void setAvailable(int available) {
    this.available = available;
  }

  public int getAvailable() {
    return available;
  }

  public String getKeywordsAsString() {
    String keys = "";
    if (keywords.size() == 1) {
      keys = keywords.get(0).getKeyword();
    } else if (keywords.size() == 0) {
      keys = "";
    } else {
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

  public long getDeadlineOfDocument(long deadline) {
    long day = 24 * 60 * 60 * 1000;
    long left;
    Date date = new Date();
    left = deadline - date.getTime();
    return (long) Math.ceil((double) left / (double) day);
  }
}