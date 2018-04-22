package com.kitaphana.Servlet;


import com.kitaphana.Entities.User;

import com.kitaphana.Service.LoginService;
import com.kitaphana.Service.TelegramBot;
import com.kitaphana.Service.UserService;

import java.io.IOException;
import org.apache.log4j.Logger;
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
    final static Logger logger = Logger.getLogger(LoginServlet.class);
//    TelegramBot telegramBot;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect("/main");
        } else {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException,  ServletException {
        userService = new UserService();
        service = new LoginService();
        phone_number = request.getParameter("login");
        password = request.getParameter("password");
//        telegramBot = new TelegramBot();
        HttpSession session = request.getSession();
        String check = request.getParameter("remember");
        if (check != null) {
            session.setMaxInactiveInterval(24*60*60);
        } else {
            session.setMaxInactiveInterval(2*60*60);
        }
        boolean isValidUser = service.loginCheck(phone_number, password);
        if (isValidUser) {
            boolean isLibrarian = userService.isLibrarian(phone_number);
            if (isLibrarian) {
                session.setAttribute("librarian", "true");
            }
            User user = userService.findUserByPhoneNumber(phone_number);
            session.setAttribute("user", user);
//            if (user.getChatId() != 0){
//                telegramBot.sendMsg(service.getChatId(phone_number), "You have logged in Kitaphana Library System successfully!");
//            }
            logger.info(user.getName() + user.getSurname() + " have logged in Kitaphana.");
            response.sendRedirect("/main");
        } else {
            request.setAttribute("errorMessage", "Invalid username or password");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}