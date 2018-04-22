package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.exceptions.DocumentNotFoundException;
import com.kitaphana.exceptions.OperationFailedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class documentDAOImpl implements documentDAO {

    Database db = Database.getInstance();
    commonDAOImpl commonDAO = new commonDAOImpl();

    private static final String FIND_BY_ID = "SELECT * FROM documents WHERE id=?";
    private static final String FIND_ALL = "SELECT id FROM documents";
    private static final String INSERT = "INSERT INTO documents (title, authors, keywords, price, amount, type, description) VALUES (?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE documents SET title=?, authors=?, keywords=?, price=?, amount=?, type=?, description=?, users=?, waiting_list=? WHERE id=?";
    private static final String UPDATE_INFO = "UPDATE documents SET title=?, authors=?, keywords=?, price=?, amount=?, type=?, description=? WHERE id=?";

    public long findLastId() {
        return commonDAO.findLastId("documents");
    }

    @Override
    public Document findById(long id) {
        Document document;
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                document = getVariables(rs);
            } else {
                throw new DocumentNotFoundException();
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return document;
    }

    @Override
    public ArrayList<Document> findAll() {
        ArrayList<Document> documents = new ArrayList<>();
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Document document = findById(rs.getLong("id"));
                documents.add(document);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return documents;
    }

    @Override
    public void insert(Document document) {
        try {
            PreparedStatement ps = db.connect().prepareStatement(INSERT);
            setVariables(document, ps);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    @Override
    public void update(Document document) {
        try {
            PreparedStatement ps = db.connect().prepareStatement(UPDATE);
            setVariables(document, ps);
            ps.setString(8, document.getUsersId());
            ps.setString(9, document.getAwaitersId());
            ps.setLong(10, document.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    public void updateInfo(Document document) {
        try {
            PreparedStatement ps = db.connect().prepareStatement(UPDATE_INFO);
            setVariables(document, ps);
            ps.setLong(8, document.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    @Override
    public void delete(long id) {
        commonDAO.delete(id, "documents", "id");
    }

    private Document getVariables(ResultSet rs) {
        Document document;
        try {
            document = new Document();
            document.setId(rs.getLong("id"));
            document.setTitle(rs.getString("title"));
            document.setAuthorsId(rs.getString("authors"));
            document.setKeywordsId(rs.getString("keywords"));
            document.setUsersId(rs.getString("users"));
            document.setPrice(rs.getInt("price"));
            document.setAmount(rs.getInt("amount"));
            document.setType(rs.getString("type"));
            document.setCover(rs.getString("document_cover"));
            document.setDescription(rs.getString("description"));
            document.setAwaitersId(rs.getString("waiting_list"));
            document.setAvailable(rs.getInt("available"));
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return document;
    }

    private void setVariables(Document document, PreparedStatement ps) {
        try {
            ps.setString(1, document.getTitle());
            ps.setString(2, document.getAuthorsId());
            ps.setString(3, document.getKeywordsId());
            ps.setInt(4, document.getPrice());
            ps.setInt(5, document.getAmount());
            ps.setString(6, document.getType());
            ps.setString(7, document.getDescription());
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }
}
