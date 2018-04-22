package com.kitaphana.Service;
import com.kitaphana.Database.Database;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.patronDAOImpl;

public class LoginService {
    public Database db = Database.getInstance();
    patronDAOImpl userDAO = new patronDAOImpl();

    public boolean loginCheck(String phone_number, String password) {
        boolean login = false;
        User user = userDAO.findByPhoneNumber(phone_number);
        if (user != null && user.getPassword().equals(password)) {
            login = true;
        }
        return login;
    }
}