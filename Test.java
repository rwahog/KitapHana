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

    @org.junit.jupiter.api.Test
    public void test_case2() throws Exception{
        Author Cormen = new Author(connection, in);
        Cormen.read();
        Cormen.save();
        Patron patron = new Patron(connection, in);
        patron.login();
        Assertions.assertEquals("a", patron.searchDocumentByAuthor(Cormen));
    }

    @org.junit.jupiter.api.Test
    public void test_case3() throws Exception{
        FacultyMember Shilov = new FacultyMember(connection, in);
        Shilov.login();
        Student Ivanov = new Student(connection, in);
        Ivanov.login();
        Librarian librarian = new Librarian(connection, in);
        librarian.login();

        Book b = new Book(connection, in);
        b.setTitle("Touch Of Class");
        b.setVariablesKnowingTitle();
        Shilov.checkOutDocument(b);
        Assertions.assertEquals(28, Shilov.maxdays);
    }


    @org.junit.jupiter.api.Test
    public void test_case4() throws Exception{
        FacultyMember Shilov = new FacultyMember(connection, in);
        Shilov.login();
        Student Ivanov = new Student(connection, in);
        Ivanov.login();
        Book b = new Book(connection, in);
        b.read();
        b.save();
        Assertions.assertEquals(14, Shilov.maxdays);
    }

    @org.junit.jupiter.api.Test
    public void test_case5() throws Exception {
        Book A = new Book(connection, in);
        A.read();
        A.save();
        Assertions.assertEquals(2, A.getAmount());
        Patron patron1 = new Patron(connection, in);
        patron1.login();
        Patron patron2 = new Patron(connection, in);
        patron2.login();
        Patron patron3 = new Patron(connection, in);
        patron3.login();
        patron1.checkOutDocument(A);
        Assertions.assertEquals(A.getTitle(), patron1.getDocumentsAsString());
        patron2.checkOutDocument(A);
        Assertions.assertEquals(A.getTitle(), patron2.getDocumentsAsString());
        patron3.checkOutDocument(A);
        Assertions.assertEquals("", patron3.searchDocumentByPossibleTitle(A.getTitle()));
    }
    @org.junit.jupiter.api.Test
    public void test_case6() throws Exception {
        Librarian librarian = new Librarian(connection, in);
        librarian.login();
        Patron patron = new Patron(connection, in);
        patron.login();
        Book book = new Book(connection, in);
        book.read();
        book.save();
        patron.checkOutDocument(book);
        Assertions.assertEquals(book.getTitle(), patron.getDocumentsAsString());
        patron.checkOutDocument(book);
        Assertions.assertEquals(1, patron.getDocuments().size());
     //   Assertions.assertEquals(book, librarian.searchUserByCardNumber(patron.getCard_number()).getDocumentsAsString);
    }
    @org.junit.jupiter.api.Test
    public void test_case7() throws Exception {
        Patron p1 = new Patron(connection, in);
        p1.login();
        Patron p2 = new Patron(connection, in);
        p2.login();
        Librarian librarian = new Librarian(connection, in);
        librarian.login();
        Book book = new Book(connection, in);
        book.read();
        book.save();
        p1.checkOutDocument(book);
        p2.checkOutDocument(book);
        Assertions.assertEquals(book.getTitle(), p1.getDocumentsAsString());
        Assertions.assertEquals(book.getTitle(), p2.getDocumentsAsString());

    }
    @org.junit.jupiter.api.Test
    public void test_case8() throws Exception {
        FacultyMember Shilov = new FacultyMember(connection, in);
        Shilov.login();
        Student Ivanov = new Student(connection, in);
        Ivanov.login();
        Librarian librarian = new Librarian(connection, in);
        librarian.login();
        Book book = new Book(connection, in);
        book.read();
        book.save();
        Ivanov.checkOutDocument(book);
        Assertions.assertEquals(21, Ivanov.maxdays);
    }
    @org.junit.jupiter.api.Test
    public void test_case9() throws Exception {
        FacultyMember Shilov = new FacultyMember(connection, in);
        Shilov.login();
        Student Ivanov = new Student(connection, in);
        Ivanov.login();
        Librarian librarian = new Librarian(connection, in);
        librarian.login();
        Book book = new Book(connection, in);
        book.read();
        book.save();
        Assertions.assertEquals(1, book.getBest_sellerAsInt());
        Ivanov.checkOutDocument(book);
        Assertions.assertEquals(14, Ivanov.maxdays);
    }
    @org.junit.jupiter.api.Test
    public void test_case10() throws Exception {

    }
}
