package com.kitaphana.dao;

import com.kitaphana.Entities.Document;

import java.sql.SQLException;
import java.util.List;

public interface documentDAO {

  Document findById(long id) throws SQLException;

  List<Document> findAll() throws SQLException;

  void insert(Document object) throws SQLException;

  void update(Document object) throws SQLException;

  void delete(long id) throws SQLException;
}
