package com.kitaphana.Service;

import com.kitaphana.Entities.Author;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.Keyword;
import com.kitaphana.dao.authorDAOImpl;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.keywordDAOImpl;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class DocumentSearch {
    protected Connection connection;
    protected Statement statement;
    protected Scanner in;
    protected ArrayList<Document> documents;
    protected ArrayList<Author> authors;
    protected ArrayList<Keyword> keywords;
    protected DBService dbService;
    protected Trie trie;
    DocumentSearch(Connection connection, Scanner in) throws SQLException {
        this.connection = connection;
        this.in = in;
        this.statement = connection.createStatement();
        initializeDocuments();
        initializeAuthors();
        initializeKeywords();
        trie = new Trie(connection);
    }
    public void initializeDocuments() throws SQLException {
        documentDAOImpl documentDAO = new documentDAOImpl();
        documents = documentDAO.findAll();
    }
    public void initializeAuthors() throws SQLException {
        authorDAOImpl authorDAO = new authorDAOImpl();
        authors = authorDAO.findAll();
    }
    public void initializeKeywords() throws SQLException {
        keywordDAOImpl keywordDAO = new keywordDAOImpl();
        keywords = keywordDAO.findAll();
    }
    public ArrayList<String> getPossibleQueries(String s){//Работает за |s|
        return trie.get(s);
    }
    public Document getDocumentByTitle(String title) throws SQLException {
        for(int i = 0; i<documents.size(); i++){
            if(documents.get(i).getTitle().equals(title)){
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
    public ArrayList<Document> getDocumentsByPossibleTitle(String possible_title, int amount, int lev_dist) throws SQLException {//Кол-во документов, которые нужно вернуть
        update(possible_title, 1);
        ArrayList<Document> [] sorted_documents = new ArrayList[lev_dist + 1];
        for(int i = 0; i<documents.size(); i++){
            int dist = levenshteinDistance(possible_title, documents.get(i).getTitle());
            if(dist <= lev_dist){
                sorted_documents[dist].add(documents.get(i));
            }
        }
        ArrayList<Document> ans = new ArrayList<>();
        for(int dist = 0; dist<= lev_dist && amount > 0; dist++){
            for(int i = 0; i <sorted_documents[dist].size() && amount>0; i++){
                ans.add(sorted_documents[dist].get(i));
                amount--;
            }
        }
        return ans;
    }
    public ArrayList<Author> getAuthorsByPossibleNameOrSurname(String possible_name_or_surname, int amount, int lev_dist) throws SQLException {
        update(possible_name_or_surname, 1);
        ArrayList<Author> [] sorted_documents = new ArrayList[lev_dist + 1];
        for(int i = 0; i<authors.size(); i++){
            int dist1 = levenshteinDistance(possible_name_or_surname, authors.get(i).getName());
            int dist2 = levenshteinDistance(possible_name_or_surname, authors.get(i).getSurname());

            if(dist1 <= lev_dist || dist2 <= lev_dist){
                sorted_documents[Math.min(dist1, dist2)].add(authors.get(i));
            }
        }
        ArrayList<Author> ans = new ArrayList<>();
        for(int dist = 0; dist <= lev_dist && amount > 0; dist++){
            for(int i = 0; i <sorted_documents[dist].size() && amount>0; i++){
                ans.add(sorted_documents[dist].get(i));
                amount--;
            }
        }
        return ans;
    }
    public ArrayList<Keyword> getKeywordsByPossibleKeyword(String possible_keyword, int amount, int lev_dist) throws SQLException {
        update(possible_keyword, 1);
        ArrayList<Keyword> [] sorted_documents = new ArrayList[lev_dist + 1];
        for(int i = 0; i<keywords.size(); i++){
            int dist = levenshteinDistance(possible_keyword, authors.get(i).getSurname());
            if(dist <= lev_dist){
                sorted_documents[dist].add(keywords.get(i));
            }
        }
        ArrayList<Keyword> ans = new ArrayList<>();
        for(int dist = 0; dist<=lev_dist && amount > 0; dist++){
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
            if(authors.get(i).getName().equals(name) || authors.get(i).getSurname().equals(surname)){
                for(int j = 0; j<authors.get(i).getDocuments().size(); j++){
                    ans.add(authors.get(i).getDocuments().get(j));
                }
            }
        }
        return ans;
    }
    public ArrayList<Document> getDocumentsByKeyword(String keyword) throws SQLException {
        ArrayList<Document> ans = new ArrayList<>();
        for(int i = 0; i<keywords.size(); i++){
            if(keywords.get(i).getKeyword().equals(keyword)){
                for(int j = 0; j<keywords.get(i).getDocuments().size(); j++){
                    ans.add(keywords.get(i).getDocuments().get(j));
                }
            }
        }
        return ans;
    }
    public int getNumberOfDocuments() throws SQLException {
        int ans = 0;
        for(int i = 0; i<documents.size(); i++){
            ans += documents.get(i).getAmount();
        }
        return ans;
    }
    public int getNumberOfAVMaterials() throws SQLException {
        int ans = 0;
        for(int i = 0; i<documents.size(); i++){
            if(documents.get(i).getType().equals("avmaterial")) ans++;
        }
        return ans;
    }
    public int getNumberOfBooks() throws SQLException {
        int ans = 0;
        for(int i = 0; i<documents.size(); i++){
            if(documents.get(i).getType().equals("book")) ans++;
        }
        return ans;
    }

}
