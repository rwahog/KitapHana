package com.kitaphana.Service;

import com.kitaphana.Entities.ActionMessage;
import com.kitaphana.Entities.Employee;
import com.kitaphana.Entities.Librarian;
import com.kitaphana.dao.librarianDAO;
import com.kitaphana.exceptions.OperationFailedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminService {
  private librarianDAO librarianDAO = new librarianDAO();


  public ArrayList<Employee> findAll() {
    return librarianDAO.findAll();
  }

  public void addLibrarian(Librarian librarian) {
    librarianDAO.insert(librarian);
  }

  public void updateLibrarian(Librarian librarian) {
    librarianDAO.update(librarian);
  }

  public void deleteLibrarian(String id) {
    librarianDAO.delete(Long.parseLong(id));
  }

  public Employee findById(String id) {
    Employee employee = librarianDAO.findById(Long.parseLong(id));
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
