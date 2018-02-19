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
        Scanner scan = new Scanner(new File("input.txt"));
        Scanner scan2 = new Scanner(System.in);
        FileWriter writer = new FileWriter(new File("output.txt"));
        String userName = "root";
        String password = "1234";//6446
        String url = "jdbc:mysql://localhost:3306/kitaphana?autoReconnect=true&useSSL=false";
        Class.forName("com.mysql.jdbc.Driver");
        try(Connection conn = DriverManager.getConnection(url, userName, password);
        Statement statement = conn.createStatement()){
            statement.executeUpdate("drop table if exists users");
            statement.executeUpdate("drop table if exists addresses");
            statement.executeUpdate("drop table if exists documents");
            statement.executeUpdate("drop table if exists authors");
            statement.executeUpdate("drop table if exists keywords");
            statement.executeUpdate("drop table if exists books");
            statement.executeUpdate("drop table if exists av");
            statement.executeUpdate("drop table if exists ja");
            statement.executeUpdate("create table if not exists users (id mediumint not null auto_increment primary key, name varchar(1024) not null, surname varchar(1024) not null, card_number bigint not null, phone_number varchar(1024) not null, password varchar(1024) not null, id_address mediumint not null, documents varchar(1024), deadlines varchar(1024), type varchar(1024), possible_type varchar(1024))");
            statement.executeUpdate("create table if not exists addresses(id_address mediumint not null auto_increment primary key, country varchar(1024) not null, town varchar(1024) not null, street varchar(1024), house_number mediumint not null, apartment_number mediumint not null, postcode varchar(1024) not null)");
            statement.executeUpdate("create table if not exists documents (id mediumint not null auto_increment primary key, title varchar(1024) not null, authors varchar(1024), keywords varchar(1024), price mediumint, amount mediumint not null, type varchar(30))");
            statement.executeUpdate("create table if not exists authors (id mediumint not null auto_increment primary key, name varchar(1024) not null, surname varchar(1024) not null, documents varchar(1024))");
            statement.executeUpdate("create table if not exists keywords (id mediumint not null auto_increment primary key, keyword varchar(1024) not null, documents varchar(1024))");
            statement.executeUpdate("create table if not exists books (id_document mediumint not null primary key, title varchar(1024) not null, publisher varchar(1024) not null, year mediumint, edition_number mediumint, best_seller mediumint)");
            statement.executeUpdate("create table if not exists av (id_document mediumint not null primary key, title varchar(1024) not null)");
            statement.executeUpdate("create table if not exists ja (id_document mediumint not null primary key, title varchar(1024), date varchar(1024), journal_name varchar(1024), editors varchar(1024))");
            /*FacultyMember facultyMember = new FacultyMember(conn, scan2);
            fcultyMember.read();
            facultyMember.save();

            Test test = new Test(conn, scan2);
            test.test_case5();*/
        }

    }

}
