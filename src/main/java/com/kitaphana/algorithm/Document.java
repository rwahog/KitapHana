package com.kitaphana.algorithm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Document{
    protected String title, document_cover, description;
    protected ArrayList<Keyword> keywords;
    protected ArrayList<Author> authors;
    protected ArrayList<User> users;
    protected int price, amount, id;
    protected Statement statement;
    protected Connection connection;
    protected Scanner in;
    protected String type;
    Document(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
        this.in = in;
        keywords = new ArrayList<Keyword>();
        authors = new ArrayList<Author>();

        users = new ArrayList<User>();
    }
    public void read() throws SQLException {
        System.out.println("Title: ");
        setTitle(in.nextLine());
        ResultSet resultSet = statement.executeQuery("select * from documents where title = '"+title+"'");
        if(resultSet.next()){
            System.out.println("This document already exists");
        }
        else {
            System.out.println("Authors: ");
            while (in.hasNext()) {
                String name = in.nextLine();
                if (name.equals("end")) break;
                String surname = in.nextLine();
                Author author = new Author(connection, in);
                author.setName(name);
                author.setSurname(surname);
                author.setVariablesKnowingNameSurname();
                addAuthor(author);
            }
            System.out.println("Keywords: ");
            while (in.hasNext()) {
                String word = in.nextLine();
                if (word.equals("end")) break;
                Keyword keyword = new Keyword(connection, in);
                keyword.setKeyword(word);
                keyword.setVariablesKnowingKeyword();
                addKeyword(keyword);
            }
            System.out.println("Price: ");
            setPrice(Integer.parseInt(in.nextLine()));
            System.out.println("Amount: ");
            setAmount(Integer.parseInt(in.nextLine()));
            System.out.println("Description: ");
            setDescription(in.nextLine());
        }
    }
    public void save() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from documents where title = '"+title+"'");
        for (int i = 0; i < authors.size(); i++) {
            authors.get(i).save();
        }
        for (int i = 0; i < keywords.size(); i++) {
            keywords.get(i).save();
        }
        if(resultSet.next()){
            statement.executeUpdate("update documents set title = '"+title+"', authors = '"+getAuthorsAsString()+"', keywords = '"+getKeywordsAsString()+"', users = '"+getUsersAsString()+"', price = '"+price+"', amount = '"+amount+"', document_cover = '"+document_cover+"', description = '"+description+"' where id = '"+id+"'");
        }
        else {
            statement.executeUpdate("insert into documents (title, authors, keywords, users, price, amount, document_cover, description) values ('" + title + "','" + getAuthorsAsString() + "', '" + getKeywordsAsString() + "', '"+getUsersAsString()+"', '" + price + "', '" + amount + "', '"+document_cover+"', '"+description+"' )");
            resultSet = statement.executeQuery("select * from documents where title = '" + title + "'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        }
    }
    public void remove() throws SQLException {
        for(int i = 0 ; i<authors.size(); i++){
            authors.get(i).removeDocument(this);
            authors.get(i).save();
        }
        for (int i = 0; i < keywords.size(); i++) {
            keywords.get(i).removeDocument(this);
            keywords.get(i).save();
        }
        statement.executeUpdate("delete from documents where id = '"+id+"'");
    }
    public void setVariablesKnowingTitle(String title) throws SQLException {
        this.title = title;
        ResultSet resultSet = statement.executeQuery("select * from documents where title = '"+title+"'");
        if(resultSet.next()){
            setPrice(resultSet.getInt("price"));
            setAmount(resultSet.getInt("amount"));
            setId(resultSet.getInt("id"));
            setAuthorsFromString(resultSet.getString("authors"));
            setKeywordsFromString(resultSet.getString("keywords"));
            setDocument_cover(resultSet.getString("document_cover"));
            setDescription(resultSet.getString("description"));
            setType(resultSet.getString("type"));
        }
    }
    public void setVariablesKnowingId(int id) throws SQLException {
        this.id = id;
        ResultSet resultSet = statement.executeQuery("select * from documents where id = '"+id+"'");
        if(resultSet.next()){
            setPrice(resultSet.getInt("price"));
            setAmount(resultSet.getInt("amount"));
            setTitle(resultSet.getString("title"));
            setAuthorsFromString(resultSet.getString("authors"));
            setKeywordsFromString(resultSet.getString("keywords"));
            setDocument_cover(resultSet.getString("document_cover"));
            setDescription(resultSet.getString("description"));
            setType(resultSet.getString("type"));
        }
    }

    //Id
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    //Price
    public void setPrice(int price) throws SQLException {
        this.price = price;
    }
    public void setType(String type){
        this.type = type;
    }
    public int getPrice() {
        return price;
    }
    //Authors
    public void setAuthors(ArrayList<Author> authors) throws SQLException {
        for(int i =0; i<authors.size(); i++){
            addAuthor(authors.get(i));
        }
    }
    public ArrayList<Author> getAuthors() {
        return authors;
    }
    public void addAuthor(Author author) throws SQLException {
        authors.add(author);
        author.addDocument(this);
    }
    public void setAuthorsFromString(String s) throws SQLException {
        if(s!=null) {
            for (int i = 0; i < s.length(); i++) {
                int j = i;
                String cur = "";
                while (j < s.length() && s.charAt(j) != ',') {
                    cur = cur.concat(String.valueOf(s.charAt(j)));
                    j++;
                }
                i = j + 1;
                Author author = new Author(connection, in);
                author.setName(cur);
                addAuthor(author);
            }
        }
    }
    public void removeAuthor(Author author) throws SQLException {
        authors.remove(author);
        author.removeDocument(this);
        author.save();
    }
    public String getAuthorsAsString(){
        String s = "";
        for(int i = 0; i<authors.size(); i++){
            if(i<authors.size()-1) s = s.concat(authors.get(i).getName() + " " + authors.get(i).getSurname() + ", ");
            else s = s.concat(authors.get(i).getName() +" "+authors.get(i).getSurname());
        }
        return s;
    }
    //Keywords
    public void setKeywords(ArrayList<Keyword> keywords) throws SQLException {
        for(int i =0; i<keywords.size(); i++){
            addKeyword(keywords.get(i));
        }
    }
    public ArrayList<Keyword> getKeywords() {
        return keywords;
    }
    public void addKeyword(Keyword keyword) throws SQLException {
        keywords.add(keyword);
        keyword.addDocument(this);
    }
    public void setKeywordsFromString(String s) throws SQLException {
        if(s!=null) {
            for (int i = 0; i < s.length(); i++) {
                int j = i;
                String cur = "";
                while (j < s.length() && s.charAt(j) != ',') {
                    cur = cur.concat(String.valueOf(s.charAt(j)));
                    j++;
                }
                i = j + 1;
                Keyword keyword = new Keyword(connection, in);
                keyword.setKeyword(cur);
                addKeyword(keyword);
            }
        }
    }
    public void removeKeyword(Keyword keyword) throws SQLException {
        keywords.remove(keyword);
        keyword.removeDocument(this);
        keyword.save();
    }
    public String getKeywordsAsString(){
        String s = "";
        for(int i = 0; i<keywords.size(); i++){
            if(i<keywords.size()-1)  s = s.concat(keywords.get(i).getKeyword() + ", ");
            else s = s.concat(keywords.get(i).getKeyword());
        }
        return s;
    }
    //User
    public ArrayList<User> getUsers() {
        return users;
    }
    public void addUser(User user) throws SQLException {
        users.add(user);
    }
    public void setUsersFromString(String s) throws SQLException {
        if(s!=null) {
            for (int i = 0; i < s.length(); i++) {
                int j = i;
                String cur = "";
                while (j < s.length() && s.charAt(j) != ',') {
                    cur = cur.concat(String.valueOf(s.charAt(j)));
                    j++;
                }
                i = j + 1;
                User user = new User(connection, in);
                user.setVariablesKnowingCard_number(Integer.valueOf(cur));
            }
        }
    }
    public void removeUser(User user) throws SQLException {
        users.remove(user);
    }
    public String getUsersAsString(){
        String s = "";
        for(int i = 0; i<users.size(); i++){
            if(i<users.size()-1)  s = s.concat(String.valueOf(users.get(i).getId()) + ", ");
            else s = s.concat(String.valueOf(users.get(i).getId()));
        }
        return s;
    }


    //Title
    public void setTitle(String title) throws SQLException {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    //Amount
    public void setAmount(int amount) throws SQLException {
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }
    public void increaseAmount(int amount) throws SQLException {
        setAmount(this.amount + amount);
    }
    public void decreaseAmount(int amount) throws SQLException {
        if(this.amount - amount >= 0) setAmount(this.amount - amount);
    }
    //Document_cover
    public void setDocument_cover(String document_cover){
        this.document_cover = document_cover;
    }
    public String getDocument_cover(){
        return document_cover;
    }
    //Description
    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
