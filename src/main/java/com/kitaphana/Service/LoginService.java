package com.kitaphana.Service;
import com.kitaphana.Database.Database;
import com.kitaphana.Entities.User;
import com.kitaphana.dao.userDAOImpl;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginService {
    public Database db = Database.getInstance();
    userDAOImpl userDAO = new userDAOImpl();
    public boolean login;

    public boolean loginCheck(String phone_number, String password) throws SQLException {
        login = false;
        User user = userDAO.findByPhoneNumber(phone_number);
        if (user.getPassword().equals(password)) {
            login = true;
        }
        return login;
    }

    public User getUserNameAndId(String phone_number) throws SQLException {
        User user = userDAO.findByPhoneNumber(phone_number);
        return user;
    }
    public void redirect(HttpServletRequest request, HttpServletResponse response, String red, boolean libCheck) throws IOException, ServletException, SQLException {
        HttpSession session = request.getSession();
        String phone_number = (String) session.getAttribute("login");
        String password = (String) session.getAttribute("password");
        loginCheck(phone_number, password);
        if(!login){
            response.sendRedirect("/logout");
        } else {
            ResultSet rs = db.runSqlQuery("SELECT users.name, users.type, users.id FROM users WHERE users.phone_number = '" + phone_number + "';");
            rs.next();
            String status = rs.getString("type");
            if (libCheck && !status.equals("Librarian")) {
                response.sendRedirect("/main");
            } else {
                request.getRequestDispatcher("/WEB-INF/views/" + red + ".jsp").forward(request, response);
            }
        }
    }

    public long getChatId(String phone_number) {
        long chat_id = 0;
        try {
            db.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = db.runSqlQuery("SELECT users.chat_id FROM users WHERE users.phone_number = '"+phone_number+"';");
            while (rs.next()) {
                chat_id = rs.getLong("chat_id");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return chat_id;
    }

    public static void sendMsg(long chatId, String message) throws Exception {
        String postURL = "https://api.telegram.org/bot577066011:AAFK2TXevqQRFXkJjS_eAIEEaPH2MOcXJ_s/sendMessage";

        HttpPost post = new HttpPost(postURL);

        HttpClient client = new DefaultHttpClient();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("chat_id", Long.toString(chatId)));
        params.add(new BasicNameValuePair("text", message));

        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(ent);
        HttpResponse responsePOST = client.execute(post);
        System.out.println(chatId + message);
    }
}
