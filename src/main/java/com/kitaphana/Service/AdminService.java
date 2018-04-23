package com.kitaphana.Service;

import com.kitaphana.Entities.ActionMessage;
import com.kitaphana.Entities.Address;
import com.kitaphana.Entities.Employee;
import com.kitaphana.Entities.Librarian;
import com.kitaphana.dao.addressDAOImpl;
import com.kitaphana.dao.employeeDAOImpl;
import com.kitaphana.exceptions.OperationFailedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminService {
  private employeeDAOImpl employeeDAOImpl = new employeeDAOImpl();
  private addressDAOImpl addressDAO = new addressDAOImpl();
  private DBService DBService = new DBService();

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
    String addressId = DBService.findColumn(String.valueOf(librarian.getId()), "users",
            "id_address");
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
    final String filePath = "c://logger.log";

    try (Scanner scanner = new Scanner(new File(filePath))) {
      while (scanner.hasNext()) {
        String out = scanner.nextLine();
        String[] formatter = out.split(" : ");
        ActionMessage message = new ActionMessage(
                formatter[0], formatter[1]);
        actions.add(message);
      }
    } catch (IOException e) {
      throw new OperationFailedException();
    }
    return actions;
  }
}
