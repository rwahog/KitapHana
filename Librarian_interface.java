import java.sql.Connection;
import java.sql.ResultSet;
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
        GUI.print("Get the title:");
        Document doc = library.getDocumentByTitle(GUI.read());
        librarian.modifyDocument(doc);
        doc.save();
        librarian.save();
    }

    private static void delete(Connection conn, Scanner in) throws SQLException {
        librarian.removeDocument(library.getDocumentByTitle(GUI.read()));
        librarian.save();
    }

    public static void add(Connection conn, Scanner in) throws SQLException {
        Document newDoc = new Document(conn, in);
        GUI.print("Set title:");
        newDoc.setTitle(GUI.read());
        newDoc.setVariablesKnowingTitle();
        librarian.addDocument(newDoc);
    }

    public static void manage(Connection conn, Scanner in) throws SQLException {
        GUI.print("DELETE or MODIFY");
        command = GUI.read();
        switch (command) {
            case ("DELETE"):
                librarian.removeUser(search(conn, in));
                librarian.save();
                break;
            case("MODIFY"):
                User user = search(conn, in);
                librarian.modifyUser(user);
                user.save();
                librarian.save();
                break;
            default: GUI.print("WRONG INPUT"); manage(conn, in)    ;
        }
    }
    public static User search(Connection conn, Scanner in) throws SQLException {
        GUI.print("Search by card number/name");
        command = GUI.read();
        switch (command){
            case("card number"):
                GUI.print("Get the number:");
                return library.searchUserByCard_number(Integer.parseInt(GUI.read()));
            case("name"):
                GUI.print("Get the name, then get surname:");
                return library.searchUserByNameSurname(GUI.read(), GUI.read());
        }
        return null;
    }
    public static void checkOverdue(Connection conn, Scanner in) throws SQLException {
         librarian.checkOutDocument(library.getDocumentByTitle(GUI.read()));

    }
}
