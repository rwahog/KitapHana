import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Library {
    private Connection connection;
    private Statement statement;
    private Scanner in;
    Library(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.in = in;
        this.statement = connection.createStatement();
    }
    public Document getDocumentByTitle(String title) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from documents where title = '"+title+"'");
        Document document = new Document(connection, in);
        if(resultSet.next()){
            document.setTitle(resultSet.getString("title"));
            document.setVariablesKnowingTitle();
            return document;
        }
        else{
            return null;
        }
    }
    public String searchDocumentByPossibleTitle(String title) throws SQLException { // Ищет документ по частично
        ResultSet resultSet = statement.executeQuery("select * from documents");
        String answer = "";
        while(resultSet.next()){
            String s = resultSet.getString("title");
            if(s.contains(title)){
                if(!answer.equals("")) answer = answer.concat(", " + s);
                else answer = answer.concat(s);
            }
        }
        return answer;
    }
    public String searchDocumentByAuthor(String name) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from authors where name = '"+name+"'");
        if(resultSet.next()){
            return resultSet.getString("documents");
        }
        else{
            return "No documents from this author";
        }
    }
    public String searchDocumentByKeyword(String keyword) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from keywords where keyword = '"+keyword+"'");
        if(resultSet.next()){
            return resultSet.getString("documents");
        }
        else{
            return "No documents with this keyword";
        }
    }
    public User searchUserByNameSurname(String name, String surname) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from users where name = '"+name+"', surname = '"+surname+"'");
        if(resultSet.next()){
            User user = new User(connection, in);
            user.setVariablesKnowingNameSurname(name, surname);
            return user;
        }
        else{
            return null;
        }
    }
    public User searchUserByCard_number(long card_number) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from users where card_number = '"+card_number+"'");
        if(resultSet.next()){
            User user = new User(connection, in);
            user.setVariablesKnowingCard_number(card_number);
            return user;
        }
        else{
            return null;
        }
    }
}

