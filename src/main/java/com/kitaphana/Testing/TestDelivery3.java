package com.kitaphana.Testing;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.User;
import com.kitaphana.Service.LoginService;

import java.sql.Connection;
import java.util.Scanner;

public class TestDelivery3 {
    public Database db = Database.getInstance();
    LoginService loginService = new LoginService();
    //d1:
    User p1, p2, p3, s, v;
    String d1_Title = "Introduction to Algorithms";
    String d1_Authors =  "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein";
    String d1_Publisher =  "MIT Press";
    int d1_Year =  2009;
    String d1_Edition =  "Third edition";
    int d1_Price =  5000;
    //d2:
    String d2_Title = "Design Patterns: Elements of Reusable Object-Oriented Software";
    String d2_Authors =  "Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm";
    String d2_Publisher =  "Addison-Wesley Professional";
    int d2_Year = 2003;
    String d2_Edition =  "First edition";
    boolean best_seller  = true;
    int d2_Price =  1700;
    //d3:
    String d3_Title =  "Null References: The Billion Dollar Mistake";
    String d3_Authors = "Tony Hoare";
    int d3_Price =  700;
    //p1:
    int p1_id = 301;
    String p1_Name = "Sergey Afonso";
    String p1_Address = "Via Margutta, 3";
    String p1_Phone_Number =  "30001";
    String p1_cardID =  "1010";
    String p1_Type =  "Professor";
    //p2:
    int p2_id = 302;
    String p2_Name = "Nadia Teixeira";
    String p2_Address =  "Via Sacra, 13";
    String p2_Phone_Number = "30002";
    String p2_cardID =  "1011";
    String p2_Type =  "Professor";
    //p3:
    int p3_id = 303;
    String p3_Name =  "Elvira Espindola";
    String p3_Address =  "Via del Corso, 22";
    String p3_PhoneNumber = "30003";
    String p3_cardID = "1100";
    String p3_Type = "Professor";
    //s:
    int s_id = 304;
    String s_Name =  "Andrey Velo";
    String s_Address = "Avenida Mazatlan, 250";
    String s_PhoneNumber = "30004";
    String s_cardID = "1101";
    String s_Type = "Student";
    //v:
    int v_id = 305;
    String v_Name = "Veronika Rama";
    String v_Address = "Stret Atocha, 27";
    String v_PhoneNumber = "30005";
    String v_cardID = "1110";
    String v_Type = "Visiting Professor";
    public TestDelivery3(Connection conn, Scanner scan, Scanner scan2) {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void test_case1() {

    }
    public void test_case2() {
    }
    public void test_case3() {
    }
    public void test_case4() {
    }
    public void test_case5() {
    }
    public void test_case6() {
    }
    public void test_case7() {
    }
    public void test_case8() {
    }
    public void test_case9() {
    }
    public void test_case10() {
    }
}

