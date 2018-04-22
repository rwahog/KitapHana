package com.kitaphana.dao;

import com.kitaphana.Entities.Patron;

import java.util.List;

public interface patronDAO {

    Patron findById(long id);
    List<Patron> findAll();
    void insert(Patron patron);
    void update(Patron patron);
    void delete(long id);

}
