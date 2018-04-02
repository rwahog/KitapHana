package com.kitaphana.dao;

import com.kitaphana.Entities.JournalArticle;

import java.sql.SQLException;
import java.util.List;

public interface journalArticleDAO {

    JournalArticle findById(long id) throws SQLException;
    List<JournalArticle> findAll() throws SQLException;
    void insert(JournalArticle object) throws SQLException;
    void update(JournalArticle object) throws SQLException;
    void delete(long id) throws SQLException;
}
