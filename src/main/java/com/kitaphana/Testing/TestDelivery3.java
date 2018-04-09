package com.kitaphana.Testing;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Book;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.Service.DocumentService;
import com.kitaphana.Service.EditUserService;
import com.kitaphana.Service.LibrarianPanelService;
import com.kitaphana.Service.LoginService;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class TestDelivery3 {
    long day = 1000*60*60*24;
    ResultSet rs;
    public Database db = Database.getInstance();
    DocumentService docServ = new DocumentService();
    LoginService loginService = new LoginService();
    EditUserService service = new EditUserService();
    //d1:
    User p1, p2, p3, s, v;
    Document d1, d2, d3;
    int d1_id = 311;
    String d1_Title = "Introduction to Algorithms";
    String d1_Authors =  "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein";
    String d1_Publisher =  "MIT Press";
    int d1_Year =  2009;
    String d1_Edition =  "Third edition";
    int d1_Price =  5000;
    //d2:
    int d2_id = 312;
    String d2_Title = "Design Patterns: Elements of Reusable Object-Oriented Software";
    String d2_Authors =  "Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm";
    String d2_Publisher =  "Addison-Wesley Professional";
    int d2_Year = 2003;
    String d2_Edition =  "First edition";
    boolean best_seller  = true;
    int d2_Price =  1700;
    //d3:
    int d3_id = 313;
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
    @org.junit.jupiter.api.Test
    public void test_case1() throws SQLException {
        p1 = service.setUserInfo(p1_id);
        d1 = docServ.setDocInfo(d1_id+"");
        d2 = docServ.setDocInfo(d2_id+"");
        docServ.checkOut(p1.getId(), (int)d1.getId());
        docServ.checkOut(p1.getId(), (int)d2.getId());
        setDeadlines(p1, 28);
        LibrarianPanelService libr = new LibrarianPanelService();
        //!!!!!!!!
        libr.returnDoc(p1, d2);
        //!!!!!!!!
        rs = db.runSqlQuery("SELECT * from users where id = '"+p1.getId()+"'");
        rs.next();
        int fine = Integer.parseInt(rs.getString("fine"));
        int idshnik = Integer.parseInt(rs.getString("documents"));
        Assertions.assertEquals(fine, 0);
        Assertions.assertEquals(idshnik, d1.getId());
        libr.returnDoc(p1, d1);
    }
    public void test_case2() throws SQLException {
        p1 = service.setUserInfo(p1_id);
        d1 = docServ.setDocInfo(d1_id+"");
        d2 = docServ.setDocInfo(d2_id+"");
        docServ.checkOut(p1.getId(), (int)d1.getId());
        docServ.checkOut(p1.getId(), (int)d2.getId());
        setDeadlines(p1, 28);
        s = service.setUserInfo(s_id);
        docServ.checkOut(s.getId(), (int)d1.getId());
        docServ.checkOut(s.getId(), (int)d2.getId());
        setDeadlines(s, 28);
        v = service.setUserInfo(v_id);
        docServ.checkOut(v.getId(), (int)d1.getId());
        docServ.checkOut(v.getId(), (int)d2.getId());
        setDeadlines(v, 28);
        rs = db.runSqlQuery("SELECT * from users where id = '"+p1.getId()+"'");
        rs.next();
        String fine = rs.getString("fine");
        Assertions.assertTrue(fine.equals("0,0"));
        rs = db.runSqlQuery("SELECT * from users where id = '"+s.getId()+"'");
        rs.next();
        fine = rs.getString("fine");
        Assertions.assertTrue(fine.equals("700, 1400"));
        rs = db.runSqlQuery("SELECT * from users where id = '"+v.getId()+"'");
        rs.next();
        fine = rs.getString("fine");
        Assertions.assertTrue(fine.equals("2100, 1700"));
    }
    public void test_case3() throws SQLException {
        p1 = service.setUserInfo(p1_id);
        d1 = docServ.setDocInfo(d1_id+"");
        docServ.checkOut(p1.getId(), (int)d1.getId());
        setDeadlines(p1, 4);
        s = service.setUserInfo(s_id);
        docServ.checkOut(s.getId(), (int)d1.getId());
        setDeadlines(s, 4);
        v = service.setUserInfo(v_id);
        docServ.checkOut(v.getId(), (int)d1.getId());
        setDeadlines(v, 4);
        LibrarianPanelService lib = new LibrarianPanelService();
        lib.renew(p1, d1);
        lib.renew(s, d1);
        lib.renew(v, d1);

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
    public User setDeadlines(User user, int days) throws SQLException {
        String[] deadlines = user.getDeadlines().split(",");
        String resultSet = "";
        for (int i = 0; i<deadlines.length; i++){
            resultSet += (Long.parseLong(deadlines[i])+day*days)+"";
            if (i!= deadlines.length-1){
                resultSet+=",";
            }
        }
        db.runSqlUpdate("UPDATE users SET deadlines ='"+resultSet+"'  WHETE id='"+user.getId()+"'");
        user.setDeadlines(resultSet);
        return user;
    }
}

