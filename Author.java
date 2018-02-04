import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Author {
    private int id;
    private ArrayList<Document> documents;
    private String name, surname;
    private Statement statement;
    private Connection connection;
    private Scanner in;
    Author(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.in = in;
        statement = connection.createStatement();
        documents = new ArrayList<Document>();
    }
    public void save() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from authors where name = '"+name+"' and surname = '"+surname+"'");
        if(resultSet.next()){
            statement.executeUpdate("update authors set name ='"+name+"', surname = '"+surname+"', documents = '"+getDocumentsAsString()+"' where id = '"+id+"'");
        }
        else {
            statement.executeUpdate("insert into authors (name, surname, documents) values ('" + name + "', '"+surname+"', '" + getDocumentsAsString() + "')");
            resultSet = statement.executeQuery("select * from authors where name = '" + name + "' and surname = '"+surname+"'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        }
    }
    public void setVariablesKnowingNameSurname() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from authors where name = '"+name+"' and surname = '"+surname+"'");
        if(resultSet.next()){
            id = resultSet.getInt("id");
            addDocumentsFromString(resultSet.getString("documents"));
        }
    }
    //Name
    public String getName() {
        return name;
    }

    public void setName(String name) throws SQLException {
        this.name = name;
    }
    //Surname
    public String getSurname(){
        return surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    //Documents

    public ArrayList<Document> getDocuments() {
        return documents;
    }
    public void addDocument(Document document) throws SQLException {
        documents.add(document);
    }
    public void addDocumentsFromString(String s) throws SQLException {
        for(int i = 0; i<s.length(); i++){
            String cur = "";
            int j = i;
            while(j<s.length() && s.charAt(j) != ','){
                cur = cur.concat(String.valueOf(s.charAt(j)));
                j++;
            }
            i = j+1;
            Document document = new Document(connection, in);
            addDocument(document);
            document.setTitle(cur);
            document.setVariablesKnowingTitle();
        }
    }
    public void removeDocument(Document document) throws SQLException {
        documents.remove(document);
    }
    public String getDocumentsAsString(){
        String s = "";
        for(int i = 0; i<documents.size(); i++){
            if(i<documents.size()-1) s = s.concat(documents.get(i).getTitle() + ", ");
            else s = s.concat(documents.get(i).getTitle());
        }
        return s;
    }
}