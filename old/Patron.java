import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Patron extends User {
    Patron(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
    }

    @Override
    public void save() throws SQLException {
        super.save();
        statement.executeUpdate("update users set type = 'patron' where id = '"+id+"'");
    }

    @Override
    public void login() throws SQLException {
        super.login();
    }
}
