import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Librarian_interface {
    static Librarian librarian;
    static String command;
    static Library library;

    public static void command(Connection conn, Scanner in, Librarian librarianCur) throws SQLException {
        librarian = librarianCur;
        library = new Library(conn, in);
        GUI.print("Choose your command: add/delete/modify/manage/check overdue/exit");
        command = GUI.read();
        switch (command) {
            case ("manage"):
                manage(conn, in);
                break;
            case ("check overdue"):
                checkOverdue(conn, in);
                break;
            case ("add"):
                add(conn, in);
                break;
            case ("delete"):
                delete(conn, in);
                break;
            case ("modify"):
                modify(conn, in);
                break;
            case ("exit"):
                GUI.exit();
                break;
            default:
                GUI.print("WRONG INPUT");
        }
        command(conn, in, librarian);
    }

    private static void modify(Connection conn, Scanner in) throws SQLException {
        librarian.modifyDocument(library.getDocumentByTitle(GUI.read()));
    }

    private static void delete(Connection conn, Scanner in) throws SQLException {
        librarian.removeDocument(library.getDocumentByTitle(GUI.read()));
    }

    public static void add(Connection conn, Scanner in) throws SQLException {
        Document newDoc = new Document(conn, in);
        librarian.addDocument(newDoc);
    }

    public static void manage(Connection conn, Scanner in) throws SQLException {
        GUI.print("DELETE or MODIFY");
        command = GUI.read();

        Patron patron = new Patron(conn, in);
        switch (command) {
            case ("DELETE"):
                librarian.removeUser(patron);
                break;
            case("MODIFY"):
                librarian.removeUser(patron);
                break;
            default: GUI.print("WRONG INPUT"); manage(conn, in)    ;
        }
    }

    public static void checkOverdue(Connection conn, Scanner in) throws SQLException {
         librarian.checkOutDocument(library.getDocumentByTitle(GUI.read()));
    }
}
