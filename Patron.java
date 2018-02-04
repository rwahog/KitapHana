import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        ResultSet resultSet = statement.executeQuery("select * from users where id = '"+id+"'");
        if(resultSet.next()){
            if(resultSet.getString("type").equals("patron")){
                GUI.print("Successfully");
            }
            else{
                GUI.print("Wrong card number");
            }
        }
    }
}
