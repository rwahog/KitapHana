package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Author;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.Keyword;
import com.kitaphana.dao.authorDAOImpl;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.keywordDAOImpl;
import com.kitaphana.exceptions.OperationFailedException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DocumentSearch {
    protected ArrayList<Document> documents;
    protected ArrayList<Author> authors;
    protected ArrayList<Keyword> keywords;
    protected DBService dbService;
    protected Trie trie;
    DocumentService documentService = new DocumentService();
    keywordDAOImpl keywordDAO = new keywordDAOImpl();
    authorDAOImpl authorDAO = new authorDAOImpl();
    Database db = Database.getInstance();
    private static final String init = "SELECT * FROM search WHERE query=?";
    private static final String upd = "UPDATE search SET amount =?, last_search=? WHERE query=?";
    private static final String insert = "INSERT INTO search (query, amount, last_search) VALUES(?,?,?)";
    public DocumentSearch() {
        initializeDocuments();
        initializeAuthors();
        initializeKeywords();
        trie = new Trie();
    }
    public void initializeDocuments() {
        documents = documentService.findAll();
        for (int i = 0; i < documents.size(); i++) {
            update(documents.get(i).getTitle(), 0);
        }
    }
    public void initializeAuthors() {
        authors = authorDAO.findAll();
        for (int i = 0; i<authors.size(); i++) {
            update(authors.get(i).getName(), 0);
            update(authors.get(i).getSurname(), 0);
        }
    }
    public void initializeKeywords() {
        keywords = keywordDAO.findAll();
        for (int i = 0; i<keywords.size(); i++) {
            update(keywords.get(i).getKeyword(), 0);
        }

    }
    public ArrayList<String> getPossibleQueries(String s){//Работает за |s|
        return trie.get(s);
    }
    public Document getDocumentByTitle(String title) {
        for(int i = 0; i<documents.size(); i++){
            if(documents.get(i).getTitle().equals(title)){
                return documents.get(i);
            }
        }
        return null;
    }
    public void update(String s, int k) {
        try {
            PreparedStatement ps = db.connect().prepareStatement(init);
            ps.setString(1, s);
            ResultSet resultSet = ps.executeQuery();
            Date date = new Date();
            if (resultSet.next()) {
                ps = db.connect().prepareStatement(upd);
                ps.setInt(1, resultSet.getInt("amount") + k);
                ps.setLong(2, date.getTime());
                ps.setString(3, s);
                ps.executeUpdate();
            }
            else {
                ps = db.connect().prepareStatement(insert);
                ps.setString(1, s);
                ps.setInt(2, k);
                ps.setLong(3, date.getTime());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }
    public ArrayList<Document> getDocumentsByPossibleTitle(String possible_title, int amount, int lev_dist) {//Кол-во документов, которые нужно вернуть
        update(possible_title, 1);
        ArrayList<Document>[] sorted_documents = new ArrayList[lev_dist + 1];
        for (int i = 0; i < sorted_documents.length; i++) {
            sorted_documents[i] = new ArrayList<>();
        }
        for(int i = 0; i < documents.size(); i++){
            int dist = Integer.MAX_VALUE;
            String s ="";
            for(int j = 0; j<documents.get(i).getTitle().length(); j++) {
                if (documents.get(i).getTitle().charAt(j) == ' ') {
                    dist = Math.min(dist, levenshteinDistance(s, possible_title));
                    s = "";
                } else {
                    s.concat(String.valueOf(documents.get(i).getTitle().charAt(j)));
                }
            }
            dist = Math.min(dist, levenshteinDistance(s, possible_title));
            if (dist <= lev_dist) {
                sorted_documents[dist].add(documents.get(i));
            }
        }
        ArrayList<Document> ans = new ArrayList<>();
        for (int dist = 0; dist <= lev_dist && amount > 0; dist++) {
            for (int i = 0; i < sorted_documents[dist].size() && amount > 0; i++) {
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
                    dp[i][j] = min(dp[i - 1][j - 1]
                                    + costOfSubstitution(a.charAt(i - 1), b.charAt(j - 1)),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1);
                }
            }
        }
        return dp[a.length()][b.length()];
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers)
                .min().orElse(Integer.MAX_VALUE);
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
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
            if(documents.get(i).getType().equals("Audio/Video material")) ans++;
        }
        return ans;
    }
    public int getNumberOfBooks() throws SQLException {
        int ans = 0;
        for(int i = 0; i<documents.size(); i++){
            if(documents.get(i).getType().equals("Book")) ans++;
        }
        return ans;
    }

}
