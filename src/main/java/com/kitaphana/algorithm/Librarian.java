package com.kitaphana.algorithm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Librarian extends User {
    Library library;
    Librarian(Connection connection, Scanner in) throws SQLException {

        super(connection, in);
        library = new Library(connection, in);
    }

    @Override
    public void save() throws SQLException {
        super.save();
        statement.executeUpdate("update users set type = 'librarian' where id = '"+id+"'");
    }

    @Override
    public void login() throws SQLException {
        super.login();
    }

    //User
    public String getUserDocumentsAndDeadlines(User user){
        return user.getDocumentsAndDeadlines();
    }
    public User removeUser(User user) throws SQLException {
        user.remove();
        user = null;
        return null;
    }
    public void addUser(User user) throws SQLException {
        user.save();
    }

    public void addUser() throws SQLException {
        System.out.println("Type of user: ");
        String s = in.nextLine();
        s.toLowerCase();
        if(s.equals("student")){
            Student student = new Student(connection, in);
            student.read();
            student.save();
        }
        else if(s.equals("faculty member")){
            FacultyMember facultyMember = new FacultyMember(connection, in);
            facultyMember.read();
            facultyMember.save();
        }
        else if(s.equals("librarian")){
            Librarian librarian = new Librarian(connection, in);
            librarian.read();
            librarian.save();
        }
    }
    public void modifyUser(User user) throws SQLException {
        System.out.println("What do you want to modify?");
        String field = in.nextLine();
        field.toLowerCase();
        if(field.equals("name")){
            String name = in.nextLine();
            user.setName(name);
        }
        else if (field.equals("surname")){
            String surname = in.nextLine();
            user.setSurname(surname);
        }
        else if (field.equals("phone number")){
            String phone_number = in.nextLine();
            user.setPhone_number(phone_number);
        }
        else if(field.equals("email")){
            String email = in.nextLine();
            user.setEmail(email);
        }
        else if(field.equals("type")){
            String type = in.nextLine();
            user.setType(type);
        }
        else if(field.equals("delete document")){
            Document document = new Document(connection, in);
            String title = in.nextLine();
            document = library.getDocumentByTitle(title);
            user.returnDocument(document);
        }
        user.save();
    }
    public User searchUserByNameSurname(String name, String surname) throws SQLException {
        return library.searchUserByNameSurname(name, surname);
    }
    public User searchUserByCard_Number(long card_number) throws SQLException {
        return library.searchUserByCard_number(card_number);
    }
    public int getNumberOfUsers() throws SQLException {
        return library.getNumberOfUsers();
    }
    public int getNumberOfPatrons() throws SQLException {
        return library.getNumberOfPatrons();
    }
    public void checkRequestsForType() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from users where possible_type != ''");
        while(resultSet.next()){
            //reshaet chto delat'
        }
    }
    //Document
    public void addDocument() throws SQLException {
        System.out.println("Type: ");
        String s = in.nextLine();
        s.toLowerCase();
        if(s.equals("book")){
            Book book = new Book(connection, in);
            book.read();
            book.save();
        }
        else if(s.equals("av")){
            AVMaterial avMaterial = new AVMaterial(connection, in);
            avMaterial.read();
            avMaterial.save();
        }
        else if(s.equals("ja")){
            JournalArticle journalArticle = new JournalArticle(connection, in);
            journalArticle.read();
            journalArticle.save();
        }
    }
    public void addDocument(Document document) throws SQLException {
        document.save();
    }
    public void addBook(String title, ArrayList<Author> authors, ArrayList<Keyword> keywords, int price, int amount, String document_cover, String description, String publisher, int year, int edition_number, boolean best_seller) throws SQLException {
        Book book = new Book(connection, in);
        book.setTitle(title);
        book.setAuthors(authors);
        book.setKeywords(keywords);
        book.setPrice(price);
        book.setAmount(amount);
        book.setDocument_cover(document_cover);
        book.setDescription(description);
        book.setPublisher(publisher);
        book.setYear(year);
        book.setEdition_number(edition_number);
        book.setBest_seller(best_seller);
        book.save();
    }

    public void  removeDocument(Document document) throws SQLException {
        document.remove();
        document = null;
    }
    public void decreaseAmountOfDocument(Document document, int amount) throws SQLException {
        document.decreaseAmount(amount);
        document.save();
    }
    public void increaseAmountOfDocument(Document document, int amount) throws SQLException {
        document.increaseAmount(amount);
        document.save();
    }
    public void modifyDocument(Document document) throws SQLException {
        System.out.println("What do you want to modify?");
        String field = in.nextLine();
        field.toLowerCase();
        if(field.equals("title")){
            String title = in.nextLine();
            document.setTitle(title);
            document.save();
        }
        else if (field.equals("remove author")){
            String name = in.nextLine();
            String surname = in.nextLine();
            Author author = new Author(connection, in);
            author.setName(name);
            author.setSurname(surname);
            author.setVariablesKnowingNameSurname();
            document.removeAuthor(author);
        }
        else if (field.equals("add author")){
            String name = in.nextLine();
            String surname = in.nextLine();
            Author author = new Author(connection, in);
            author.setName(name);
            author.setSurname(surname);
            author.setVariablesKnowingNameSurname();
            document.addAuthor(author);
        }
        else if (field.equals("remove keyword")){
            String word = in.nextLine();
            Keyword keyword = new Keyword(connection, in);
            keyword.setKeyword(word);
            keyword.setVariablesKnowingKeyword();
            document.removeKeyword(keyword);
        }
        else if (field.equals("add keyword")){
            String word = in.nextLine();
            Keyword keyword = new Keyword(connection, in);
            keyword.setKeyword(word);
            keyword.setVariablesKnowingKeyword();
            document.addKeyword(keyword);
        }
        else if(field.equals("price")){
            int price = Integer.parseInt(in.nextLine());
            document.setPrice(price);
        }
        else if(field.equals("amount")){
            int amount = Integer.parseInt(in.nextLine());
            document.setAmount(amount);
        }
        document.save();
    }
    public int getNumberOfDocuments() throws SQLException {
        return library.getNumberOfDocuments();
    }
    public int getNumberOfAVMaterials() throws SQLException {
        return library.getNumberOfAVMaterials();
    }
    public int getNumberOfBooks() throws SQLException {
        return library.getNumberOfBooks();
    }
}
