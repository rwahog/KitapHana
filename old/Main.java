import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

//jdbc 6 - sql commands (executeUpdate, executeQuery, statement resultset)
//jdbc 7 - sql injection (prepared statemnt)
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scan2 = new Scanner(System.in);
        String userName = "remote";
        String password = "password";//6446
        String url = "jdbc:mysql://188.130.155.99:3306/kh";
        //String url = "jdbc:mysql://localhost:3306/kitaphana";
        Class.forName("com.mysql.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, userName, password);
             Statement statement = conn.createStatement()) {
            //statement.executeUpdate("alter table documents add description varchar(256)");
//            statement.executeUpdate("drop table if exists users");
//            statement.executeUpdate("drop table if exists addresses");
//            statement.executeUpdate("drop table if exists documents");
//            statement.executeUpdate("drop table if exists authors");
//            statement.executeUpdate("drop table if exists keywords");
//            statement.executeUpdate("drop table if exists books");
//            statement.executeUpdate("drop table if exists av");
//            statement.executeUpdate("drop table if exists ja");
            statement.executeUpdate("create table if not exists users (id mediumint not null auto_increment primary key, name varchar(256) not null, surname varchar(256) not null, card_number bigint not null, phone_number varchar(256) not null, password varchar(256) not null, email varchar(256), id_address mediumint not null, documents varchar(256), deadlines varchar(256), type varchar(256), possible_type varchar(256))");
            statement.executeUpdate("create table if not exists addresses(id_address mediumint not null auto_increment primary key, country varchar(256) not null, town varchar(256) not null, street varchar(256), house_number mediumint not null, apartment_number mediumint not null, postcode varchar(256) not null)");
            statement.executeUpdate("create table if not exists documents (id mediumint not null auto_increment primary key, title varchar(256) not null, authors varchar(256), keywords varchar(256), users varchar(256), price mediumint, amount mediumint not null, type varchar(30), document_cover varchar(256), description varchar(256))");
            statement.executeUpdate("create table if not exists authors (id mediumint not null auto_increment primary key, name varchar(256) not null, surname varchar(256) not null, documents varchar(256))");
            statement.executeUpdate("create table if not exists keywords (id mediumint not null auto_increment primary key, keyword varchar(256) not null, documents varchar(256))");
            statement.executeUpdate("create table if not exists books (id_document mediumint not null primary key, title varchar(256) not null, publisher varchar(256) not null, year mediumint, edition_number mediumint, best_seller mediumint)");
            statement.executeUpdate("create table if not exists av (id_document mediumint not null primary key, title varchar(256) not null)");
            statement.executeUpdate("create table if not exists ja (id_document mediumint not null primary key, title varchar(256), date varchar(256), journal_name varchar(256), editors varchar(256))");
//            Librarian librarian = new Librarian(conn, scan2);
//            librarian.read();
//            librarian.save();
        }

    }

}
