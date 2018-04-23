package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Keyword;
import com.kitaphana.exceptions.KeywordNotFoundException;
import com.kitaphana.exceptions.OperationFailedException;

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

  public long findLastId() {
    return commonDAO.findLastId("keywords");
  }

  public Keyword findByKeyword(String key) {
    Keyword keyword;
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_BY_KEYWORD);
      ps.setString(1, key);

      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        keyword = setVariables(rs);
      } else {
        throw new KeywordNotFoundException();
      }

      ps.close();
      rs.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }

    return keyword;
  }

  @Override
  public Keyword findById(long id) {
    Keyword keyword;
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID);
      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();

      if (rs.next()) {
        keyword = setVariables(rs);
      } else {
        throw new KeywordNotFoundException();
      }

      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }

    return keyword;
  }

  @Override
  public ArrayList<Keyword> findAll() {
    ArrayList<Keyword> keywords = new ArrayList<>();

    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_ALL);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        Keyword keyword = findById(rs.getLong("id"));
        keywords.add(keyword);
      }

      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }

    return keywords;
  }

  @Override
  public void insert(Keyword keyword) {
    try {
      PreparedStatement ps = db.connect().prepareStatement(INSERT);
      setVariables(keyword, ps);

      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  @Override
  public void update(Keyword keyword) {
    try {
      PreparedStatement ps = db.connect().prepareStatement(UPDATE);
      setVariables(keyword, ps);
      ps.setLong(3, keyword.getId());

      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  @Override
  public void delete(long id) {
    commonDAO.delete(id, "keywords", "id");
  }

  private Keyword setVariables(ResultSet rs) {
    Keyword keyword;
    try {
      keyword = new Keyword();
      keyword.setId(rs.getLong("id"));
      keyword.setKeyword(rs.getString("keyword"));
      keyword.setDocumentsId(rs.getString("documents"));
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return keyword;
  }

  private void setVariables(Keyword keyword, PreparedStatement ps) {
    try {
      ps.setString(1, keyword.getKeyword());
      ps.setString(2, keyword.getDocumentsId());
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }
}
