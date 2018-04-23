package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.Employee;
import com.kitaphana.Entities.Librarian;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.librarianDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class LibrarianService {
    private Database db = Database.getInstance();
    documentDAOImpl documentDAO = new documentDAOImpl();
    DocumentService documentService = new DocumentService();
    librarianDAO librarianDAO = new librarianDAO();

    public Employee findByPhone(String phone) {
        return librarianDAO.findByPhoneNumber(phone);
    }
}
