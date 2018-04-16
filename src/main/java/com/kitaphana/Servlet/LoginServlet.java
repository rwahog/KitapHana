package com.kitaphana.Servlet;


import com.kitaphana.Service.LoginService;
import com.kitaphana.Service.TelegramBot;
import com.kitaphana.Service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns="/login")
public class LoginServlet extends HttpServlet{

    String phone_number;
    String password;
    LoginService service;
    UserService userService;
    TelegramBot telegramBot;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException,  ServletException {
        userService = new UserService();
        service = new LoginService();
        phone_number = request.getParameter("login");
        password = request.getParameter("password");
        telegramBot = new TelegramBot();
        HttpSession session = request.getSession();
        String check = request.getParameter("remember");
        if(check != null){
            session.setMaxInactiveInterval(24*60*60);
        }
        boolean isValidUser = false;
        try {
            isValidUser = service.loginCheck(phone_number, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (isValidUser){
            boolean isLibrarian = userService.isLibrarian(phone_number);
            if (isLibrarian) {
                session.setAttribute("librarian", "true");
            }
            try {
                session.setAttribute("name", service.getUserNameAndId(phone_number).getName());
                session.setAttribute("surname", service.getUserNameAndId(phone_number).getSurname());
                session.setAttribute("id", service.getUserNameAndId(phone_number).getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            session.setAttribute("login", phone_number);
            session.setAttribute("password", password);
            session.setAttribute("chat_id", service.getChatId(phone_number));
            if (service.getChatId(phone_number)!=0){
                try {
                    telegramBot.sendMsg(service.getChatId(phone_number), "You have logged in Kitaphana Library System successfully!");
                }
                catch (Exception e){
                }
            }
            response.sendRedirect("/main");
        }
        else {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }

    }
}
