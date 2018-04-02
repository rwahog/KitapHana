package com.kitaphana.dao;

import com.kitaphana.Entities.Address;

import java.sql.SQLException;
import java.util.List;

public interface addressDAO {

    Address findById(long id) throws SQLException;
    List<Address> findAll() throws SQLException;
    void insert(Address object) throws SQLException;
    void update(Address object) throws SQLException;
    void delete(long id) throws SQLException;
}
