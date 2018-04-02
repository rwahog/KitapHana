package com.kitaphana.dao;

import com.kitaphana.Entities.Author;

import java.sql.SQLException;
import java.util.List;

public interface authorDAO {

    Author findById(long id) throws SQLException;
    List<Author> findAll() throws SQLException;
    void insert(Author object) throws SQLException;
    void update(Author object) throws SQLException;
    void delete(long id) throws SQLException;

}
