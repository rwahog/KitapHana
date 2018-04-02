package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class authorDAOImpl implements authorDAO {

    Database db = Database.getInstance();
    private static final String FIND_BY_ID = "SELECT * FROM authors WHERE id=?";
    private static final String FIND_BY_NAME_AND_SURNAME = "SELECT * FROM authors WHERE name=? AND surname=?";
    private static final String FIND_ALL = "SELECT * FROM authors";
    private static final String INSERT = "INSERT INTO authors (name, surname, documents) VALUES (?,?,?)";
    private static final String DELETE = "DELETE FROM authors WHERE id=?";
    private static final String UPDATE = "UPDATE authors SET name=?, surname=?, documents=? WHERE id=?";

    public Author findByNameAndSurname(String name, String surname) throws SQLException {
        Author author = null;

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_NAME_AND_SURNAME);
            ps.setString(1, name);
            ps.setString(2, surname);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                author = new Author();
                author.setName(rs.getString("name"));
                author.setSurname(rs.getString("surname"));
                author.setId_documents(rs.getString("documents"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return author;
    }

    @Override
    public Author findById(long id) throws SQLException {
        Author author = null;

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                author = new Author();
                author.setName(rs.getString("name"));
                author.setSurname(rs.getString("surname"));
                author.setId_documents(rs.getString("documents"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return author;
    }

    @Override
    public ArrayList<Author> findAll() throws SQLException {
        ArrayList<Author> authors = new ArrayList<>();

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Author author = new Author();

                author.setId(rs.getLong("id"));
                author.setName(rs.getString("name"));
                author.setSurname(rs.getString("surname"));
                author.setId_documents(rs.getString("documents"));

                authors.add(author);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }

    @Override
    public void insert(Author object) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(INSERT);
            ps.setString(1, object.getName());
            ps.setString(2, object.getSurname());
            ps.setString(3, object.getId_documents());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Author object) throws SQLException {
        try{
            PreparedStatement ps = db.con.prepareStatement(UPDATE);
            ps.setString(1, object.getName());
            ps.setString(2, object.getSurname());
            ps.setString(3, object.getId_documents());
            ps.setLong(4, object.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(DELETE);
            ps.setLong(1, id);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
