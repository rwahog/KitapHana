import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class FacultyMember extends Patron {

    FacultyMember(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
        maxdays = 21;
    }

}
