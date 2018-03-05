package com.kitaphana;
import com.kitaphana.Database.Database;
import com.kitaphana.Service.EditUserService;
import com.kitaphana.algorithm.Librarian;
import org.junit.jupiter.api.Assertions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class Test {
    static Database db = new Database();


    public Test() throws SQLException {
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Statement statement = db.con.createStatement();
    }

    @org.junit.jupiter.api.Test
    public  static void test1() throws SQLException {

        EditUserService editUserService = new EditUserService();
        String name = "Timur";
        String surname = "Shilova";
        int id = 7;
        editUserService.editUser(name, surname, "Librarian", "1488322228", "1223", "wefov;neqijvn", "wrpbg", "e3ibjv", "wrtpob", "123", "123", "1231312", id, 19);
        ResultSet rs = db.runSqlQuery("select * from users where name = '"+name+"' and surname = '"+surname+"'");
        rs.next();
        Assertions.assertEquals(rs.getInt("id"), id);
    }
}
