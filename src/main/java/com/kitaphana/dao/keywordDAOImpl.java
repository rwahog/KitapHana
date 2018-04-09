package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Keyword;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class keywordDAOImpl implements keywordDAO {

    Database db = Database.getInstance();
    commonDAOImpl commonDAO = new commonDAOImpl();
    private static final String FIND_BY_ID = "SELECT * FROM keywords WHERE id=?";
    private static final String FIND_BY_KEYWORD = "SELECT * FROM keywords WHERE keyword=?";
    private static final String FIND_ALL = "SELECT * FROM keywords";
    private static final String INSERT = "INSERT INTO keywords (keyword, documents) VALUES (?,?)";
    private static final String UPDATE = "UPDATE keywords SET keyword=?, documents=? WHERE id=?";

    public Keyword findByKeyword(String key) {
        Keyword keyword = null;

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_KEYWORD);
            ps.setString(1, key);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                keyword = new Keyword(key);
                keyword.setId(rs.getLong("id"));
                keyword.setDocumentsId(rs.getString("documents"));
                keyword.setKeyword(rs.getString("keyword"));
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return keyword;
    }

    @Override
    public Keyword findById(long id) {
        Keyword keyword = null;

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                keyword = new Keyword();
                keyword.setKeyword(rs.getString("keyword"));
                keyword.setId(rs.getLong("id"));
                keyword.setDocumentsId(rs.getString("documents"));
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return keyword;
    }

    @Override
    public ArrayList<Keyword> findAll() {
        ArrayList<Keyword> keywords = new ArrayList<>();

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Keyword keyword = new Keyword();

                keyword.setKeyword(rs.getString("keyword"));
                keyword.setDocumentsId(rs.getString("documents"));
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
    public void insert(Keyword object) {
        try {
            PreparedStatement ps = db.con.prepareStatement(INSERT);
            ps.setString(1, object.getKeyword());
            ps.setString(2, object.getDocumentsId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Keyword object) {
        try {
            PreparedStatement ps = db.con.prepareStatement(UPDATE);
            ps.setString(1, object.getKeyword());
            ps.setString(2, object.getDocumentsId());
            ps.setLong(3, object.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        commonDAO.delete(id, "keywords", "id");
    }
}
