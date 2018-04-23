package com.kitaphana.dao;

import com.kitaphana.Entities.Book;

import java.sql.SQLException;
import java.util.List;

public interface bookDAO {

  Book findById(long id) throws SQLException;

  List<Book> findAll() throws SQLException;

  void insert(Book object) throws SQLException;

  void update(Book object) throws SQLException;

  void delete(long id) throws SQLException;
}
