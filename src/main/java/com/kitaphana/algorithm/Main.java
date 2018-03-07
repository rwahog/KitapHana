package com.kitaphana.algorithm;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

//jdbc 6 - sql commands (executeUpdate, executeQuery, statement resultset)
//jdbc 7 - sql injection (prepared statemnt)
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan2 = new Scanner(System.in);
        Scanner scan = new Scanner(new File("test_case1.txt"));
        String userName = "root";
        String password = "1234";//6446
        String url = "jdbc:mysql://localhost:3306/kitaphana";
        //String url = "jdbc:mysql://localhost:3306/kitaphana";
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             Statement statement = conn.createStatement()) {
            statement.executeUpdate("drop table if exists users");
            statement.executeUpdate("drop table if exists addresses");
            statement.executeUpdate("drop table if exists documents");
            statement.executeUpdate("drop table if exists authors");
            statement.executeUpdate("drop table if exists keywords");
            statement.executeUpdate("drop table if exists books");
            statement.executeUpdate("drop table if exists av");
            statement.executeUpdate("drop table if exists ja");
            statement.executeUpdate("create table if not exists users (id mediumint not null auto_increment primary key, name varchar(256) not null, surname varchar(256) not null, card_number bigint not null, phone_number varchar(256) not null, password varchar(256) not null, email varchar(256), id_address mediumint not null, documents varchar(256), deadlines varchar(256), type varchar(256), possible_type varchar(256))");
            statement.executeUpdate("create table if not exists addresses(id_address mediumint not null auto_increment primary key, country varchar(256) not null, town varchar(256) not null, street varchar(256), house_number mediumint not null, apartment_number mediumint not null, postcode varchar(256) not null)");
            statement.executeUpdate("create table if not exists documents (id mediumint not null auto_increment primary key, title varchar(256) not null, authors varchar(256), keywords varchar(256), users varchar(256), price mediumint, amount mediumint not null, type varchar(30), document_cover varchar(256), description varchar(1024))");
            statement.executeUpdate("create table if not exists authors (id mediumint not null auto_increment primary key, name varchar(256) not null, surname varchar(256) not null, documents varchar(256))");
            statement.executeUpdate("create table if not exists keywords (id mediumint not null auto_increment primary key, keyword varchar(256) not null, documents varchar(256))");
            statement.executeUpdate("create table if not exists books (id_document mediumint not null primary key, title varchar(256) not null, publisher varchar(256) not null, year mediumint, edition_number mediumint, best_seller mediumint)");
            statement.executeUpdate("create table if not exists av (id_document mediumint not null primary key, title varchar(256) not null)");
            statement.executeUpdate("create table if not exists ja (id_document mediumint not null primary key, title varchar(256), date varchar(256), journal_name varchar(256), editors varchar(256))");
            //Librarian librarian = new Librarian(conn, scan2);
            //librarian.login();
           // User user = new User(conn, scan2);
//            Book b = new Book(conn, scan2);
//            b.setVariablesKnowingTitle("The Mythical Man-month");
//
//            user.setVariablesKnowingNameSurname("Almir", "Mullanurov");
//            user.checkOutDocument(b);
           // System.out.println(librarian.getUserDocumentsAndDeadlines(user));
//            Librarian librarian = new Librarian(conn, scan);
//            librarian.read();
//            librarian.save();
            Test2 test2 = new Test2(conn,scan, scan2);
            test2.test_case4();
        }

    }

}
