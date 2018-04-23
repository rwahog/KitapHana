package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Employee;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.librarianDAO;

public class LibrarianService {
  private Database db = Database.getInstance();
  documentDAOImpl documentDAO = new documentDAOImpl();
  DocumentService documentService = new DocumentService();
  librarianDAO librarianDAO = new librarianDAO();

  public Employee findByPhone(String phone) {
    return librarianDAO.findByPhoneNumber(phone);
  }
}
