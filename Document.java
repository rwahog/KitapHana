import java.security.Key;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.security.Key;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Document {
    protected String title;
    protected ArrayList<Keyword> keywords;
    protected ArrayList<Author> authors;
    protected int price, amount, id;
    protected Statement statement;
    protected Connection connection;
    protected Scanner in;
    Document(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
        this.in = in;
        keywords = new ArrayList<Keyword>();
        authors = new ArrayList<Author>();
    }
    public void read() throws SQLException {
        GUI.print("Title: ");
        setTitle(GUI.read());setTitle(GUI.read());
        ResultSet resultSet = statement.executeQuery("select * from documents where title = '"+title+"'");
        if(resultSet.next()){
            GUI.print("This document already exists");
        }
        else {
            GUI.print("Authors: ");
            while (in.hasNext()) {
                String name = GUI.read();
                if (name.equals("end")) break;
                String surname = GUI.read();
                Author author = new Author(connection, in);
                author.setName(name);
                author.setSurname(surname);
                author.setVariablesKnowingNameSurname();
                addAuthor(author);
            }
            GUI.print("Keywords: ");
            while (in.hasNext()) {
                String word = GUI.read();
                if (word.equals("end")) break;
                Keyword keyword = new Keyword(connection, in);
                keyword.setKeyword(word);
                keyword.setVariablesKnowingKeyword();
                addKeyword(keyword);
            }
            GUI.print("Price: ");
            setPrice(Integer.parseInt(GUI.read()));
            GUI.print("Amount: ");
            setAmount(Integer.parseInt(GUI.read()));
        }
    }
    public void save() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from documents where title = '"+title+"'");
        for (int i = 0; i < authors.size(); i++) {
            authors.get(i).save();
        }
        for (int i = 0; i < keywords.size(); i++) {
            keywords.get(i).save();
        }
        if(resultSet.next()){
            statement.executeUpdate("update documents set title = '"+title+"', authors = '"+getAuthorsAsString()+"', keywords = '"+getKeywordsAsString()+"', price = '"+price+"', amount = '"+amount+"' where id = '"+id+"'");
        }
        else {
            statement.executeUpdate("insert into documents (title, authors, keywords, price, amount) values ('" + title + "','" + getAuthorsAsString() + "', '" + getKeywordsAsString() + "', '" + price + "', '" + amount + "' )");
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
            setAuthorsFromString(resultSet.getString("authors"));
            setKeywordsFromString(resultSet.getString("keywords"));
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
    public void setAuthorsFromString(String s) throws SQLException {
        if(s!=null) {
            for (int i = 0; i < s.length(); i++) {
                int j = i;
                String cur = "";
                while (j < s.length() && s.charAt(j) != ',') {
                    cur = cur.concat(String.valueOf(s.charAt(j)));
                    j++;
                }
                i = j + 1;
                Author author = new Author(connection, in);
                author.setName(cur);
                addAuthor(author);
            }
        }
    }
    public void removeAuthor(Author author) throws SQLException {
        authors.remove(author);
        author.removeDocument(this);
    }
    public String getAuthorsAsString(){
        String s = "";
        for(int i = 0; i<authors.size(); i++){
            if(i<authors.size()-1) s = s.concat(authors.get(i).getName() + " " + authors.get(i).getSurname() + ", ");
            else s = s.concat(authors.get(i).getName() +" "+authors.get(i).getSurname());
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
    public void setKeywordsFromString(String s) throws SQLException {
        if(s!=null) {
            for (int i = 0; i < s.length(); i++) {
                int j = i;
                String cur = "";
                while (j < s.length() && s.charAt(j) != ',') {
                    cur = cur.concat(String.valueOf(s.charAt(j)));
                    j++;
                }
                i = j + 1;
                Keyword keyword = new Keyword(connection, in);
                keyword.setKeyword(cur);
                addKeyword(keyword);
            }
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
    public void decreaseAmount() throws SQLException {
        setAmount(amount - 1);
    }
}