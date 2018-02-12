import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Librarian extends User {
    Library library;
    Librarian(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
        library = new Library(connection, in);
    }

    @Override
    public void save() throws SQLException {
        super.save();
        statement.executeUpdate("update users set type = 'librarian' where id = '"+id+"'");
    }

    @Override
    public void login() throws SQLException {
        super.login();
    }

    //User
    public void removeUser(User user) throws SQLException {
        user.remove();
    }
    public void modifyUser(User user) throws SQLException {
        //chto modifayit'


        user.save();
    }
    public User searchUserByNameSurname(String name, String surname) throws SQLException {
        return library.searchUserByNameSurname(name, surname);
    }
    public User searchUserByCard_Number(long card_number) throws SQLException {
        return library.searchUserByCard_number(card_number);
    }

    //Document

    public void addDocument() throws SQLException {
        System.out.println("Type: ");
        String s = in.next();
        if(s.equals("book")){
            Book book = new Book(connection, in);
            book.read();
            book.save();
        }
        else if(s.equals("av")){
            AVMaterial avMaterial = new AVMaterial(connection, in);
            avMaterial.read();
            avMaterial.save();
        }
        else if(s.equals("ja")){
            JournalArticle journalArticle = new JournalArticle(connection, in);
            journalArticle.read();
            journalArticle.save();
        }
    }
    public void  removeDocument(Document document) throws SQLException {
        document.remove();
    }
    public void modifyDocument(Document document) throws SQLException {
        //chto modifayit'

        document.save();
    }

}
