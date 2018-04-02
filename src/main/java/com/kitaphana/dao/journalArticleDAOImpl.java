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
    private static final String FIND_BY_ID = "SELECT * FROM ja WHERE id=?";
    private static final String FIND_BY_ID_DOCUMENT = "SELECT * FROM ja WHERE id_document=?";
    private static final String FIND_ALL = "SELECT * FROM ja";
    private static final String INSERT = "INSERT INTO ja (title, date, journal_name, editors, id_document) VALUES (?,?,?,?,?)";
    private static final String DELETE = "DELETE FROM ja WHERE id=?";
    private static final String UPDATE = "UPDATE authors SET title=?, date=?, journal_name=?, editors=? WHERE id=?";


    public JournalArticle findByIdDocument(long id) throws SQLException {
        JournalArticle journalArticle = null;
        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_ID_DOCUMENT);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                journalArticle = new JournalArticle();
                journalArticle.setTitle(rs.getString("title"));
                journalArticle.setDate(rs.getString("date"));
                journalArticle.setJournal_name(rs.getString("journal_name"));
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
    public JournalArticle findById(long id) throws SQLException {
        JournalArticle journalArticle = null;

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                journalArticle = new JournalArticle();
                journalArticle.setTitle(rs.getString("title"));
                journalArticle.setDate(rs.getString("date"));
                journalArticle.setJournal_name(rs.getString("journal_name"));
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
    public List<JournalArticle> findAll() throws SQLException {
        ArrayList<JournalArticle> books = new ArrayList<>();

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                JournalArticle journalArticle = new JournalArticle();

                journalArticle.setTitle(rs.getString("title"));
                journalArticle.setDate(rs.getString("date"));
                journalArticle.setJournal_name(rs.getString("journal_name"));
                journalArticle.setEditors(rs.getString("editors"));
                books.add(journalArticle);

            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public void insert(JournalArticle object) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(INSERT);
            ps.setString(1, object.getTitle());
            ps.setString(2, object.getDate());
            ps.setString(3, object.getJournal_name());
            ps.setString(4, object.getEditors());
            ps.setLong(5, object.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(JournalArticle object) throws SQLException {
        try{
            PreparedStatement ps = db.con.prepareStatement(UPDATE);
            ps.setString(1, object.getTitle());
            ps.setString(2, object.getDate());
            ps.setString(3, object.getJournal_name());
            ps.setString(4, object.getEditors());

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
