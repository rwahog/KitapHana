package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Employee;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.employeeDAOImpl;
import com.kitaphana.dao.patronDAOImpl;

public class LoginService {
  public Database db = Database.getInstance();
  patronDAOImpl patronDAO = new patronDAOImpl();
  employeeDAOImpl employeeDAO = new employeeDAOImpl();

}