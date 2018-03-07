package com.kitaphana.algorithm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Student extends Patron{
    Student(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
        maxdays = 21;
    }

    @Override
    public void save() throws SQLException {
        super.save();

        statement.executeUpdate("update users set type = 'student' where id = '"+id+"'");
    }
}