package com.kitaphana.Testing;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Book;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.Service.DocumentService;
import com.kitaphana.Service.MyDocumentsService;
import com.kitaphana.Service.UserService;
import com.kitaphana.Service.MyDocumentsService;
import com.kitaphana.Service.LoginService;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class TestDelivery3 {
    MyDocumentsService libr = new MyDocumentsService();
    DocumentService libr2 = new DocumentService();
    long day = 1000*60*60*24;
    ResultSet rs;
    public Database db = Database.getInstance();
    DocumentService docServ = new DocumentService();
    UserService service = new UserService();
    //d1:
    User p1, p2, p3, s, v;
    Document d1 = new Document();
    Document d2 = new Document();
    Document d3 = new Document();
    int d1_id = 301;
    String d1_Title = "Introduction to Algorithms";
    String d1_Authors =  "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein";
    String d1_Publisher =  "MIT Press";
    int d1_Year =  2009;
    String d1_Edition =  "Third edition";
    int d1_Price =  5000;
    //d2:
    int d2_id = 302;
    String d2_Title = "Design Patterns: Elements of Reusable Object-Oriented Software";
    String d2_Authors =  "Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm";
    String d2_Publisher =  "Addison-Wesley Professional";
    int d2_Year = 2003;
    String d2_Edition =  "First edition";
    boolean best_seller  = true;
    int d2_Price =  1700;
    //d3:
    int d3_id = 303;
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
    public TestDelivery3() {
        d1.setPrice(5000);
        d2.setPrice(1700);
        d3.setPrice(700);
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @org.junit.jupiter.api.Test
    public void test_case1() throws SQLException {
        p1 = service.findUserById(p1_id);
        docServ.checkOut(p1.getId(), (int)d1_id);
        docServ.checkOut(p1.getId(), (int)d2_id);
        docServ.checkOutApproval(p1.getId()+"", d1_id+"");
        docServ.checkOutApproval(p1.getId()+"", d2_id+"");
        setDeadlines(p1, 28);

        //!!!!!!!!!!FINES!!!!!!!!!!!!!!!!!
        libr.returnDoc(p1.getId()+"", d2_id+"");
        libr2.returnDocApproval(p1.getId()+"", d2_id+"");
        rs = db.runSqlQuery("SELECT * from users where id = '"+p1.getId()+"'");
        rs.next();
        long deadline = Long.parseLong(rs.getString("deadlines"));
        int idshnik = Integer.parseInt(rs.getString("documents"));
        long fine = d1.getDeadlineOfDocument(deadline);
        Assertions.assertEquals(fine, 0);
        Assertions.assertEquals(idshnik, d1_id);
        libr.returnDoc(p1.getId()+"", d1_id+"");
        libr2.returnDocApproval(p1.getId()+"", d1_id+"");
    }
    public void test_case2() throws SQLException {

        p1 = service.findUserById(p1_id);
        docServ.checkOut(p1.getId(), (int)d1_id);
        docServ.checkOutApproval(p1.getId()+"", d1_id+"");
        docServ.checkOut(p1.getId(), (int)d2_id);
        docServ.checkOutApproval(p1.getId()+"", d2_id+"");
        setDeadlines(p1, 28);
        s = service.findUserById(s_id);
        docServ.checkOut(s.getId(), (int)d1_id);
        docServ.checkOutApproval(s_id+"", d1_id+"");
        docServ.checkOut(s.getId(), (int)d2_id);
        docServ.checkOutApproval(s.getId()+"", d2_id+"");
        setDeadlines(s, 28);
        v = service.findUserById(v_id);
        docServ.checkOut(v.getId(), (int)d1_id);
        docServ.checkOutApproval(v.getId()+"", d1_id+"");
        docServ.checkOut(v.getId(), (int)d2_id);
        docServ.checkOutApproval(v.getId()+"", d2_id+"");
        setDeadlines(v, 28);
        //!!!!!!!!!!!!!!!FINES!!!!!!!!!!!!!!!!!!!
        rs = db.runSqlQuery("SELECT * from users where id = '"+p1.getId()+"'");
        rs.next();
        String[] deadlines1 = rs.getString("deadlines").split(",");
        long fine1 = d1.getDeadlineOfDocument(Long.parseLong(deadlines1[0]));
        long fine2 = d2.getDeadlineOfDocument(Long.parseLong(deadlines1[1]));
        Assertions.assertEquals(fine1, 0);
        Assertions.assertEquals(fine2, 0);
        rs = db.runSqlQuery("SELECT * from users where id = '"+s.getId()+"'");
        rs.next();
        deadlines1 = rs.getString("deadlines").split(",");
        fine1 = d1.getDeadlineOfDocument(Long.parseLong(deadlines1[0]));
        fine2 = d2.getDeadlineOfDocument(Long.parseLong(deadlines1[1]));
        Assertions.assertEquals(fine1, 700);
        Assertions.assertEquals(fine2, 1400);
        rs = db.runSqlQuery("SELECT * from users where id = '"+v.getId()+"'");
        rs.next();
        deadlines1 = rs.getString("deadlines").split(",");
        fine1 = d1.getDeadlineOfDocument(Long.parseLong(deadlines1[0]));
        fine2 = d2.getDeadlineOfDocument(Long.parseLong(deadlines1[1]));

        Assertions.assertEquals(fine1, 2100);
        Assertions.assertEquals(fine2, 1700);

        libr.returnDoc(p1.getId()+"", d1_id+"");
        libr2.returnDocApproval(p1.getId()+"", d1_id+"");
        libr.returnDoc(p1.getId()+"", d2_id+"");
        libr2.returnDocApproval(p1.getId()+"", d2_id+"");

        libr.returnDoc(v.getId()+"", d1_id+"");
        libr2.returnDocApproval(v.getId()+"", d1_id+"");
        libr.returnDoc(v.getId()+"", d2_id+"");
        libr2.returnDocApproval(v.getId()+"", d2_id+"");

        libr.returnDoc(s.getId()+"", d1_id+"");
        libr2.returnDocApproval(s.getId()+"", d1_id+"");
        libr.returnDoc(s.getId()+"", d2_id+"");
        libr2.returnDocApproval(s.getId()+"", d2_id+"");
    }
    public void test_case3() throws SQLException {

        p1 = service.findUserById(p1_id);
        docServ.checkOut(p1.getId(), (int)d1_id);
        docServ.checkOutApproval(p1.getId()+"", d1_id+"");
        setDeadlines(p1, 4);
        s = service.findUserById(s_id);
        docServ.checkOut(s.getId(), (int)d2_id);
        docServ.checkOutApproval(s.getId()+"", d2_id+"");
        setDeadlines(s, 4);

        v = service.findUserById(v_id);
        docServ.checkOut(v.getId(), (int)d2_id);
        docServ.checkOutApproval(v.getId()+"", d2_id+"");
        setDeadlines(v, 4);
        MyDocumentsService lib = new MyDocumentsService();
        lib.renewDoc(d1_id+"", p1.getId()+"");
        lib.renewDoc(d2_id+"", s.getId()+"");
        lib.renewDoc(d2_id+"", v.getId()+"");
        libr2.renewDocApproval(p1.getId()+"", d1_id+"");
        libr2.renewDocApproval(s.getId()+"", d2_id+"");
        libr2.renewDocApproval(v.getId()+"", d2_id+"");

        //!!!!!!!!!!!!!!!!!!!!!!!FINE!!!!!!!!!!!!

        rs = db.runSqlQuery("SELECT * from users where id = '"+s.getId()+"'");
        rs.next();
        Assertions.assertEquals(rs.getInt("documents"), d2_id);

        rs = db.runSqlQuery("SELECT * from users where id = '"+v.getId()+"'");
        rs.next();
        Assertions.assertEquals(rs.getInt("documents"), d2_id);

        rs = db.runSqlQuery("SELECT * from users where id = '"+p1.getId()+"'");
        rs.next();
        Assertions.assertEquals(rs.getInt("documents"), d1_id);

        libr.returnDoc(p1.getId()+"", d1_id+"");
        libr2.returnDocApproval(p1.getId()+"", d1_id+"");

        libr.returnDoc(v.getId()+"", d2_id+"");
        libr2.returnDocApproval(v.getId()+"", d2_id+"");

        libr.returnDoc(s.getId()+"", d2_id+"");
        libr2.returnDocApproval(s.getId()+"", d2_id+"");
    }
    public void test_case4() throws SQLException {
        p1 = service.findUserById(p1_id);
        d1 = docServ.setDocInfo(d1_id+"");
        d2 = docServ.setDocInfo(d2_id+"");
        docServ.checkOut(p1.getId(), (int)d1_id);
        docServ.checkOutApproval(p1.getId()+"", d1_id+"");
        setDeadlines(p1, 4);
        s = service.findUserById(s_id);
        docServ.checkOut(s.getId(), (int)d2_id);
        docServ.checkOutApproval(s.getId()+"", d2_id+"");
        setDeadlines(s, 4);
        v = service.findUserById(v_id);

        docServ.checkOut(v.getId(), (int)d2_id);
        docServ.checkOutApproval(v.getId()+"", d2_id+"");
        setDeadlines(v, 4);
        docServ.outstandingRequest(d2_id+"");
        libr.renewDoc(p1.getId()+"", d1_id+"");
        libr.renewDoc(s.getId()+"", d2_id+"");
        libr.renewDoc(v.getId()+"", d2_id+"");
        setDeadlines(p1, 28);
        rs = db.runSqlQuery("SELECT * from users where id = '"+p1.getId()+"'");
        rs.next();
        long deadline = Long.parseLong(rs.getString("deadlines"));
        long fine = d1.getDeadlineOfDocument(deadline)+3;
        Assertions.assertEquals(fine, 0);
        rs = db.runSqlQuery("SELECT * from users where id = '"+s.getId()+"'");
        rs.next();
        deadline = Long.parseLong(rs.getString("deadlines"));
        fine = d1.getDeadlineOfDocument(deadline);
        Assertions.assertEquals(fine, 0);
        rs = db.runSqlQuery("SELECT * from users where id = '"+v.getId()+"'");
        rs.next();
        deadline = Long.parseLong(rs.getString("deadlines"));
        fine = d1.getDeadlineOfDocument(deadline);
        Assertions.assertEquals(fine, 0);
        //!!!!!!!!!!FINE!!!!!!!!!!!!!!!!!
        //outstanding request to d2
        //assertions

        libr.returnDoc(p1.getId()+"", d1_id+"");
        libr2.returnDocApproval(p1.getId()+"", d1_id+"");

        libr.returnDoc(v.getId()+"", d2_id+"");
        libr2.returnDocApproval(v.getId()+"", d2_id+"");

        libr.returnDoc(s.getId()+"", d2_id+"");
        libr2.returnDocApproval(s.getId()+"", d2_id+"");
    }
    public void test_case5() throws SQLException {
        p1 = service.findUserById(p1_id);
        docServ.checkOut(p1.getId(), (int)d3_id);
        docServ.checkOutApproval(p1.getId()+"", d3_id+"");

        s = service.findUserById(s_id);
        docServ.checkOut(s.getId(), (int)d3_id);
        docServ.checkOutApproval(s.getId()+"", d3_id+"");

        v = service.findUserById(v_id);
        rs = db.runSqlQuery("SELECT * from documents where id = '"+d3_id+"'");
        rs.next();
        if(rs.getInt("amount")<=0){
            docServ.queue(v.getId(), d3_id);
        }
        else {
            docServ.checkOut(v.getId(), (int) d3_id);
            docServ.checkOutApproval(v.getId() + "", d3_id + "");
        }
        //assert: waiting list for d3 is [v]

        rs = db.runSqlQuery("SELECT * from documents where id = '"+d3_id+"'");
        rs.next();
        Assertions.assertEquals(rs.getString("waiting_list"), v.getId()+"");
        //outstanding request to d3

    }
    public void test_case6(boolean tc) throws SQLException {
        p1 = service.findUserById(p1_id);
        d3 = docServ.setDocInfo(d3_id+"");
        docServ.checkOut(p1.getId(), (int)d3_id);
        docServ.checkOutApproval(p1.getId()+"", d3_id+"");

        p2 = service.findUserById(p2_id);
        docServ.checkOut(p2.getId(), (int)d3_id);
        docServ.checkOutApproval(p2.getId()+"", d3_id+"");

        s = service.findUserById(s_id);
        rs = db.runSqlQuery("SELECT * from documents where id = '"+d3_id+"'");
        rs.next();
        if(rs.getInt("amount")<=0){
            docServ.queue(s.getId(), d3_id);
        }
        else {
            docServ.checkOut(s.getId(), (int) d3_id);
            docServ.checkOutApproval(s.getId() + "", d3_id + "");
        }
        v = service.findUserById(v_id);
        rs = db.runSqlQuery("SELECT * from documents where id = '"+d3_id+"'");
        rs.next();
        if(rs.getInt("amount")<=0){
            docServ.queue(v.getId(), d3_id);
        }
        else {
            docServ.checkOut(v.getId(), (int) d3_id);
            docServ.checkOutApproval(v.getId() + "", d3_id + "");
        }


        p3 = service.findUserById(p3_id);
        if(rs.getInt("amount")<=0){
            docServ.queue(p3.getId(), d3_id);
        }
        else {
            docServ.checkOut(p3.getId(), (int) d3_id);
            docServ.checkOutApproval(p3.getId() + "", d3_id + "");
        }
        rs = db.runSqlQuery("SELECT * from documents where id = '"+d3_id+"'");
        rs.next();
        Assertions.assertEquals(rs.getString("waiting_list"), s.getId()+","+v.getId()+","+p3.getId());
        //outstanding request for d3 if not going to tc
    }
    public void test_case7() throws SQLException {
        test_case6(true);
        //outstanding request to d3
        docServ.outstandingRequest(d3_id+"");
        rs = db.runSqlQuery("SELECT * from documents where id = '"+d3_id+"'");
        rs.next();
        Assertions.assertEquals(rs.getString("waiting_list"), "");
    }
    public void test_case8() throws SQLException {
        test_case6(true);
        p2 =  service.findUserById(p2_id);
        libr.returnDoc(p2.getId()+"", d3_id+"");
        libr2.returnDocApproval(p2.getId()+"", d3_id+"");
        rs = db.runSqlQuery("SELECT * from documents where id = '"+d3_id+"'");
        rs.next();
        Assertions.assertEquals(rs.getString("waiting_list"), s.getId()+","+v.getId()+","+p3.getId());
        rs = db.runSqlQuery("SELECT * from users where id = '"+p2_id+"'");
        rs.next();
        Assertions.assertEquals(rs.getString("deadlines"), "");
        //outstanding request to d3
    }
    public void test_case9() throws SQLException {
        test_case6(true);
        libr.renewDoc(d3_id+"", p1.getId()+"");
        libr2.renewDocApproval(p1.getId()+"", d3_id+"");
        rs = db.runSqlQuery("SELECT * from documents where id = '"+d3_id+"'");
        rs.next();
        Assertions.assertEquals(rs.getString("waiting_list"), s.getId()+","+v.getId()+","+p3.getId());
        rs = db.runSqlQuery("SELECT * from users where id = '"+p1.getId()+"'");
        rs.next();
        Assertions.assertEquals(rs.getString("documents"), d3_id+"");
        //assertion: deadline after 4 weeks
        setDeadlines(p1, 28);
        rs = db.runSqlQuery("SELECT * from users where id = '"+p1.getId()+"'");
        rs.next();
        String[] deadlines1 = rs.getString("deadlines").split(",");

        long fine = d3.getDeadlineOfDocument(Long.parseLong(deadlines1[0]));
        Assertions.assertEquals(fine, 0);
        libr.returnDoc(p1.getId()+"", d3_id+"");
        libr2.returnDocApproval(p1.getId()+"", d3_id+"");
    }
    public void test_case10() throws SQLException {
        p1 = service.findUserById(p1_id);
        v = service.findUserById(v_id);
        docServ.checkOut(v.getId(), (int)d1_id);
        docServ.checkOutApproval(v.getId()+"", d1_id+"");
        docServ.checkOut(p1.getId(), (int)d1_id);
        docServ.checkOutApproval(p1.getId()+"", d1_id+"");
        libr.renewDoc(d1_id+"", v.getId()+"");
        libr2.renewDocApproval(v.getId()+"", d1_id+"");
        libr.renewDoc(d1_id+"", p1.getId()+"");
        libr2.renewDocApproval(p1.getId()+"", d1_id+"");
        setDeadlines(p1, 4);
        setDeadlines(v, 4);
        rs = db.runSqlQuery("SELECT * from users where id = '"+p1.getId()+"'");
        rs.next();
        String deadline1_1 = rs.getString("deadlines");
        rs = db.runSqlQuery("SELECT * from users where id = '"+v.getId()+"'");
        rs.next();
        String deadline1_2 = rs.getString("deadlines");
        libr.renewDoc(d1_id+"", v.getId()+"");
        libr.renewDoc(d1_id+"", p1.getId()+"");
        rs = db.runSqlQuery("SELECT * from users where id = '"+p1.getId()+"'");
        rs.next();
        String deadline2_1 = rs.getString("deadlines");
        rs = db.runSqlQuery("SELECT * from users where id = '"+v.getId()+"'");
        rs.next();
        String deadline2_2 = rs.getString("deadlines");
        Assertions.assertEquals(deadline1_1, deadline2_1);
        Assertions.assertEquals(deadline1_2, deadline2_2);
    }
    public User setDeadlines(User user, int days) throws SQLException {
        rs = db.runSqlQuery("SELECT * from users where id = '"+user.getId()+"'");
        rs.next();
        String[] deadlines = rs.getString("deadlines").split(",");
        String resultSet = "";
        for (int i = 0; i<deadlines.length; i++){
            resultSet += (Long.parseLong(deadlines[i])-day*days)+"";
            if (i!= deadlines.length-1){
                resultSet+=",";
            }
        }
        db.runSqlUpdate("UPDATE users SET deadlines ='"+resultSet+"'  WHERE id='"+user.getId()+"'");
        user.setDeadlines(resultSet);
        return user;
    }
}

