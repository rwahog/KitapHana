package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Keyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class keywordDAOImpl implements keywordDAO {

    Database db = Database.getInstance();
    private static final String FIND_BY_ID = "SELECT * FROM keywords WHERE id=?";
    private static final String FIND_BY_KEYWORD = "SELECT * FROM keywords WHERE keyword=?";
    private static final String FIND_ALL = "SELECT * FROM keywords";
    private static final String INSERT = "INSERT INTO keywords (keyword, documents) VALUES (?,?)";
    private static final String DELETE = "DELETE FROM keywords WHERE id=?";
    private static final String UPDATE = "UPDATE keywords SET keyword=?, documents=? WHERE id=?";

    public Keyword findByKeyword(String key) throws SQLException {
        Keyword keyword = null;

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_KEYWORD);
            ps.setString(1, key);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                keyword = new Keyword(key);
                keyword.setId(rs.getLong("id"));
                keyword.setId_documents(rs.getString("documents"));
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return keyword;
    }

    @Override
    public Keyword findById(long id) throws SQLException {
        Keyword keyword = null;

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                keyword = new Keyword();
                keyword.setKeyword(rs.getString("keyword"));
                keyword.setId_documents(rs.getString("documents"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return keyword;
    }

    @Override
    public ArrayList<Keyword> findAll() throws SQLException {
        ArrayList<Keyword> keywords = new ArrayList<>();

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Keyword keyword = new Keyword();

                keyword.setKeyword(rs.getString("keyword"));
                keyword.setId_documents(rs.getString("documents"));
                keyword.setId(rs.getInt("id"));

                keywords.add(keyword);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return keywords;
    }

    @Override
    public void insert(Keyword object) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(INSERT);
            ps.setString(1, object.getKeyword());
            ps.setString(2, object.getId_documents());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Keyword object) throws SQLException {
        try {
            PreparedStatement ps = db.con.prepareStatement(UPDATE);
            ps.setString(1, object.getKeyword());
            ps.setString(2, object.getId_documents());
            ps.setLong(3, object.getId());

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
