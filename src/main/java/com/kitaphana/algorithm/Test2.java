package com.kitaphana.algorithm;

import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Test2 {
    protected Statement statement;
    protected Connection connection;
    protected Scanner in, intxt;
    protected long hour = 1000*60*60;
    Book b1 , b2, b3;
    Student p2, p3;
    FacultyMember p1;


    AVMaterial av1, av2;
    Librarian librarian;
    //protected Librarian librarian;
    Test2(Connection connection, Scanner intxt, Scanner in) throws SQLException {
        this.in = in;
        this.intxt = intxt;
        this.connection = connection;
        this.statement = connection.createStatement();
        librarian = new Librarian(connection, intxt);
        librarian.read();
        librarian.save();
    }

    @org.junit.jupiter.api.Test
    public void test_case1() throws Exception{
        Librarian librarian = new Librarian(connection, intxt);
        librarian.login();
        librarian.addDocument();//b1 3 copies
        b1= new Book(connection, in);
        b1.setVariablesKnowingTitle("Introduction to Algorithms");
        b1.best_seller = true;
//      Title: Introduction to Algorithms
//      Authors: Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein
//      Publisher: MIT Press
//      Year: 2009
//      Edition: Third edition
//      Note: NA
        librarian.addDocument();//b2 2 copies
        b2= new Book(connection, in);
        b2.setVariablesKnowingTitle("Design Patterns: Elements of Reusable Object-Oriented Software");
        b2.best_seller = true;
//      Title: Design Patterns: Elements of Reusable Object-Oriented Software
//      Authors: Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm
//      Publisher: Addison-Wesley Professional
//      Year: 2003
//      Edition: First edition
//      Note: best seller
        librarian.addDocument();//b3 1 copy
        b3 = new Book(connection, in);
        b3.setVariablesKnowingTitle("The Mythical Man-month");
        b3.best_seller = true;
//        Title: The Mythical Man-month
//      Authors: Brooks,Jr., Frederick P.
//      Publisher: Addison-Wesley Longman Publishing Co., Inc.
//      Year: 1995
//      Edition: Second edition
//      Note: reference
        librarian.addDocument();//v1
        av1 = new AVMaterial(connection, in);
        av1.setVariablesKnowingTitle("Null References: The Billion Dollar Mistake");
//        Title: Null References: The Billion Dollar Mistake
//          Authors: Tony Hoare
        librarian.addDocument();//v2
        av2 = new AVMaterial(connection, in);
        av2.setVariablesKnowingTitle("Information Entropy");
//        Title: Information Entropy
//        Authors: Claude Shannon
        librarian.addUser();//p1
        p1 = new FacultyMember(connection, in);
        p1.setVariablesKnowingNameSurname("Sergey", "Afonso");
//        Name: Sergey Afonso
//      Address: Via Margutta, 3
//      Phone Number: 30001
//      Lib. card ID: 1010
//      Type: Faculty
        librarian.addUser();//p2
        p2 = new Student(connection, in);
        p2.setVariablesKnowingNameSurname("Nadia", "Teixeira");
//      Name: Nadia Teixeira
//      Address: Via Sacra, 13
//      Phone Number: 30002
//      Lib. card ID: 1011
//      Type: Student
        librarian.addUser();//p3
        p3 = new Student(connection, in);
        p3.setVariablesKnowingNameSurname("Elvira", "Espindola");

//        Name: Elvira Espindola
//      Address: Via del Corso, 22
//      Phone Number: 30003
//      Lib. card ID: 1100
//      Type: Student

        Assertions.assertEquals(8, librarian.getNumberOfDocuments());
        Assertions.assertEquals(4, librarian.getNumberOfUsers());
        System.out.println("test case 1 passed");
    }
    public void test_case2() throws Exception{
        test_case1();
        librarian.decreaseAmountOfDocument(b1, 2);
        librarian.decreaseAmountOfDocument(b3, 1);
        p2 = (Student) librarian.removeUser(p2);
        Assertions.assertEquals(5, librarian.getNumberOfDocuments());
        Assertions.assertEquals(3, librarian.getNumberOfUsers());
        System.out.println("test case 2 passed");

    }
    public void test_case3() throws Exception {
        test_case1();
        Assertions.assertTrue(p1.name.equals("Sergey") && p1.surname.equals("Afonso") &&
                             p1.getAddress().street.equals("Via Margutta") && p1.getAddress().house_number==3 && p1.phone_number.equals("30001"));
//        Name: Sergey Afonso
//        Address: Via Margutta, 3
//        Phone Number: 30001
//        Lib. card ID: 1010
//        Type: Faculty
        Assertions.assertTrue(p3.name.equals("Elvira") && p3.surname.equals("Espindola") &&
                p3.getAddress().street.equals("Via del Corso") && p3.getAddress().house_number==22 && p3.phone_number.equals("30003"));
//        Name: Elvira Espindola
//        Address: Via del Corso, 22
//        Phone Number: 30003
//        Lib. card ID: 1100
//        Type: Student
//                (document checked-out, due date):
        System.out.println("test case 3 passed");
    }
    public void test_case4() throws Exception {
        test_case2();
        User p3 = librarian.searchUserByNameSurname("Elvira", "Espindola");
        User p2 = librarian.searchUserByNameSurname("Nadia","Teixeira");

        Assertions.assertTrue(p2==null && p3.name.equals("Elvira") && p3.surname.equals("Espindola") &&
                p3.getAddress().street.equals("Via del Corso") && p3.getAddress().house_number==22 && p3.phone_number.equals("30003"));
        System.out.println("test case 4 passed");
    }
    public void test_case5() throws Exception {
        test_case2();
        boolean couldCheck = true;
        try{
            p2.checkOutDocument(b2);
        }catch (Exception e){
            couldCheck = false;
        }
        Assertions.assertTrue(!couldCheck);
        System.out.println("test case 5 passed");
    }
    public void test_case6() throws Exception {
        test_case2();
        p1.checkOutDocument(b1);

        p3.checkOutDocument(b1);

        p1.checkOutDocument(b2);
        User p1 = librarian.searchUserByNameSurname("Sergey",  "Afonso");
        User p3 = librarian.searchUserByNameSurname("Elvira",  "Espindola");
        Assertions.assertTrue(p1.name.equals("Sergey") && p1.surname.equals("Afonso") &&
                p1.getAddress().street.equals("Via Margutta") && p1.getAddress().house_number==3 && p1.phone_number.equals("30001"));
        Assertions.assertTrue(p3.name.equals("Elvira") && p3.surname.equals("Espindola") &&
                p3.getAddress().street.equals("Via del Corso") && p3.getAddress().house_number==22 && p3.phone_number.equals("30003"));
        String[] deadlines1 = p1.getDeadlinesAsString().split(", ");
        long deadlineP11 = Long.parseLong(deadlines1[0]);
        long deadTime1 = deadline(p1, deadlineP11, b1);
        Assertions.assertTrue(deadTime1==0);
        long deadlineP12 = Long.parseLong(deadlines1[1]);
        long deadTime2 = deadline(p1, deadlineP12, b3);
        Assertions.assertTrue(deadTime2==0);
        System.out.println("test case 6 passed");

    }
    public void test_case7() throws Exception{
        test_case1();
        p1.checkOutDocument(b1);
        p1.checkOutDocument(b2);
        p1.checkOutDocument(b3);
        p2.checkOutDocument(b1);
        p2.checkOutDocument(b2);
        p2.checkOutDocument(av2);
        User p1 = librarian.searchUserByNameSurname("Sergey",  "Afonso");
        User p2 = librarian.searchUserByNameSurname("Nadia",  "Teixeira");
        Assertions.assertTrue(p1.name.equals("Sergey") && p1.surname.equals("Afonso") &&
                p1.getAddress().street.equals("Via Margutta") && p1.getAddress().house_number==3 && p1.phone_number.equals("30001"));
        Assertions.assertTrue(p2.name.equals("Nadia") && p2.surname.equals("Teixeira") &&
                p2.getAddress().street.equals("Via Sacra") && p2.getAddress().house_number==13 && p2.phone_number.equals("30002"));
        String[] deadlines1 = p1.getDeadlinesAsString().split(", ");
        long deadlineP11 = Long.parseLong(deadlines1[0]);
        long deadlineP12 = Long.parseLong(deadlines1[1]);
        long deadlineP13 = Long.parseLong(deadlines1[2]);
        long deadTime11 = deadline(p1, deadlineP11, b1);
        long deadTime12 = deadline(p1, deadlineP12, b2);
        long deadTime13 = deadline(p1, deadlineP13, b3);
        Assertions.assertTrue(deadTime11==0);
        Assertions.assertTrue(deadTime12==0);
        Assertions.assertTrue(deadTime13==0);
//        Name: Nadia Teixeira
//        Address: Via Sacra, 13
//        Phone Number: 30002
//        Lib. card ID: 1011
//        Type: Student
        String[] deadlines2 = p2.getDeadlinesAsString().split(", ");
        long deadlineP21 = Long.parseLong(deadlines2[0]);
        long deadlineP22 = Long.parseLong(deadlines2[1]);
        long deadlineP23 = Long.parseLong(deadlines2[2]);
        long deadTime21 = deadline(p1, deadlineP21, b1);
        long deadTime22 = deadline(p1, deadlineP22, b2);
        long deadTime23 = deadline(p1, deadlineP23, b3);
        Assertions.assertTrue(deadTime21==0);
        Assertions.assertTrue(deadTime22==0);
        Assertions.assertTrue(deadTime23==0);
        System.out.println("test case 7 passed");
    }
    public void test_case8() throws Exception{
        test_case1();
        p1.checkOutDocument(b1);
        p1.checkOutDocumentForCertainPeriod(b2, 11);
        p2.checkOutDocumentForCertainPeriod(b1, 7);
        p2.checkOutDocumentForCertainPeriod(av2, 12);
        User p1 = librarian.searchUserByNameSurname("Sergey",  "Afonso");
        User p2 = librarian.searchUserByNameSurname("Nadia",  "Teixeira");
        String[] deadlines1 = p1.getDeadlinesAsString().split(", ");
        long deadlineP11 = Long.parseLong(deadlines1[0]);
        long deadlineP12 = Long.parseLong(deadlines1[1]);
        long deadTime11 = deadline(p1, deadlineP11, b1);
        long deadTime12 = deadline(p1, deadlineP12, b2);
        Assertions.assertEquals(deadTime11, 0);
        System.out.println(new Date(deadTime12));
        Assertions.assertEquals(deadTime12, 3);
        String[] deadlines2 = p2.getDeadlinesAsString().split(",");
        String[] words21 = deadlines2[0].split(" ");
        String[] words22 = deadlines2[1].split(" ");
        long deadlineP21 = Long.parseLong(words21[words21.length-1]);
        long deadlineP22 = Long.parseLong(words22[words22.length-1]);
        long deadTime21 = deadline(p1, deadlineP21, b1);
        long deadTime22 = deadline(p1, deadlineP22, av2);
        Assertions.assertTrue(deadTime21==7);
        Assertions.assertEquals(deadTime22, 2);
        System.out.println("test case 8 passed");
    }
    public void test_case9() throws Exception {
        Assertions.assertEquals(3, librarian.getNumberOfBooks());
        Assertions.assertEquals(2, librarian.getNumberOfAVMaterials());
        Assertions.assertEquals(3,  librarian.getNumberOfPatrons());
//        i. 1 copy of book b1
//        ii. 2 copies of book b2
//        iii. 1 copy of book b3
//        iv. 2 Video materials: av1 and av2
//        v. patrons p1, p2 and p3

    }
    //returns mount of days delay, 0 otherwise
    private long deadline(User user, long deadline, Document document ) throws SQLException {
        Date date = new Date();
        System.out.println(document.type.toLowerCase());
        long time = date.getTime();
        if(document.type.toLowerCase().equals("book")){

            String title = document.title;
            Book book = new Book(connection, intxt);
            book.setVariablesKnowingTitle(title);
            if(book.best_seller){
                time +=hour*24*14;
            }else{
                time = setDead(user, time);
            }

        }else if(document.type.toLowerCase().equals("av") ){
            time +=hour*24*14;
        }
        else{
            time = setDead(user, time);
        }
        System.out.println("OP:");
        System.out.println(new Date(time));
        System.out.println(new Date(deadline));
        if(time-12*hour<=deadline && deadline<=time+12*hour){
            return 0;
        }else{
            return (time-deadline)/(hour*24);
        }
    }
    public long setDead(User user, long time){
        System.out.println(user.type);
        if (user.type.toLowerCase().equals("student")){
            time +=hour*24*21;
        }else{
            time+=hour*24*28;
        }
        return time;
    }
}
