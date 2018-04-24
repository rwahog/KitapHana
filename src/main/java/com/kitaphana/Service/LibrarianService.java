package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Employee;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.employeeDAOImpl;

public class LibrarianService {
  private Database db = Database.getInstance();
  documentDAOImpl documentDAO = new documentDAOImpl();
  DocumentService documentService = new DocumentService();
  employeeDAOImpl employeeDAOImpl = new employeeDAOImpl();

  public Employee findByPhone(String phone) {
    return employeeDAOImpl.findByPhoneNumber(phone);
  }

}
