import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
//jdbc 6 - sql commands (executeUpdate, executeQuery, statement resultset)
//jdbc 7 - sql injection (prepared statemnt)
public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Scanner scan = new Scanner(new File("input.txt"));
        Scanner scan2 = new Scanner(System.in);
        FileWriter writer = new FileWriter(new File("output.txt"));
        String userName = "root";
        String password = "mullanurov";
        String url = "jdbc:mysql://localhost:3306/test";
        Class.forName("com.mysql.jdbc.Driver");
        try(Connection conn = DriverManager.getConnection(url, userName, password);
        Statement statement = conn.createStatement()){
            statement.executeUpdate("drop table if exists users");
            statement.executeUpdate("drop table if exists addresses");
            statement.executeUpdate("drop table if exists documents");
            statement.executeUpdate("drop table if exists authors");
            statement.executeUpdate("drop table if exists keywords");
            statement.executeUpdate("create table if not exists users (id mediumint not null auto_increment primary key, name varchar(255) not null, surname varchar(255) not null, card_number bigint not null, phone_number varchar(255) not null, id_address mediumint not null, documents varchar(255))");
            statement.executeUpdate("create table if not exists addresses(id mediumint not null auto_increment primary key, country varchar(1024) not null, town varchar(1024) not null, street varchar(1024), house_number mediumint not null, apartment_number mediumint not null, postcode varchar(1024) not null)");
            statement.executeUpdate("create table if not exists documents (id mediumint not null auto_increment primary key, title varchar(1024) not null, authors varchar(1024), keywords varchar(1024), price mediumint, amount mediumint not null, type varchar(30))");
            statement.executeUpdate("create table if not exists authors (id mediumint not null auto_increment primary key, name varchar(1024) not null, documents varchar(1024))");
            statement.executeUpdate("create table if not exists keywords (id mediumint not null auto_increment primary key, keyword varchar(1024) not null, documents varchar(1024))");
            Document d = new Document(conn, scan2);
            d.readDocument();
            d.save();
        }

    }
}
