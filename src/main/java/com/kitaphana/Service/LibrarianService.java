package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.documentDAOImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class LibrarianService {
    private Database db = Database.getInstance();
    documentDAOImpl documentDAO = new documentDAOImpl();
    DocumentService documentService = new DocumentService();


}
