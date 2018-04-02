package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Author;
import com.kitaphana.Entities.Book;
import com.kitaphana.Entities.JournalArticle;
import com.kitaphana.Entities.Keyword;
import com.kitaphana.dao.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddDocumentService {

    Database db = Database.getInstance();
    userDAOImpl userDAO = new userDAOImpl();
    documentDAOImpl documentDAO = new documentDAOImpl();
    bookDAOImpl bookDAO = new bookDAOImpl();
    authorDAOImpl authorDAO = new authorDAOImpl();
    journalArticleDAOImpl journalArticleDAO = new journalArticleDAOImpl();
    keywordDAOImpl keywordDAO = new keywordDAOImpl();

    public boolean checkUnique(String title, String authors, String type) {
        boolean unique = false;
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT * FROM documents WHERE title = '" + title + "' AND authors = '" + authors + "' AND type = '" + type +"';");
            if (!rs.next()) {
                unique = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unique;
    }

    public void saveKeywords(ArrayList<String> keywords, long id) throws SQLException {
        for (String keyword: keywords) {
            Keyword key = keywordDAO.findByKeyword(keyword);
            if (key == null) {
                Keyword keyword1 = new Keyword(keyword);
                keyword1.setId_documents(String.valueOf(id));
                keywordDAO.insert(keyword1);
            } else {
                key.setId_documents(key.getId_documents().concat(", " + id));
                keywordDAO.update(key);
            }
        }
    }

    public void saveAuthors(ArrayList<String> authors, long id) throws SQLException {
        for (String author: authors) {
            int i = 0;
            String name = "";
            String surname;
            while (author.charAt(i) != ' ') {
                name = name + author.charAt(i);
                i = i + 1;
            }
            surname = author.substring(i + 1);
            Author author1 = authorDAO.findByNameAndSurname(name, surname);
            if (author1 == null) {
                Author author_obj = new Author(name, surname);
                author_obj.setId_documents(String.valueOf(id));
                authorDAO.insert(author_obj);
            } else {
                author1.setId_documents(author1.getId_documents().concat(", " + id));
                authorDAO.update(author1);
            }
        }
    }

    public void saveBook(Book book) throws SQLException {
        documentDAO.insert(book);
        book.setId_document(documentDAO.findLastId());
        bookDAO.insert(book);
        ArrayList<String> authors_list = book.getAuthorsAsArray();
        ArrayList<String> keywords_list = book.getKeywordsAsArray();
        saveAuthors(authors_list, documentDAO.findLastId());
        saveKeywords(keywords_list, documentDAO.findLastId());
    }

    public void saveJournalArticle(JournalArticle article) throws SQLException {
        documentDAO.insert(article);
        article.setDocument_id(documentDAO.findLastId());
        journalArticleDAO.insert(article);
        ArrayList<String> authors_list = article.getAuthorsAsArray();
        ArrayList<String> keywords_list = article.getKeywordsAsArray();
        saveAuthors(authors_list, documentDAO.findLastId());
        saveKeywords(keywords_list, documentDAO.findLastId());
    }
}
