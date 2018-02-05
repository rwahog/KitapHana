import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Librarian_interaction {
    static Librarian librarian;
    static String command;
    static Library library;

    public static void command(Connection conn, Scanner in, Librarian librarianCur) throws SQLException {
        librarian = librarianCur;
        library = new Library(conn, in);
        System.out.println("Choose your command: add/delete/modify/manage/check overdue/exit");
        command = in.nextLine();
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
                System.out.println("WRONG INPUT");
        }
        command(conn, in, librarian);
    }

    private static void modify(Connection conn, Scanner in) throws SQLException {
        System.out.println("Get the title:");
        Document doc = library.getDocumentByTitle(in.nextLine());
        librarian.modifyDocument(doc);
        doc.save();
        librarian.save();
    }

    private static void delete(Connection conn, Scanner in) throws SQLException {
        librarian.removeDocument(library.getDocumentByTitle(in.nextLine()));
        librarian.save();
    }

    public static void add(Connection conn, Scanner in) throws SQLException {
        Document newDoc = new Document(conn, in);
        System.out.println("Set title:");
        newDoc.setTitle(in.nextLine());
        newDoc.setVariablesKnowingTitle();
        librarian.addDocument(newDoc);
    }

    public static void manage(Connection conn, Scanner in) throws SQLException {
        System.out.println("DELETE or MODIFY");
        command = in.nextLine();
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
            default: System.out.println("WRONG INPUT"); manage(conn, in)    ;
        }
    }
    public static User search(Connection conn, Scanner in) throws SQLException {
        System.out.println("Search by card number/name");
        command = in.nextLine();
        switch (command){
            case("card number"):
                System.out.println("Get the number:");
                return library.searchUserByCard_number(Integer.parseInt(in.nextLine()));
            case("name"):
                System.out.println("Get the name, then get surname:");
                return library.searchUserByNameSurname(in.nextLine(), in.nextLine());
        }
        return null;
    }
    public static void checkOverdue(Connection conn, Scanner in) throws SQLException {
        librarian.checkOutDocument(library.getDocumentByTitle(in.nextLine()));

    }
}
