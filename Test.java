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
        librarian.login();
        Patron patron = new Patron(connection, in);
        patron.login();
        Book book1 = new Book(connection, in);
        book1.setTitle("Touch Of Class");
        book1.setVariablesKnowingTitle();
        Assertions.assertEquals("", patron.getDocumentsAsString());
        patron.checkOutDocument(book1);
        Assertions.assertEquals("Touch Of Class", patron.getDocumentsAsString());
        Assertions.assertEquals(book1.getTitle(), patron.searchDocumentByPossibleTitle("Touch Of Class"));
        Library library = new Library(connection, in);
        int n = library.getDocumentByTitle("Touch Of Class").getAmount();
        Assertions.assertEquals(1, n);
    }
}
