import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Test {
    protected Statement statement;
    protected Connection connection;
    protected Scanner in;
    Test(Connection connection, Scanner in) throws SQLException {
        this.in = in;
        this.connection = connection;
        this.statement = connection.createStatement();
    }
    test_case1(){
        
    }
}
