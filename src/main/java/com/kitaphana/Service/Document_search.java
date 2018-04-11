
import javax.print.Doc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Document_search {
    protected Connection connection;
    protected Statement statement;
    protected Scanner in;
    protected ArrayList<Document> documents;
    protected ArrayList<Author> authors;
    protected ArrayList<Keyword> keywords;
    protected Trie trie;
    Document_search(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.in = in;
        this.statement = connection.createStatement();
        initializeDocuments();
        initializeAuthors();
        initializeKeywords();
        trie = new Trie(connection);
    }
    public void initializeDocuments() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from documents");
        documents = new ArrayList<>();
        while(resultSet.next()){
            Document document = new Document(connection, in);
            document.setVariablesKnowingId(resultSet.getInt("id"));
            documents.add(document);
            update(document.title, 0);
        }
    }
    public void initializeAuthors() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from authors");
        authors = new ArrayList<>();
        while(resultSet.next()){
            Author author = new Author(connection, in);
            author.setVariablesKnowingNameSurname(resultSet.getString("name"), resultSet.getString("surname"));
            authors.add(author);
            update(author.name, 0);
            update(author.surname, 0);
        }
    }
    public void initializeKeywords() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from keywords");
        keywords = new ArrayList<>();
        while(resultSet.next()) {
            Keyword keyword = new Keyword(connection, in);
            keyword.setVariablesKnowingKeyword(resultSet.getString("keyword"));
            keywords.add(keyword);
            update(keyword.keyword, 0);
        }
    }
    public ArrayList<String> getPossibleQueries(String s){//Работает за |s|
        return trie.get(s);
    }
    public Document getDocumentByTitle(String title) throws SQLException {
        for(int i = 0; i<documents.size(); i++){
            if(documents.get(i).title.equals(title)){
                return documents.get(i);
            }
        }
        return null;
    }
    public void update(String s, int k) throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from search where query = '"+s+"'");
        Date date = new Date();
        if(resultSet.next()){
            statement.executeUpdate("update search set amount = '"+resultSet.getInt("amount")+k+"', last_search = '"+date.getTime()+"' where query = '"+s+"'");
        }
        else{
            statement.executeUpdate("insert into search (query, amount, last_search) values('"+s+"', '"+k+"', '"+date.getTime()+"')");
        }
    }
    public ArrayList<Document> getDocumentsByPossibleTitle(String possible_title, int amount) throws SQLException {//Кол-во документов, которые нужно вернуть
        update(possible_title, 1);
        ArrayList<Document> [] sorted_documents = new ArrayList[100];
        for(int i = 0; i<documents.size(); i++){
            int dist = levenshteinDistance(possible_title, documents.get(i).title);
            if(dist < 100){
                sorted_documents[dist].add(documents.get(i));
            }
        }
        ArrayList<Document> ans = new ArrayList<>();
        for(int dist = 0; dist<100 && amount > 0; dist++){
            for(int i = 0; i <sorted_documents[dist].size() && amount>0; i++){
                ans.add(sorted_documents[dist].get(i));
                amount--;
            }
        }
        return ans;
    }
    public ArrayList<Author> getAuthorsByPossibleNameOrSurname(String possible_name_or_surname, int amount) throws SQLException {
        update(possible_name_or_surname, 1);
        ArrayList<Author> [] sorted_documents = new ArrayList[100];
        for(int i = 0; i<authors.size(); i++){
            int dist1 = levenshteinDistance(possible_name_or_surname, authors.get(i).name);
            int dist2 = levenshteinDistance(possible_name_or_surname, authors.get(i).surname);

            if(dist1 < 100 || dist2 < 100){
                sorted_documents[Math.min(dist1, dist2)].add(authors.get(i));
            }
        }
        ArrayList<Author> ans = new ArrayList<>();
        for(int dist = 0; dist<100 && amount > 0; dist++){
            for(int i = 0; i <sorted_documents[dist].size() && amount>0; i++){
                ans.add(sorted_documents[dist].get(i));
                amount--;
            }
        }
        return ans;
    }
    public ArrayList<Keyword> getKeywordsByPossibleKeyword(String possible_keyword, int amount) throws SQLException {
        update(possible_keyword, 1);
        ArrayList<Keyword> [] sorted_documents = new ArrayList[100];
        for(int i = 0; i<keywords.size(); i++){
            int dist = levenshteinDistance(possible_keyword, authors.get(i).surname);
            if(dist < 100){
                sorted_documents[dist].add(keywords.get(i));
            }
        }
        ArrayList<Keyword> ans = new ArrayList<>();
        for(int dist = 0; dist<100 && amount > 0; dist++){
            for(int i = 0; i <sorted_documents[dist].size() && amount>0; i++){
                ans.add(sorted_documents[dist].get(i));
                amount--;
            }
        }
        return ans;
    }
    private int levenshteinDistance(String a, String b){
        int dp[][] = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    int substitutionCost = 0;
                    if(!(a.charAt(i) == b.charAt(j))) substitutionCost = 1;
                    dp[i][j] = Math.min(dp[i - 1][j - 1] + substitutionCost, Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1));
                }
            }
        }
        return dp[a.length()][b.length()];
    }
    public ArrayList<Document> getDocumentsByAuthorsNameSurname(String name, String surname) throws SQLException {
        ArrayList<Document> ans = new ArrayList<>();
        for(int i = 0; i<authors.size(); i++){
            if(authors.get(i).name.equals(name) || authors.get(i).surname.equals(surname)){
                for(int j = 0; j<authors.get(i).documents.size(); j++){
                    ans.add(authors.get(i).documents.get(j));
                }
            }
        }
        return ans;
    }
    public ArrayList<Document> getDocumentsByKeyword(String keyword) throws SQLException {
        for(int i = 0; i<keywords.size(); i++){
            if(keywords.get(i).keyword.equals(keyword)){
                ArrayList<Document> ans = new ArrayList<>();
                for(int j = 0; j<keywords.get(i).documents.size(); j++){
                    ans.add(keywords.get(i).documents.get(j));
                }
                return ans;
            }
        }
        return null;
    }
    public int getNumberOfDocuments() throws SQLException {
        int ans = 0;
        for(int i = 0; i<documents.size(); i++){
            ans += documents.get(i).amount;
        }
        return ans;
    }
    public int getNumberOfAVMaterials() throws SQLException {
        int ans = 0;
        for(int i = 0; i<documents.size(); i++){
            if(documents.get(i).type.equals("avmaterial")) ans++;
        }
        return ans;
    }
    public int getNumberOfBooks() throws SQLException {
        int ans = 0;
        for(int i = 0; i<documents.size(); i++){
            if(documents.get(i).type.equals("book")) ans++;
        }
        return ans;
    }

}
