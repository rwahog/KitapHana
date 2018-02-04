import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class FacultyMember extends Patron {

    FacultyMember(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
        maxdays = 21;
    }

    @Override
    public void save() throws SQLException {
        super.save();
        statement.executeUpdate("update users set type = 'faculty member' where id = '"+id+"'");
    }
}
