package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.JournalArticle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class journalArticleDAOImpl implements journalArticleDAO {

    Database db = Database.getInstance();
    commonDAOImpl commonDAO = new commonDAOImpl();
    private static final String FIND_BY_ID = "SELECT * FROM ja WHERE id=?";
    private static final String FIND_ALL = "SELECT * FROM ja";
    private static final String INSERT = "INSERT INTO ja (title, date, journal_name, editors, document_id) VALUES (?,?,?,?,?)";
    private static final String UPDATE = "UPDATE authors SET title=?, date=?, journal_name=?, editors=? WHERE document_id=?";


    public long findLastId() {
        return commonDAO.findLastId("ja");
    }

    public JournalArticle findByDocumentId(String id) {
        return (JournalArticle) commonDAO.findByDocumentId(id, "ja");
    }

    @Override
    public JournalArticle findById(long id) {
        JournalArticle journalArticle = null;

        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                journalArticle = new JournalArticle();
                journalArticle.setTitle(rs.getString("title"));
                journalArticle.setDate(rs.getString("date"));
                journalArticle.setJournalName(rs.getString("journal_name"));
                journalArticle.setEditors(rs.getString("editors"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return journalArticle;
    }

    @Override
    public List<JournalArticle> findAll() {
        ArrayList<JournalArticle> journalArticles = new ArrayList<>();

        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                JournalArticle journalArticle = new JournalArticle();

                journalArticle.setTitle(rs.getString("title"));
                journalArticle.setDate(rs.getString("date"));
                journalArticle.setJournalName(rs.getString("journal_name"));
                journalArticle.setEditors(rs.getString("editors"));
                journalArticles.add(journalArticle);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return journalArticles;
    }

    @Override
    public void insert(JournalArticle object) {
        try {
            PreparedStatement ps = db.connect().prepareStatement(INSERT);
            ps.setString(1, object.getTitle());
            ps.setString(2, object.getDate());
            ps.setString(3, object.getJournalName());
            ps.setString(4, object.getEditors());
            ps.setLong(5, object.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(JournalArticle object) {
        try{
            PreparedStatement ps = db.connect().prepareStatement(UPDATE);
            ps.setString(1, object.getTitle());
            ps.setString(2, object.getDate());
            ps.setString(3, object.getJournalName());
            ps.setString(4, object.getEditors());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        commonDAO.delete(id, "ja", "id");
    }
}
