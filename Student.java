import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Student extends User{
    Student(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
    }
}