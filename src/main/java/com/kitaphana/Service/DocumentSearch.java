package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Author;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.Keyword;
import com.kitaphana.dao.authorDAOImpl;
import com.kitaphana.dao.keywordDAOImpl;
import com.kitaphana.exceptions.OperationFailedException;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        for (int i = 0; i < authors.size(); i++) {
            update(authors.get(i).getName(), 0);
            update(authors.get(i).getSurname(), 0);
        }
    }

    public void initializeKeywords() {
        keywords = documentService.findAllKeywords();
        for (int i = 0; i < keywords.size(); i++) {
            update(keywords.get(i).getKeyword(), 0);
        }

    }

    public ArrayList<String> getPossibleQueries(String s) {//Работает за |s|
        return trie.get(s);
    }

    public Document getDocumentByTitle(String title) {
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getTitle().equals(title)) {
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
            } else {
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
        possible_title = possible_title.toLowerCase();
        ArrayList<Document>[] sorted_documents = new ArrayList[lev_dist + 1];
        for (int i = 0; i < sorted_documents.length; i++) {
            sorted_documents[i] = new ArrayList<>();
        }
        for (int i = 0; i < documents.size(); i++) {
            int dist = Integer.MAX_VALUE;
            String title = new String(documents.get(i).getTitle());
            title = title.toLowerCase();
            for (int j = 0; j < title.length(); j++) {
                for(int l = j; l<title.length(); l++){
                    String s = new String(title.substring(j, l+1));
                    dist = Math.min(dist, levenshteinDistance(s, possible_title));
                }
            }

//            System.out.println(title);
//            System.out.println(dist);
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

    public ArrayList<Document> getDocumentsByPossibleNameOrSurname(String possible_name_or_surname, int amount, int lev_dist)  {
        ArrayList<Document> ans = ans = new ArrayList<>();
        ArrayList<Author> authors = getAuthorsByPossibleNameOrSurname(possible_name_or_surname, amount, lev_dist);
        for(int i = 0; i<authors.size(); i++){
            for(int j = 0; j<authors.get(i).getDocuments().size(); j++){
                if(!ans.contains(authors.get(i).getDocuments().get(j))){
                    ans.add(authors.get(i).getDocuments().get(j));
                }
            }
        }
        return ans;
    }

    public ArrayList<Author> getAuthorsByPossibleNameOrSurname(String possible_name_or_surname, int amount, int lev_dist)  {
        update(possible_name_or_surname, 1);
        possible_name_or_surname = possible_name_or_surname.toLowerCase();
        ArrayList<Author>[] sorted_documents = new ArrayList[lev_dist + 1];
        for (int i = 0; i < sorted_documents.length; i++) {
          sorted_documents[i] = new ArrayList<>();
        }
        for (int i = 0; i < authors.size(); i++) {
            String name = new String(authors.get(i).getName());
            name = name.toLowerCase();
            int dist = Integer.MAX_VALUE;
            for (int j = 0; j < name.length(); j++) {
                for(int l = j; l<name.length(); l++){
                    String s = new String(name.substring(j, l+1));
                    dist = Math.min(dist, levenshteinDistance(s, possible_name_or_surname));
                }
            }

            String surname = new String(authors.get(i).getSurname());
            surname = surname.toLowerCase();
            for (int j = 0; j < surname.length(); j++) {
                for(int l = j; l<surname.length(); l++){
                    String s = new String(surname.substring(j, l+1));
                    dist = Math.min(dist, levenshteinDistance(s, possible_name_or_surname));
                }
            }

//            System.out.println(name);
//            System.out.println(surname);
//            System.out.println(dist);
            if (dist <= lev_dist) {
                sorted_documents[dist].add(authors.get(i));
            }
        }
        ArrayList<Author> ans = new ArrayList<>();
        for (int dist = 0; dist <= lev_dist && amount > 0; dist++) {
            for (int i = 0; i < sorted_documents[dist].size() && amount > 0; i++) {
                ans.add(sorted_documents[dist].get(i));
                amount--;
            }
        }
        return ans;
    }
    public ArrayList<Document> getDocumentsByPossibleKeyword(String possible_keyword, int amount, int lev_dist){
        ArrayList<Document> ans = ans = new ArrayList<>();
        ArrayList<Keyword> keywords = getKeywordsByPossibleKeyword(possible_keyword, amount, lev_dist);
        for(int i = 0; i<keywords.size(); i++){
            for(int j = 0; j<keywords.get(i).getDocuments().size(); j++){
                if(!ans.contains(keywords.get(i).getDocuments().get(j))){
                    ans.add(keywords.get(i).getDocuments().get(j));
                }
            }
        }
        return ans;
    }
    public ArrayList<Keyword> getKeywordsByPossibleKeyword(String possible_keyword, int amount, int lev_dist)  {
        update(possible_keyword, 1);
        possible_keyword = possible_keyword.toLowerCase();
        ArrayList<Keyword>[] sorted_documents = new ArrayList[lev_dist + 1];
        for (int i = 0; i < sorted_documents.length; i++) {
          sorted_documents[i] = new ArrayList<>();
        }
        for (int i = 0; i < keywords.size(); i++) {
            String keyword = new String(keywords.get(i).getKeyword());
            keyword = keyword.toLowerCase();
            int dist = Integer.MAX_VALUE;
            for (int j = 0; j < keyword.length(); j++) {
                for(int l = j; l<keyword.length(); l++){
                    String s = new String(keyword.substring(j, l+1));
                    dist = Math.min(dist, levenshteinDistance(s, possible_keyword));
                }
            }
//            System.out.println(keyword);
//            System.out.println(dist);
            if (dist <= lev_dist) {
                sorted_documents[dist].add(keywords.get(i));
            }
        }
        ArrayList<Keyword> ans = new ArrayList<>();
        for (int dist = 0; dist <= lev_dist && amount > 0; dist++) {
            for (int i = 0; i < sorted_documents[dist].size() && amount > 0; i++) {
                ans.add(sorted_documents[dist].get(i));
                amount--;
            }
        }
        return ans;
    }

    private int levenshteinDistance(String a, String b) {
        a.toLowerCase();
        b.toLowerCase();
        int dp[][] = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
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

    public int getNumberOfDocuments() throws SQLException {
        int ans = 0;
        for (int i = 0; i < documents.size(); i++) {
            ans += documents.get(i).getAmount();
        }
        return ans;
    }

    public int getNumberOfAVMaterials() throws SQLException {
        int ans = 0;
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getType().equals("Audio/Video material")) ans++;
        }
        return ans;
    }

    public int getNumberOfBooks() throws SQLException {
        int ans = 0;
        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getType().equals("Book")) ans++;
        }
        return ans;
    }

}
