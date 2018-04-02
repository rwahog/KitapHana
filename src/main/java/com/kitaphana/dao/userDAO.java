package com.kitaphana.dao;

import com.kitaphana.Entities.User;

import java.sql.SQLException;
import java.util.List;

public interface userDAO {

    User findById(long id) throws SQLException;
    List<User> findAll() throws SQLException;
    void insert(User object) throws SQLException;
    void update(User object) throws SQLException;
    void delete(long id) throws SQLException;

}
