package com.kitaphana.Service;

import com.kitaphana.Entities.Author;
import com.kitaphana.dao.authorDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorService {

    authorDAOImpl authorDAO = new authorDAOImpl();

    public void deleteAuthor(long id) throws SQLException {
        authorDAO.delete(id);
    }

    public void updateAuthor(Author author) throws SQLException {
        authorDAO.update(author);
    }

    public void addAuthor(Author author) throws SQLException {
        authorDAO.insert(author);
    }

    public Author findAuthorbyId(long id) throws SQLException {
        return authorDAO.findById(id);
    }

    public ArrayList<Author> findAllAuthors() throws SQLException {
        return authorDAO.findAll();
    }
}
