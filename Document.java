import java.security.Key;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Document implements Document_interface{
    private String title;
    private ArrayList<Keyword> keywords;
    private ArrayList<Author> authors;
    private int price, amount, id, type;
    private Statement statement;
    private Connection connection;
    private Scanner in;
    Document(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
        this.in = in;
        keywords = new ArrayList<Keyword>();
        authors = new ArrayList<Author>();
    }
    public void readDocument() throws SQLException {
        System.out.println("Title: ");
        setTitle(in.next());
        System.out.println("Authors: ");
        while(in.hasNext()){
            String name = in.next();
            if(name.equals("end")) break;
            ResultSet resultSet = statement.executeQuery("select * from authors where name = '"+name+"'");
            Author author = new Author(connection, in);
            author.setName(name);
            addAuthor(author);
            author.addDocument(this);
        }
        System.out.println("Keywords: ");
        while(in.hasNext()){
            String word = in.next();
            if(word.equals("end")) break;
            ResultSet resultSet = statement.executeQuery("select * from keywords where keyword = '"+word+"'");
            Keyword keyword = new Keyword(connection, in);
            keyword.setKeyword(word);
            addKeyword(keyword);
            keyword.addDocument(this);
        }
        System.out.println("Price: ");
        setPrice(in.nextInt());
        System.out.println("Amount: ");
        setAmount(in.nextInt());
    }
    public void save() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from documents where title = '"+title+"'");
        if(resultSet.next()){
            statement.executeUpdate("update documents set title = '"+title+"', authors = '"+getAuthorsAsString()+"', keywords = '"+getKeywordsAsString()+"', price = '"+price+"', amount = '"+amount+"', type = '"+type+"' where id = '"+id+"'");
            for (int i = 0; i < authors.size(); i++) {
                authors.get(i).save();
            }
            for (int i = 0; i < keywords.size(); i++) {
                keywords.get(i).save();
            }
        }
        else {
            statement.executeUpdate("insert into documents (title, authors, keywords, price, amount) values ('" + title + "','" + getAuthorsAsString() + "', '" + getKeywordsAsString() + "', '" + price + "', '" + amount + "' )");
            for (int i = 0; i < authors.size(); i++) {
                authors.get(i).save();
            }
            for (int i = 0; i < keywords.size(); i++) {
                keywords.get(i).save();
            }
            resultSet = statement.executeQuery("select * from documents where title = '" + title + "'");
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        }
    }
    public void setVariablesKnowingTitle() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from documents where title = '"+title+"'");
        if(resultSet.next()){
            setPrice(resultSet.getInt("price"));
            setAmount(resultSet.getInt("amount"));
            setId(resultSet.getInt("id"));
            addAuthorsFromString(resultSet.getString("authors"));
            addKeywordsFromString(resultSet.getString("keywords"));
        }
    }
    //Id
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    //Price
    public void setPrice(int price) throws SQLException {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
    //Authors
    public ArrayList<Author> getAuthors() {
        return authors;
    }
    public void addAuthor(Author author) throws SQLException {
        authors.add(author);
        author.addDocument(this);
    }
    public void addAuthorsFromString(String s) throws SQLException {
        for(int i = 0; i<s.length(); i++){
            int j = i;
            String cur = "";
            while(j<s.length() && s.charAt(j)!=','){
                cur = cur.concat(String.valueOf(s.charAt(j)));
                j++;
            }
            i = j+1;
            Author author = new Author(connection, in);
            author.setName(cur);
            addAuthor(author);
        }
    }
    public void removeAuthor(Author author) throws SQLException {
        authors.remove(author);
        author.removeDocument(this);
    }
    public String getAuthorsAsString(){
        String s = "";
        for(int i = 0; i<authors.size(); i++){
            if(i<authors.size()-1) s = s.concat(authors.get(i).getName() + ", ");
            else s = s.concat(authors.get(i).getName());
        }
        return s;
    }
    //Keywords
    public ArrayList<Keyword> getKeywords() {
        return keywords;
    }
    public void addKeyword(Keyword keyword) throws SQLException {
        keywords.add(keyword);
        keyword.addDocument(this);
    }
    public void addKeywordsFromString(String s) throws SQLException {
        for(int i = 0; i<s.length(); i++){
            int j = i;
            String cur = "";
            while(j<s.length() && s.charAt(j)!=','){
                cur = cur.concat(String.valueOf(s.charAt(j)));
                j++;
            }
            i = j+1;
            Keyword keyword = new Keyword(connection, in);
            keyword.setKeyword(cur);
            addKeyword(keyword);
        }
    }
    public void removeKeyword(Keyword keyword) throws SQLException {
        keywords.remove(keyword);
        keyword.removeDocument(this);
    }
    public String getKeywordsAsString(){
        String s = "";
        for(int i = 0; i<keywords.size(); i++){
            if(i<keywords.size()-1)  s = s.concat(keywords.get(i).getKeyword() + ", ");
            else s = s.concat(keywords.get(i).getKeyword());
        }
        return s;
    }
    //Title
    public void setTitle(String title) throws SQLException {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    //Amount
    public void setAmount(int amount) throws SQLException {
        this.amount = amount;
    }
    public int getAmount() {
        return amount;
    }
    public void increaseAmount() throws SQLException {
        setAmount(amount + 1);
    }
}
