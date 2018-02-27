package com.kitaphana.Servlet;


import com.kitaphana.Service.LoginService;

import java.io.IOException;
import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns="/login.do")
public class LoginServlet extends HttpServlet{

    private LoginService service = new LoginService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException,  ServletException {
        String phone_number = request.getParameter("login");
        String password = request.getParameter("password");
        boolean isValidUser = false;
        isValidUser = service.loginCheck(phone_number, password);

        if (isValidUser){
            request.getSession().setAttribute("name", service.getUserName(phone_number).get(0));
            request.getSession().setAttribute("surname", service.getUserName(phone_number).get(1));
            response.sendRedirect("/main");
        }
        else {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
