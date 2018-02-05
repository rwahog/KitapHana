import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class User_interaction {
    static String command;
    public static void command(Connection conn, Scanner in) throws SQLException {
        System.out.println("Choose your command: registration/log in/exit");
        command = in.nextLine();
        switch (command){
            case("registration"):
                registration(conn, in);
                break;
            case("log in"):
                logIn(conn, in);
                break;
            case("exit"):
                GUI.exit();
            default:
                System.out.println("Wrong input");
                command(conn, in);
        }
    }

    public static void logIn(Connection conn, Scanner in) throws SQLException {
        System.out.println("Patron/Librarian");
        command = in.nextLine();
        switch (command){
            case("Librarian"):
                Librarian librarian = new Librarian(conn, in);
                librarian.login();
                Librarian_interaction.command(conn, in, librarian);
                break;
            case("Patron"):
                Patron patron = new Patron(conn, in);
                patron.login();
                Patron_interaction.command(conn, in, patron);
                break;
            default:
                System.out.println("Incorrect input");
                logIn(conn, in);
        }
        System.out.println("try again");
        logIn(conn, in);
    }

    public static void registration(Connection conn, Scanner in) throws SQLException {
        System.out.println("Patron/Librarian");
        command = in.nextLine();
        switch (command){
            case("Librarian"):
                Librarian librarian = new Librarian(conn, in);
                librarian.read();
                Librarian_interaction.command(conn, in, librarian);
                break;
            case("Patron"):
                Patron patron = new Patron(conn, in);
                patron.read();
                Patron_interaction.command(conn, in, patron);
                break;
            default:
                System.out.println("Incorrect input");
                registration(conn, in);
        }
        System.out.println("try again");
        registration(conn, in);
    }
}
