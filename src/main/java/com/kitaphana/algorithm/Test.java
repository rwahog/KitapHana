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
        Student patron = new Student(connection, in);
        patron.login();
        Book book1 = new Book(connection, in);
        book1.setVariablesKnowingTitle("Touch Of Class");
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
        Student patron = new Student(connection, in);
        patron.login();
        Assertions.assertEquals("", patron.searchDocumentByAuthor(Cormen));
    }

    @org.junit.jupiter.api.Test
    public void test_case3() throws Exception{
        FacultyMember facultyMember = new FacultyMember(connection, in);
     facultyMember.login();
//        Student student = new Student(connection, in);
//        student.login();
//        Librarian librarian = new Librarian(connection, in);
//        librarian.login();

        Book b = new Book(connection, in);
        b.setVariablesKnowingTitle("Touch Of Class");
        facultyMember.checkOutDocument(b);
        Assertions.assertEquals(28, facultyMember.getDeadlineOfDocument(b));
    }

    @org.junit.jupiter.api.Test
    public void test_case4() throws Exception{
        FacultyMember facultyMember = new FacultyMember(connection, in);
        facultyMember.login();
        Student student = new Student(connection, in);
        student.login();
        Book b = new Book(connection, in);
        b.setVariablesKnowingTitle("Touch Of Class");
        facultyMember.checkOutDocument(b);
        Assertions.assertEquals(14, facultyMember.getDeadlineOfDocument(b));
    }

    @org.junit.jupiter.api.Test
    public void test_case5() throws Exception {
        Book A = new Book(connection, in);
        A.setVariablesKnowingTitle("Touch Of Class");
        Assertions.assertEquals(2, A.getAmount());
        Student patron1 = new Student(connection, in);
        patron1.login();
        Student patron2 = new Student(connection, in);
        patron2.login();
        FacultyMember patron3 = new FacultyMember(connection, in);
        patron3.login();
        patron1.checkOutDocument(A);
        Assertions.assertEquals(true, patron1.hasDocument(A));
        patron2.checkOutDocument(A);
        Assertions.assertEquals(true, patron2.hasDocument(A));
        patron3.checkOutDocument(A);
        Assertions.assertEquals(false, patron3.hasDocument(A));
    }

    @org.junit.jupiter.api.Test
    public void test_case6() throws Exception {
        Librarian librarian = new Librarian(connection, in);
        librarian.login();
        Student patron = new Student(connection, in);
        patron.login();
        Book book = new Book(connection, in);
        book.setVariablesKnowingTitle("Touch Of Class");
        patron.checkOutDocument(book);
        Assertions.assertEquals(true, patron.hasDocument(book));
        int amountOfBooksOfUser= patron.getDocuments().size();
        int amountOfBooksInLibrary = book.getAmount();
        patron.checkOutDocument(book);
        Assertions.assertEquals(amountOfBooksOfUser, patron.getDocuments().size());
        Assertions.assertEquals(amountOfBooksInLibrary, book.getAmount());
    }
    @org.junit.jupiter.api.Test
    public void test_case7() throws Exception {
        Student p1 = new Student(connection, in);
        p1.login();
        Student p2 = new Student(connection, in);
        p2.login();
//        Librarian librarian = new Librarian(connection, in);
////        librarian.login();
        Book book = new Book(connection, in);
        book.setVariablesKnowingTitle("Touch Of Class");
        p1.checkOutDocument(book);
        p2.checkOutDocument(book);
        Assertions.assertEquals(true, p1.hasDocument(book));
        Assertions.assertEquals(true, p2.hasDocument(book));
    }

    @org.junit.jupiter.api.Test
    public void test_case8() throws Exception {
        FacultyMember facultyMember = new FacultyMember(connection, in);
        facultyMember.login();
        Student student = new Student(connection, in);
        student.login();
        Librarian librarian = new Librarian(connection, in);
        librarian.login();
        Book book = new Book(connection, in);
        book.setVariablesKnowingTitle("Touch Of Class");
        student.checkOutDocument(book);
        Assertions.assertEquals(21, student.getDeadlineOfDocument(book));
    }
    @org.junit.jupiter.api.Test
    public void test_case9() throws Exception {
        FacultyMember facultyMember = new FacultyMember(connection, in);
        facultyMember.login();
        Student student = new Student(connection, in);
        student.login();
        Librarian librarian = new Librarian(connection, in);
        librarian.login();
        Book book = new Book(connection, in);
        book.setVariablesKnowingTitle("Touch Of Class");
        Assertions.assertEquals(1, book.getBest_sellerAsInt());
        student.checkOutDocument(book);
        Assertions.assertEquals(14, student.getDeadlineOfDocument(book));
    }

    @org.junit.jupiter.api.Test
    public void test_case10() throws Exception {
        Student student = new Student(connection, in);
        student.login();
        Librarian librarian = new Librarian(connection, in);
        librarian.login();
        Book book = new Book(connection, in);
        book.read();
        book.save();
        Book book1 = new Book(connection, in);
        book1.setVariablesKnowingTitle("Touch Of Class");
        student.checkOutDocument(book1);
        Assertions.assertEquals(true, student.hasDocument(book1));
        student.checkOutDocument(book);
        Assertions.assertEquals(false, student.hasDocument(book));
    }



}
