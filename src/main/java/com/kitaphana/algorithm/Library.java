package com.kitaphana.algorithm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Library {
    protected Connection connection;
    protected Statement statement;
    protected Scanner in;
    Library(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.in = in;
        this.statement = connection.createStatement();
    }
    //Document
    public Document getDocumentByTitle(String title) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from documents where title = '"+title+"'");
        Document document = new Document(connection, in);
        if(resultSet.next()){
            document.setVariablesKnowingTitle(resultSet.getString("title"));
            return document;
        }
        else{
            return null;
        }
    }
    public String searchDocumentByPossibleTitle(String title) throws SQLException { // Ищет документ по частично
        ResultSet resultSet = statement.executeQuery("select * from documents");
        String answer = "";
        while(resultSet.next()){
            String s = resultSet.getString("title");
            if(s.contains(title)){
                if(!answer.equals("")) answer = answer.concat(", " + s);
                else answer = answer.concat(s);
            }
        }
        return answer;
    }
    public String searchDocumentByAuthor(Author author) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from authors where name = '"+author.getName()+"' and surname = '"+author.getSurname()+"'");
        if(resultSet.next()){
            return resultSet.getString("documents");
        }
        else{
            return "No documents from this author";
        }
    }
    public String searchDocumentByKeyword(Keyword keyword) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from keywords where keyword = '"+keyword.getKeyword()+"'");
        if(resultSet.next()){
            return resultSet.getString("documents");
        }
        else{
            return "No documents with this keyword";
        }
    }
    
    public int getNumberOfDocuments() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT documents.amount FROM documents");
        int totalAmount = 0;
        while(resultSet.next()){
            totalAmount += resultSet.getInt("amount");
        }
        return totalAmount;
    }
    public int getNumberOfAVMaterials() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM documents where type = 'av'");
        int totalAmount = 0;

        while(resultSet.next()){
            totalAmount ++;
        }
        return totalAmount;
    }
    public int getNumberOfBooks() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM documents where type = 'book'");
        int totalAmount = 0;
        while(resultSet.next()){
            totalAmount ++;
        }
        return totalAmount;
    }
    //User
    public User searchUserByNameSurname(String name, String surname) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from users where name = '"+name+"' AND surname = '"+surname+"'");
        if(resultSet.next()){
            User user = new User(connection, in);
            user.setVariablesKnowingNameSurname(name, surname);
            return user;
        }
        else{
            return null;
        }
    }
    public User searchUserByCard_number(long card_number) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from users where card_number = '"+card_number+"'");
        if(resultSet.next()){
            User user = new User(connection, in);
            user.setVariablesKnowingCard_number(card_number);
            return user;
        }
        else{
            return null;
        }
    }
    
    public int getNumberOfUsers() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT users.id FROM users");
        int total = 0;
        while (resultSet.next()) {
            total++;
        }
        return total;
    }
    public int getNumberOfPatrons() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT id FROM users where type !='librarian'");
        int total = 0;
        while (resultSet.next()) {
            total++;
        }
        return total;
    }
}
