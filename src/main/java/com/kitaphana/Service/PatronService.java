package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.Employee;
import com.kitaphana.Entities.Patron;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.addressDAOImpl;
import com.kitaphana.dao.documentDAOImpl;
import com.kitaphana.dao.employeeDAOImpl;
import com.kitaphana.dao.patronDAOImpl;
import com.kitaphana.exceptions.UserNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatronService {
  Database db = Database.getInstance();
  private patronDAOImpl patronDAO = new patronDAOImpl();
  private addressDAOImpl addressDAO = new addressDAOImpl();
  private documentDAOImpl documentDAO = new documentDAOImpl();
  private employeeDAOImpl employeeDAO = new employeeDAOImpl();
  private DBService dbService = new DBService();

  public long getUserId(String phone) {
    return Long.parseLong(dbService.findColumn(phone, "users", "id", "phone_number"));
  }

  private long getUserAddressId(long id) {
    return Long.parseLong(dbService.findColumn(String.valueOf(id), "users", "id_address"));
  }

  public ArrayList<String> phones() {
    return dbService.findAllPhones();
  }

  public Patron findUserById(long id) {
    Patron patron;
    patron = patronDAO.findById(id);
    patron.setAddress(addressDAO.findById(patron.getAddressId()));
    return patron;
  }

  public Patron findUserByPhoneNumber(String phone) {
    Patron patron;
    patron = patronDAO.findByPhoneNumber(phone);
    patron.setAddress(addressDAO.findById(patron.getAddressId()));
    return patron;
  }

  public ArrayList<Patron> findAll() {
    return patronDAO.findAll();
  }

  public int numberOfUnconfirmedUsers() {
    int count = 0;
    final String statement = "SELECT COUNT(users.id) AS count FROM users WHERE users.possible_type <> users.type";
    try {
      PreparedStatement ps = db.connect().prepareStatement(statement);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        count = rs.getInt("count");
      }
      ps.close();
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return count;
  }


  private void deleteUserAddress(long id) {
    addressDAO.delete(getUserAddressId(id));
  }

  public void deletePatron(String userId) {
    long id = Long.parseLong(userId);
    Patron patron = patronDAO.findById(id);
    if (patron == null) {
      throw new UserNotFoundException();
    } else if ((patron.getDocuments().size() != 0) || (patron.getFines().length() != 0)) {
      return;
    }
    else {
      deleteUserAddress(id);
      patronDAO.delete(id);
    }
  }

  public void editUser(Patron patron) {
    addressDAO.update(patron.getAddress());
    patronDAO.update(patron);
  }

  public void editUserInfo(Patron patron, String type) {
    addressDAO.update(patron.getAddress());
    patronDAO.updateUserInfo(patron, type);
    if (!patron.getType().equals(patron.getPossibleType())) {
      dbService.sendMessageToLibrarians("Patron " + patron.getName() + " " +
              patron.getSurname() + " (id: " + patron.getId() + ")" +
              " has unconfirmed type.");
    }
  }

  public ArrayList<Document> fillPage(long id) {
    ArrayList<Document> documents = new ArrayList<>();
    String ids = patronDAO.findById(id).getDocumentsId();
    if (ids == null || ids.length() == 0) {
      return null;
    }
    String[] idss = ids.split(",");
    for (int i = 0; i < idss.length; i++) {
      Document doc = null;
      doc = documentDAO.findById(Long.parseLong(idss[i]));
      documents.add(doc);
    }
    return documents;
  }

  public void savePatron(Patron patron) {
    addressDAO.insert(patron.getAddress());
    patron.setAddressId(addressDAO.findLastId());
    patronDAO.insert(patron);
  }

  public String getRole(String phone_number) {
    String role = "patron";
    Employee employee = employeeDAO.findByPhoneNumber(phone_number);
    if (employee != null) {
      role = "librarian";
      if (employee.getPrivilege() == 0) {
        role = "admin";
      }
    }
    return role;
  }

  public boolean loginCheck(String phone_number, String password) {
    boolean login = false;
    User user = patronDAO.findByPhoneNumber(phone_number);
    Employee employee = employeeDAO.findByPhoneNumber(phone_number);
    if (user != null && user.getPassword().equals(password)) {
      login = true;
    } else if (employee != null && employee.getPassword().equals(password)) {
      login = true;
    }
    return login;
  }
}