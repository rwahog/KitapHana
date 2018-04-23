package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.*;
import com.kitaphana.Service.DBService;
import com.kitaphana.exceptions.OperationFailedException;
import com.kitaphana.exceptions.UserNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class patronDAOImpl implements patronDAO {

  Database db = Database.getInstance();
  commonDAOImpl commonDAO = new commonDAOImpl();
  DBService dbService = new DBService();
  private static final String FIND_BY_ID = "SELECT * FROM users WHERE id=?";
  private static final String FIND_BY_CHAT_ID = "SELECT * FROM users WHERE chat_id=?";
  private static final String FIND_ALL = "SELECT id FROM users";
  private static final String INSERT = "INSERT INTO users (name, surname, card_number, phone_number, password, email, id_address, possible_type) VALUES (?,?,?,?,?,?,?,?)";
  private static final String UPDATE = "UPDATE users SET name=?, surname=?, card_number=?, phone_number=?, password=?, email=?, documents=?, deadlines=?, type=?, possible_type=?, waiting_list=?, fine=?, renews=?, returns=?, checkouts=?, chat_id=? WHERE id=?";
  private static final String FIND_BY_PHONE_NUMBER = "SELECT * FROM users WHERE phone_number=?";
  private static final String UPDATE_INFO = "UPDATE users SET name=?, surname=?, card_number=?, phone_number=?, password=?, email=? WHERE id=?";

  @Override
  public Patron findById(long id) {
    Patron patron;
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID);
      ps.setLong(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        patron = setUserVariables(rs);
      } else {
        throw new UserNotFoundException();
      }

      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return patron;
  }

  public Patron findByPhoneNumber(String phone) {
    Patron patron = null;
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_BY_PHONE_NUMBER);
      ps.setString(1, phone);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        patron = setUserVariables(rs);
      }
      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return patron;
  }

  public Patron findByChatId(long chatId) {
    Patron patron;
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_BY_CHAT_ID);
      ps.setLong(1, chatId);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        patron = setUserVariables(rs);
      } else {
        throw new UserNotFoundException();
      }

      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return patron;
  }

  @Override
  public ArrayList<Patron> findAll() {
    ArrayList<Patron> patrons = new ArrayList<>();
    try {
      PreparedStatement ps = db.connect().prepareStatement(FIND_ALL);
      ResultSet rs = ps.executeQuery();
      Patron patron;
      while (rs.next()) {
        patron = findById(rs.getLong("id"));
        patrons.add(patron);
      }

      rs.close();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }

    return patrons;
  }

  @Override
  public void insert(Patron patron) {
    try {
      PreparedStatement ps = db.connect().prepareStatement(INSERT);
      setUserVariables(patron, ps);
      ps.setLong(7, patron.getAddressId());
      ps.setString(8, patron.getPossibleType());

      ps.executeUpdate();
      ps.close();

    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  @Override
  public void update(Patron patron) {
    try {
      PreparedStatement ps = db.connect().prepareStatement(UPDATE);
      setUserVariables(patron, ps);
      ps.setString(7, patron.getDocumentsId());
      ps.setString(8, patron.getDeadlines());
      ps.setString(9, patron.getType());
      ps.setString(10, patron.getPossibleType());
      ps.setString(11, patron.getWaitingListId());
      ps.setString(12, patron.getFines());
      ps.setString(13, patron.getRenewsId());
      ps.setString(14, patron.getReturnsId());
      ps.setString(15, patron.getCheckoutsId());
      ps.setLong(16, patron.getChatId());
      ps.setLong(17, patron.getId());

      ps.executeUpdate();
      ps.close();
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  public void updateUserInfo(Patron patron, String type) {
    try {
      PreparedStatement ps = db.connect().prepareStatement(UPDATE_INFO);
      setUserVariables(patron, ps);
      ps.setLong(7, patron.getId());

      ps.executeUpdate();
      ps.close();

      if (type.equals("Librarian")) {
        dbService.updateColumn(String.valueOf(patron.getId()), patron.getType(), "users", "type");
      } else {
        dbService.updateColumn(String.valueOf(patron.getId()), patron.getPossibleType(), "users", "possible_type");
      }
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  @Override
  public void delete(long id) {
    commonDAO.delete(id, "users", "id");
  }

  private Patron setUserVariables(ResultSet rs) {
    Patron patron;
    try {
      patron = new Patron();
      patron.setId(rs.getLong("id"));
      patron.setName(rs.getString("name"));
      patron.setSurname(rs.getString("surname"));
      patron.setCardNumber(rs.getLong("card_number"));
      patron.setPhoneNumber(rs.getString("phone_number"));
      patron.setPassword(rs.getString("password"));
      patron.setEmail(rs.getString("email"));
      patron.setAddressId(rs.getLong("id_address"));
      patron.setDocumentsId(rs.getString("documents"));
      patron.setDeadlines(rs.getString("deadlines"));
      patron.setType(rs.getString("type"));
      patron.setPossibleType(rs.getString("possible_type"));
      patron.setFines(rs.getString("fine"));
      patron.setWaitingListId(rs.getString("waiting_list"));
      patron.setRenewsId(rs.getString("renews"));
      patron.setReturnsId(rs.getString("returns"));
      patron.setCheckoutsId(rs.getString("checkouts"));
      patron.setChatId(rs.getLong("chat_id"));
      String type = rs.getString("type");
      switch (type) {
        case "Student":
          Student student = new Student(patron);
          return student;
        case "Instructor":
          Instructor instructor = new Instructor(patron);
          return instructor;
        case "Teacher Assistant":
          TeacherAssistant assistant = new TeacherAssistant(patron);
          return assistant;
        case "Professor":
          Professor professor = new Professor(patron);
          return professor;
        case "Visiting Professor":
          VisitingProfessor VP = new VisitingProfessor(patron);
          return VP;
      }
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
    return patron;
  }

  private void setUserVariables(Patron patron, PreparedStatement ps) {
    try {
      ps.setString(1, patron.getName());
      ps.setString(2, patron.getSurname());
      ps.setLong(3, patron.getCardNumber());
      ps.setString(4, patron.getPhoneNumber());
      ps.setString(5, patron.getPassword());
      ps.setString(6, patron.getEmail());
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }
}
