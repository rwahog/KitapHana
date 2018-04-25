package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.ActionMessage;
import com.kitaphana.Entities.Address;
import com.kitaphana.Entities.Employee;
import com.kitaphana.Entities.Librarian;
import com.kitaphana.dao.addressDAOImpl;
import com.kitaphana.dao.employeeDAOImpl;
import com.kitaphana.exceptions.OperationFailedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AdminService {
  private Database db = Database.getInstance();
  private employeeDAOImpl employeeDAOImpl = new employeeDAOImpl();
  private addressDAOImpl addressDAO = new addressDAOImpl();
  private DBService DBService = new DBService();
  private static final String LOGGING = "SELECT * FROM logging";

  public ArrayList<Employee> findAll() {
    return employeeDAOImpl.findAll();
  }

  public void addLibrarian(Librarian librarian) {
    addressDAO.insert(librarian.getAddress());
    long addressId = addressDAO.findLastId();
    librarian.setAddressId(addressId);
    employeeDAOImpl.insert(librarian);
  }

  public void updateLibrarian(Librarian librarian) {
    String addressId = DBService.findColumn(String.valueOf(librarian.getId()), "librarians",
            "address_id");
    Address address = librarian.getAddress();
    address.setAddressId(Long.parseLong(addressId));
    addressDAO.update(address);
    employeeDAOImpl.update(librarian);
  }

  public void deleteLibrarian(String id) {
    employeeDAOImpl.delete(Long.parseLong(id));
  }

  public Employee findById(String id) {
    Employee employee = employeeDAOImpl.findById(Long.parseLong(id));
    Address address = addressDAO.findById(employee.getAddressId());
    employee.setAddress(address);
    return employee;
  }

  public ArrayList<ActionMessage> logging() {
    ArrayList<ActionMessage> actions = new ArrayList<>();
    try {
      PreparedStatement ps = db.connect().prepareStatement(LOGGING);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        ActionMessage message = new ActionMessage(rs.getString("date"),
                rs.getString("message"));
        actions.add(message);
      }
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return actions;
  }
}
