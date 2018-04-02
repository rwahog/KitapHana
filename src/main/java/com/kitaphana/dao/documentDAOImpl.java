package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class documentDAOImpl implements documentDAO {

    Database db = Database.getInstance();

    private static final String FIND_BY_ID = "SELECT * FROM documents WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM documents";
    private static final String INSERT = "INSERT INTO documents (title, authors, keywords, price, amount, type, description) VALUES (?,?,?,?,?,?,?)";
    private static final String DELETE = "DELETE FROM documents WHERE id=?";
    private static final String UPDATE = "UPDATE documents SET title=?, authors=?, keywords=?, users=?, price=?, amount=?, type=?, document_cover=?, description=? WHERE id=?";
    private static final String FIND_LAST_ID = "SELECT MAX(id) AS maxID FROM documents";

    public long findLastId() throws SQLException {
        long id = 0;
        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_LAST_ID);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                id = rs.getInt("maxID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public Document findById(long id) throws SQLException {
        Document document = null;
        try {

            PreparedStatement ps = db.con.prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                document = new Document();
                document.setTitle(rs.getString("title"));
                document.setAuthors(rs.getString("authors"));
                document.setKeywords(rs.getString("keywords"));
                document.setUsers(rs.getString("users"));
                document.setPrice(rs.getInt("price"));
                document.setAmount(rs.getInt("amount"));
                document.setType(rs.getString("type"));
                document.setCover(rs.getString("document_cover"));
                document.setDescription(rs.getString("description"));
                document.setAwaiters(rs.getString("waiting_list"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return document;
    }

    @Override
    public ArrayList<Document> findAll() throws SQLException {
        ArrayList<Document> documents = new ArrayList<>();

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Document document = new Document();

                document.setId(rs.getLong("id"));
                document.setTitle(rs.getString("title"));
                document.setAuthors(rs.getString("authors"));
                document.setKeywords(rs.getString("keywords"));
                document.setUsers(rs.getString("users"));
                document.setPrice(rs.getInt("price"));
                document.setAmount(rs.getInt("amount"));
                document.setType(rs.getString("type"));
                document.setCover(rs.getString("document_cover"));
                document.setDescription(rs.getString("description"));

                documents.add(document);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return documents;
    }

    @Override
    public void insert(Document object) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(INSERT);
            ps.setString(1, object.getTitle());
            ps.setString(2, object.getAuthors());
            ps.setString(3, object.getKeywords());
            ps.setInt(4, object.getPrice());
            ps.setInt(5, object.getAmount());
            ps.setString(6, object.getType());
            ps.setString(7, object.getDescription());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Document object) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(UPDATE);
            ps.setString(1, object.getTitle());
            ps.setString(2, object.getAuthors());
            ps.setString(3, object.getKeywords());
            ps.setString(4, object.getUsers());
            ps.setInt(5, object.getPrice());
            ps.setInt(6, object.getAmount());
            ps.setString(7, object.getType());
            ps.setString(8, object.getCover());
            ps.setString(9, object.getDescription());
            ps.setLong(10, object.getId());

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
