package com.kitaphana.dao;

public interface commonDAO {

  long findLastId(String table);

  void delete(long id, String table, String column);
}
