import java.security.Key;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    public void addUser() throws SQLException {
        System.out.println("Type of user: ");
        String s = in.nextLine();
        if(s.equals("Student")){
            Student student = new Student(connection, in);
            student.read();
            student.save();
        }
        else if(s.equals("Faculty member")){
            FacultyMember facultyMember = new FacultyMember(connection, in);
            facultyMember.read();
            facultyMember.save();
        }
        else if(s.equals("Librarian")){
            Librarian librarian = new Librarian(connection, in);
            librarian.read();
            librarian.save();
        }
    }
    public void modifyUser(User user) throws SQLException {
        System.out.println("What do you want to modify?");
        String field = in.nextLine();
        if(field.equals("Name")){
            String name = in.nextLine();
            user.setName(name);
        }
        else if (field.equals("Surname")){
            String surname = in.nextLine();
            user.setSurname(surname);
        }
        else if (field.equals("Phone number")){
            String phone_number = in.nextLine();
            user.setPhone_number(phone_number);
        }
        else if(field.equals("Email")){
            String email = in.nextLine();
            user.setEmail(email);
        }
        else if(field.equals("Type")){
            String type = in.nextLine();
            user.setType(type);
        }
        else if(field.equals("Delete document")){
            Document document = new Document(connection, in);
            String title = in.nextLine();
            document = library.getDocumentByTitle(title);
            user.returnDocument(document);
        }
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
        System.out.println("What do you want to modify?");
        String field = in.nextLine();
        if(field.equals("Title")){
            String title = in.nextLine();
            document.setTitle(title);
            document.save();
        }
        else if (field.equals("Remove author")){
            String name = in.nextLine();
            String surname = in.nextLine();
            Author author = new Author(connection, in);
            author.setName(name);
            author.setSurname(surname);
            author.setVariablesKnowingNameSurname();
            document.removeAuthor(author);
        }
        else if (field.equals("Add author")){
            String name = in.nextLine();
            String surname = in.nextLine();
            Author author = new Author(connection, in);
            author.setName(name);
            author.setSurname(surname);
            author.setVariablesKnowingNameSurname();
            document.addAuthor(author);
        }
        else if (field.equals("Remove keyword")){
            String word = in.nextLine();
            Keyword keyword = new Keyword(connection, in);
            keyword.setKeyword(word);
            keyword.setVariablesKnowingKeyword();
            document.removeKeyword(keyword);
        }
        else if (field.equals("Add keyword")){
            String word = in.nextLine();
            Keyword keyword = new Keyword(connection, in);
            keyword.setKeyword(word);
            keyword.setVariablesKnowingKeyword();
            document.addKeyword(keyword);
        }
        else if(field.equals("Price")){
            int price = in.nextInt();
            document.setPrice(price);
        }
        else if(field.equals("Amount")){
            int amount = in.nextInt();
            document.setAmount(amount);
        }
        document.save();
    }

}
