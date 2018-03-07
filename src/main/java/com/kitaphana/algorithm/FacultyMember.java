package com.kitaphana.algorithm;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class FacultyMember extends Patron {

    FacultyMember(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
        maxdays = 28;
    }

    @Override
    public void save() throws SQLException {

        super.save();
        statement.executeUpdate("update users set type = 'faculty member' where id = '"+id+"'");
    }
}
