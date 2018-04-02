package com.kitaphana.Servlet;

import com.kitaphana.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    UserService service = new UserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                service.deleteUser(Long.parseLong(request.getParameter("id")));
                response.sendRedirect("/librarianPanel");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}

