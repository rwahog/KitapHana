package com.kitaphana.Service;

import com.kitaphana.Entities.Employee;
import com.kitaphana.dao.employeeDAOImpl;

public class LibrarianService {
  employeeDAOImpl employeeDAOImpl = new employeeDAOImpl();

  public Employee findByPhone(String phone) {
    return employeeDAOImpl.findByPhoneNumber(phone);
  }

}
