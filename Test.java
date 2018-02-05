import org.junit.jupiter.api.Assertions;

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
    @org.junit.jupiter.api.Test
    public void test_case1() throws Exception{
        Librarian librarian = new Librarian(connection, in);
        librarian.read();
        librarian.save();
        Patron patron = new Patron(connection,in);
        patron.read();
        patron.save();
        Book book1 = new Book(connection, in);
        book1.read();
        book1.save();
        book1.amount = 2;
        book1.title = "Touch Of Class";
        Assertions.assertEquals(null, patron.getDocumentsAsString());
        Assertions.assertEquals(book1, patron.searchDocumentByPossibleTitle("Touch Of Class"));

    }
}
