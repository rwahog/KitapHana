import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Patron_interface {
    static String command;
    static Library library;
    static Patron patron;
    public static void command(Connection conn, Scanner in, Patron patronCur) throws SQLException {
        patron  = patronCur;
        library = new Library(conn, in);
        GUI.print("Choose your command: search for/check out/return document/exit");
        command = GUI.read();
        switch (command){
            case("search for"):
                searchFor(conn, in);
                break;
            case("check out") :
                checkOut(conn, in);
                break;
            case("return document")    :
                returnDoc(conn, in);
                break;
            case("exit"):
                GUI.exit();
                break;
            default: GUI.print("WRONG INPUT");
        }
        command(conn, in, patron);
    }

    private static void returnDoc(Connection conn, Scanner in) throws SQLException {
    }

    private static void checkOut(Connection conn, Scanner in) throws SQLException {

        Document document = searchFor(conn, in);
        patron.checkOutDocument(document);
    }

    public static Document searchFor(Connection conn, Scanner in) throws SQLException {
        GUI.print("Author/Keyword/PossibleTitle");
        command = GUI.read();
        Document doc = new Document(conn, in);
        switch (command){
            case("Author"):
                GUI.print("write the Author");
                doc = library.getDocumentByTitle(library.searchDocumentByAuthor(GUI.read()));
                break;
            case("PossibleTitle"):
                GUI.print("write the Title");
                doc = library.getDocumentByTitle(GUI.read());
                break;
            case("Keyword"):
                GUI.print("write the keyword");
                doc = library.getDocumentByTitle(library.searchDocumentByKeyword(GUI.read()));
                break;
        }
        return doc;
    }

}
