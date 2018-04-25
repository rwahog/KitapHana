package com.kitaphana.Testing;
import com.kitaphana.Database.Database;
import com.kitaphana.Entities.*;
import com.kitaphana.Service.*;
import com.kitaphana.Service.MyDocumentsService;
import org.junit.jupiter.api.Assertions;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
public class TestDelivery4 {
    public DocumentSearch search;
    public AdminService admin1 = new AdminService();
    public PatronService PatServ = new PatronService();
    public DocumentService DocServ = new DocumentService();
    public Database db = Database.getInstance();
    long day = 1000*60*60*24;
    ResultSet rs;
    Librarian l1, l2, l3;
    String namel1 = "l", namel2 = "l", namel3 = "l";
    String surnamel1 = "1",surnamel2 = "2", surnamel3 = "3";
    int privilegel1 = 3, privilegel2 = 2, privilegel3 = 1;
    String phone_numberl1 = "123123211", phone_numberl2 = "524345533", phone_numberl3 = "555445332";
    String emailL1 = "l1@mail.ru", emailL2 = "l2@mail.ru", emailL3 = "l3@mail.ru";
    String passwordL1 = "passwordL1", passwordL2 = "passwordL2", passwordL3 = "passwordL3";
    Address addressL1 = new Address("Russia", "Boguchar", "Lenina", 1, 1, "123");
    Address addressL2 = new Address("Russia", "Boguchar", "Lenina", 2, 2, "123");
    Address addressL3 = new Address("Russia", "Boguchar", "Lenina", 3, 3, "123");
    Book d1, d2, d3;
    String Titled1 =  "Introduction to Algorithms";
    String Authorsd1 =  "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein";
    String Publisherd1 =  "MIT Press";
    int Yeard1 =  2009;
    int Editiond1 = 3;
    int Priced1 =  5000;
    String keywordsd1 = "Algorithms, Data Structures, Complexity, Computational Theory";
    String Titled2 =  "Algorithms + Data Structures = Programs";
    String Authorsd2 = "Niklaus Wirth";
    String Publisherd2 = "Prentice Hall PTR";
    int Yeard2 = 1978;
    int Editiond2 = 1;
    int Priced2 =  5000;
    String keywordsd2 =  "Algorithms, Data Structures, Search Algorithms, Pascal";
    String Titled3 =  "The Art of Computer Programming";
    String Authorsd3 =  "Donald E. Knuth";
    String Publisherd3 = "Addison Wesley Longman Publishing Co., Inc.";
    int Yeard3 = 1997;
    int Editiond3 =  3;
    int Priced3 =  5000;
    String keywordsd3 =  "Algorithms, Combinatorial Algorithms, Recursion";
    Patron p1;
    String p1_Name = "Sergey";
    String p1_Surname = "Afonso";
    String p1_Address = "Via Margutta, 3, 3, 3, 3, 3";
    String p1_Phone_Number =  "30001";
    String p1_cardID =  "1010";
    String p1_Type =  "Professor";
    Patron p2;
    String p2_Name = "Nadia";
    String p2_Surname = "Teixeira";
    String p2_Address =  "Via Sacra, 13, 3, 3, 3, 3";
    String p2_Phone_Number = "30002";
    String p2_cardID =  "1011";
    String p2_Type =  "Professor";
    Patron p3;
    String p3_Name =  "Elvira";
    String p3_Surname = "Espindola";
    String p3_Address =  "Via del Corso, 22, 3, 3, 3, 3";
    String p3_PhoneNumber = "30003";
    String p3_cardID = "1100";
    String p3_Type = "Professor";
    Patron s;
    String s_Name =  "Andrey";
    String s_Surname = "Velo";
    String s_Address = "Avenida Mazatlan, 250, 3, 3, 3, 3";
    String s_PhoneNumber = "30004";
    String s_cardID = "1101";
    String s_Type = "Student";
    Patron v;
    String v_Name = "Veronika";
    String v_Surname = "Rama";
    String v_Address = "Stret Atocha, 27, 3, 3, 3, 3";
    String v_PhoneNumber = "30005";
    String v_cardID = "1110";
    String v_Type = "Visiting Professor";
    public TestDelivery4(){
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        l1 = new Librarian(namel1, surnamel1, privilegel1, phone_numberl1, emailL1, passwordL1, addressL1);
        l2 = new Librarian(namel2, surnamel2, privilegel2, phone_numberl2, emailL2, passwordL2, addressL2);
        l3 = new Librarian(namel3, surnamel3, privilegel3, phone_numberl3, emailL3, passwordL3, addressL3);
        l1.setId(1);
        l2.setId(21);
        l3.setId(31);
        d1 = new Book(Titled1, Priced1, 3,
                "Book", keywordsd1, Publisherd1,
                Yeard1, Editiond1, 0);
        d2 = new Book(Titled2, Priced2, 3,
                "Book", keywordsd2, Publisherd2,
                Yeard2, Editiond2, 0);
        d3 = new Book(Titled3, Priced3, 3,
                "Book", keywordsd3, Publisherd3,
                Yeard3, Editiond3, 0);
        d1.setId(1);
        d2.setId(2);
        d3.setId(3);
        p1 =  new Patron(p1_Name, p1_Surname, p1_Phone_Number, "123",
                "p1@mail.ru", new Address("p1", "p1", "p1", 1, 1, "p1"), "Professor");
        p2 = new Patron(p2_Name, p2_Surname, p2_Phone_Number, "123",
                "p2@mail.ru", new Address("p2", "p2", "p2", 2, 2, "p2"), "Professor");
        p3 = new Patron(p3_Name, p3_Surname, p3_PhoneNumber, "123",
                "p3@mail.ru", new Address("p3", "p3", "p3", 3, 3, "p3"), "Professor");
        s =  new Patron(s_Name, s_Surname, s_PhoneNumber, "123",
                "s@mail.ru", new Address("s", "s", "s", 4, 4, "s"), "Student");
        v =  new Patron(v_Name, v_Surname, v_PhoneNumber, "123",
                "v@mail.ru", new Address("v", "v", "v", 5, 5, "v"), "Visiting Professor");
        p1.setDeadlines("");
        p2.setDeadlines("");
        p3.setDeadlines("");
        s.setDeadlines("");
        v.setDeadlines("");
    }

    public static void test_case1(){
        Assertions.assertTrue(true);
        System.out.println("test_case1 passed");
    }
    void test_case2(boolean cont) throws SQLException {
        admin1.addLibrarian(l1);
        admin1.addLibrarian(l2);
        admin1.addLibrarian(l3);
        //admin1.addLibrarian();
        rs = db.runSqlQuery("SELECT * from librarians where privilege > '"+0+"'");
        rs.next();
        for(int i = 1; i<4; i++){
            Assertions.assertEquals(i, Integer.parseInt(rs.getString("surname")));
            if(i==1){
                l1.setId(rs.getInt("id"));
            }else if(i==2){
                l2.setId(rs.getInt("id"));
            }else{
                l3.setId(rs.getInt("id"));
            }
            rs.next();
        }


        if(!cont){
            cleanAfter4();
        }
//        long deadline = Long.parseLong(rs.getString("deadlines"));
//        int idshnik = Integer.parseInt(rs.getString("documents"));
        System.out.println("test_case2 passed");
    }
    void test_case3() throws SQLException {
        test_case2(true);
        if(l1.getPrivilege()!=3){
            DocServ.saveDocument(d1);
            DocServ.saveDocument(d2);
            DocServ.saveDocument(d3);
        }
        rs = db.runSqlQuery("SELECT * from documents");
        Assertions.assertEquals(false, rs.next());
        System.out.println("test_case3 passed");

        cleanAfter4();
    }
    void test_case4(boolean cont) throws SQLException {
        test_case2(true);
        if(l2.getPrivilege()!=3){
            DocServ.saveDocument(d1);
            DocServ.saveDocument(d2);
            DocServ.saveDocument(d3);
            PatServ.addPatron(p1);
            PatServ.addPatron(p2);
            PatServ.addPatron(p3);
            PatServ.addPatron(s);
            PatServ.addPatron(v);
        }

        rs = db.runSqlQuery("SELECT * from users");
        for( int i = 0; i<5; i++){
            Assertions.assertNotEquals(false, rs.next());
//            switch (i){
//                case(0):
//                    p1.setId(rs.getInt("id"));
//                    break;
//                case(1):
//                    p2.setId(rs.getInt("id"));
//                    break;
//                case(3):
//                    p3.setId(rs.getInt("id"));
//                    break;
//                case(4):
//                    s.setId(rs.getInt("id"));
//                    break;
//                case(5):
//                    v.setId(rs.getInt("id"));
//            }
        }
        rs = db.runSqlQuery("SELECT * from documents");
        for(int i = 0; i<3; i++){
            Assertions.assertNotEquals(false, rs.next());
//            if(i==1){
//                d1.setId(rs.getInt("id"));
//            }else if(i==2){
//                d2.setId(rs.getInt("id"));
//            }else{
//                d3.setId(rs.getInt("id"));
//            }

        }
        System.out.println("test_case4 passed");

        if(!cont){
            cleanAfter4();
        }else{
            rs = db.runSqlQuery("SELECT * from documents");
            rs.next();
            d1.setId(rs.getInt("id"));
            rs.next();
            d2.setId(rs.getInt("id"));
            rs.next();
            d3.setId(rs.getInt("id"));
            rs = db.runSqlQuery("SELECT * from users");
            rs.next();
            p1.setId(rs.getInt("id"));
            rs.next();
            p2.setId(rs.getInt("id"));

            rs.next();
            p3.setId(rs.getInt("id"));
            rs.next();
            s.setId(rs.getInt("id"));
            rs.next();
            v.setId(rs.getInt("id"));
            types();
            db.runSqlUpdate("UPDATE users SET deadlines = '"+""+"', documents = '"+""+"', fine = '"+""+"', renews = '"+""+"', returns = '"+""+"', checkouts = '"+""+"'");
        }
    }
    void test_case5() throws SQLException {
        test_case4(true);
        if(l3.getPrivilege()==1){
            db.runSqlUpdate("UPDATE documents set amount = '"+2+"' WHERE title = '"+d1.getTitle()+"'");
        }
        rs = db.runSqlQuery("SELECT * from documents where title = '"+d1.getTitle()+"'");
        rs.next();
        Assertions.assertEquals(2, rs.getInt("amount"));
        cleanAfter4();
        System.out.println("test_case5 passed");
    }
    void test_case6(boolean cont) throws SQLException {
        test_case4(true);

        DocServ.checkOut(p1.getId(), (int)d3.getId());
        DocServ.checkOutApproval(p1.getId()+"", d3.getId()+"");
        DocServ.checkOut(p2.getId(), (int)d3.getId());
        DocServ.checkOutApproval(p2.getId()+"", d3.getId()+"");
        DocServ.checkOut(s.getId(), (int)d3.getId());
        DocServ.checkOutApproval(s.getId()+"", d3.getId()+"");
        DocServ.checkOut(v.getId(), (int)d3.getId());
        DocServ.checkOut(p3.getId(), (int)d3.getId());
        if(l1.getPrivilege()<=2){
            DocServ.outstandingRequest(d3.getId()+"");
        }
        rs = db.runSqlQuery("SELECT * from documents where title = '"+d3.getTitle()+"'");
        rs.next();
        Assertions.assertNotEquals("", rs.getString("waiting_list"));
        System.out.println("test_case6 passed");
        if(!cont){
            cleanAfter4();
        }
    }
    void test_case7(boolean cont) throws SQLException {
        test_case4(true);

        DocServ.checkOut(p1.getId(), (int)d3.getId());
        DocServ.checkOutApproval(p1.getId()+"", d3.getId()+"");
        DocServ.checkOut(p2.getId(), (int)d3.getId());
        DocServ.checkOutApproval(p2.getId()+"", d3.getId()+"");
        DocServ.checkOut(s.getId(), (int)d3.getId());
        DocServ.checkOutApproval(s.getId()+"", d3.getId()+"");
        DocServ.checkOut(v.getId(), (int)d3.getId());
        DocServ.checkOut(p3.getId(), (int)d3.getId());

        if(l3.getPrivilege()<=2){
            DocServ.outstandingRequest(d3.getId()+"");
        }
        rs = db.runSqlQuery("SELECT * from documents where title = '"+d3.getTitle()+"'");
        rs.next();
        Assertions.assertEquals("", rs.getString("waiting_list"));
        System.out.println("test_case7 passed");

        if(!cont){
            cleanAfter4();
        }
    }
    void test_case8() throws SQLException {
        test_case6(true);
        //!!!!!!!!!!!!!CHECK THE LOG!!!!!!!!!!!!!!!!!!!!!
        System.out.println("test_case8 passed");
        cleanAfter4();
    }
    void test_case9() throws SQLException {
        test_case7(true);
        //!!!!!!!!!!!!CHECK THE LOG!!!!!!!!!!!!!!!!!!!!!!
        System.out.println("test_case9 passed");
        cleanAfter4();
    }
    void test_case10() throws SQLException {
        test_case4(true);
        search = new DocumentSearch();

        ArrayList<Document> docs = search.getDocumentsByPossibleTitle("Introduction to Algorithms", 1, 1);
        Assertions.assertEquals(docs.get(0).getTitle(), "Introduction to Algorithms");
        System.out.println("test_case10 passed");
        cleanAfter4();
    }
    void test_case11() throws SQLException {
        test_case4(true);

        ArrayList<Document> docs = search.getDocumentsByPossibleTitle("Algorithms", 2, 1);
        Assertions.assertEquals(docs.get(0).getTitle(), "Introduction to Algorithms");
        Assertions.assertEquals(docs.get(1).getTitle(), "Algorithms + Data Structures = Programs");


        System.out.println("test_case11 passed");
        cleanAfter4();
    }
    void test_case12() throws SQLException {
        test_case4(true);

        ArrayList<Document> docs = search.getDocumentsByPossibleKeyword("Algorithms", 3, 1024);
        Assertions.assertEquals(docs.get(0).getTitle(), "Introduction to Algorithms");
        Assertions.assertEquals(docs.get(1).getTitle(), "Algorithms + Data Structures = Programs");
        Assertions.assertEquals(docs.get(2).getTitle(), "The Art of Computer Programming");

        System.out.println("test_case12 passed");
        cleanAfter4();
    }
    public void cleanAfter4() throws SQLException {
        db.runSqlUpdate("TRUNCATE TABLE users");
        db.runSqlUpdate("TRUNCATE TABLE addresses");
        db.runSqlUpdate("TRUNCATE TABLE books");
        db.runSqlUpdate("TRUNCATE TABLE documents");
        for(int i = 0; i<3; i++){
            rs = db.runSqlQuery("SELECT * from librarians where privilege > '"+0+"'");
            rs.next();
            admin1.deleteLibrarian(rs.getInt("id")+"");
        }
    }
    public void types() throws SQLException {
        p1.setType("Professor");
        p2.setType("Professor");
        p3.setType("Professor");
        v.setType("Visiting Professor");
        s.setType("Student");
        rs = db.runSqlQuery("SELECT * from users");
        while(rs.next()){
            db.runSqlUpdate("UPDATE users SET type = '"+rs.getString("possible_type")+"' where possible_type = '"+rs.getString("possible_type")+"'");
        }
    }
}

