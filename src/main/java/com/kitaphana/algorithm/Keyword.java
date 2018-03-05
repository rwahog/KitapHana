import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Keyword {
    protected int id;
    protected ArrayList<Document> documents;
    protected String keyword;
    protected Statement statement;
    protected Connection connection;
    protected Scanner in;
    Keyword(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.in = in;
        statement = connection.createStatement();
        documents = new ArrayList<Document>();
    }
    public void save() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from keywords where keyword = '"+keyword+"'");
        if(resultSet.next()){
            statement.executeUpdate("update keywords set keyword ='"+keyword+"', documents = '"+getDocumentsAsString()+"' where id = '"+id+"'");
        }
        else {
            statement.executeUpdate("insert into keywords (keyword, documents) values ('" + keyword + "', '" + getDocumentsAsString() + "')");
            resultSet = statement.executeQuery("select * from keywords where keyword = '" + keyword + "'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        }
    }
    public void setVariablesKnowingKeyword() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from keywords where keyword = '"+keyword+"'");
        if(resultSet.next()){
            id = resultSet.getInt("id");
            addDocumentsFromString(resultSet.getString("documents"));
        }
    }
    //Keyword
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) throws SQLException {
        this.keyword = keyword;
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
                cur.concat(String.valueOf(s.charAt(j)));
                j++;
            }
            Document document = new Document(connection, in);
            addDocument(document);
        }
    }
    public void removeDocument(Document document) throws SQLException {
        documents.remove(document);
    }
    public String getDocumentsAsString(){
        String s = "";
        for(int i = 0; i<documents.size(); i++){
            if(i<documents.size()-1) s = s.concat(String.valueOf(documents.get(i).getId()) + ", ");
            else s = s.concat(String.valueOf(documents.get(i).getId()));
        }
        return s;
    }
}
