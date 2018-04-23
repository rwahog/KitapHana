package com.kitaphana.dao;

import com.kitaphana.Entities.Keyword;

import java.sql.SQLException;
import java.util.List;

public interface keywordDAO {

  Keyword findById(long id) throws SQLException;

  List<Keyword> findAll() throws SQLException;

  void insert(Keyword object) throws SQLException;

  void update(Keyword object) throws SQLException;

  void delete(long id) throws SQLException;
}
