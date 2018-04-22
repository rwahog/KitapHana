package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Author;
import com.kitaphana.exceptions.AuthorNotFoundException;
import com.kitaphana.exceptions.OperationFailedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class authorDAOImpl implements authorDAO {

    Database db = Database.getInstance();
    commonDAOImpl commonDAO = new commonDAOImpl();
    private static final String FIND_BY_ID = "SELECT * FROM authors WHERE id=?";
    private static final String FIND_BY_NAME_AND_SURNAME = "SELECT * FROM authors WHERE name=? AND surname=?";
    private static final String FIND_ALL = "SELECT id FROM authors";
    private static final String INSERT = "INSERT INTO authors (name, surname, documents) VALUES (?,?,?)";
    private static final String UPDATE = "UPDATE authors SET name=?, surname=?, documents=? WHERE id=?";

    public long findLastId() {
        return commonDAO.findLastId("authors");
    }

    public Author findByNameAndSurname(String name, String surname) {
        Author author;
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_BY_NAME_AND_SURNAME);
            ps.setString(1, name);
            ps.setString(2, surname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                author = getVariables(rs);
            } else {
                throw new AuthorNotFoundException();
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }

        return author;
    }

    @Override
    public Author findById(long id) {
        Author author;

        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                author = getVariables(rs);
            } else {
                throw new AuthorNotFoundException();
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }

        return author;
    }

    @Override
    public ArrayList<Author> findAll() {
        ArrayList<Author> authors = new ArrayList<>();
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Author author = findById(rs.getLong("id"));
                authors.add(author);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return authors;
    }

    @Override
    public void insert(Author author) {
        try {
            PreparedStatement ps = db.connect().prepareStatement(INSERT);
            setVariables(author, ps);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    @Override
    public void update(Author author) {
        try{
            PreparedStatement ps = db.connect().prepareStatement(UPDATE);
            setVariables(author, ps);
            ps.setLong(4, author.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    @Override
    public void delete(long id) {
        commonDAO.delete(id, "authors", "id");
    }

    private Author getVariables(ResultSet rs) {
        Author author;
        try {
            author = new Author();
            author.setId(rs.getLong("id"));
            author.setName(rs.getString("name"));
            author.setSurname(rs.getString("surname"));
            author.setDocumentsId(rs.getString("documents"));
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return author;
    }

    private void setVariables(Author author, PreparedStatement ps) {
        try {
            ps.setString(1, author.getName());
            ps.setString(2, author.getSurname());
            ps.setString(3, author.getDocumentsId());
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }
}
