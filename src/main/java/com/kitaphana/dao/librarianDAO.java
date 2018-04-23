package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.Admin;
import com.kitaphana.Entities.Employee;
import com.kitaphana.Entities.Librarian;
import com.kitaphana.exceptions.OperationFailedException;
import com.kitaphana.exceptions.UserNotFoundException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class librarianDAO {

    Database db = Database.getInstance();
    commonDAOImpl commonDAO = new commonDAOImpl();

    private static final String FIND_BY_ID = "SELECT * FROM librarians WHERE id=?";
    private static final String FIND_ALL = "SELECT id FROM librarians";
    private static final String INSERT = "INSERT INTO librarians (name, surname, card_number, phone_number, password, email, address_id, privilege) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE librarians SET name=?, surname=?, card_number=?, phone_number=?, password=?, email=?, chat_id=?, privilege=? WHERE id=?";
    private static final String FIND_BY_PHONE_NUMBER = "SELECT * FROM librarians WHERE phone_number=?";

    public long findLastId() {
        return commonDAO.findLastId("librarians");
    }

    public Employee findById(long id) {
        Employee employee;
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employee = getVariables(rs);
            } else {
                throw new UserNotFoundException();
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return employee;
    }

    public Employee findByPhoneNumber(String phoneNumber) {
        Employee employee = null;
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_BY_PHONE_NUMBER);
            ps.setString(1, phoneNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                employee = getVariables(rs);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return employee;
    }

    public ArrayList<Employee> findAll() {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = findById(rs.getLong("id"));
                employees.add(employee);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return employees;
    }

    public void insert(Librarian librarian) {
        try {
            PreparedStatement ps;
            ps = db.connect().prepareStatement(INSERT);

            setVariables(librarian, ps);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    public void update(Librarian librarian) {
        try {
            PreparedStatement ps;
            ps = db.connect().prepareStatement(UPDATE);
            setVariables(librarian, ps);
            ps.setLong(9, librarian.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    public void delete(long id) {
        commonDAO.delete(id, "librarians", "id");
    }

    private Employee getVariables(ResultSet rs) {
        Employee employee;
        try {
            employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setName(rs.getString("name"));
            employee.setSurname(rs.getString("surname"));
            employee.setCardNumber(rs.getLong("card_number"));
            employee.setPhoneNumber(rs.getString("phone_number"));
            employee.setPassword(rs.getString("password"));
            employee.setEmail(rs.getString("email"));
            employee.setAddressId(rs.getLong("address_id"));
            employee.setChatId(rs.getLong("chat_id"));
            int privilege = rs.getInt("privilege");
            if (privilege == 0) {
                Admin admin = new Admin(employee);
                return admin;
            } else {
                Librarian librarian = new Librarian(employee);
                librarian.setPrivilege(rs.getInt("privilege"));
                return librarian;
            }
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    private void setVariables(Employee employee, PreparedStatement ps) {
        try {
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getSurname());
            ps.setLong(3, employee.getCardNumber());
            ps.setString(4, employee.getPhoneNumber());
            ps.setString(5, employee.getPassword());
            ps.setString(6, employee.getEmail());
            ps.setLong(7, employee.getAddressId());
            ps.setInt(8, employee.getPrivilege());
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }
}
