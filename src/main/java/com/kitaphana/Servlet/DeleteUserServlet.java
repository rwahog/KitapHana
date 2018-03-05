package com.kitaphana.Servlet;

import com.kitaphana.Service.DeleteUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    DeleteUserService service = new DeleteUserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service.deleteUser(request.getParameter("id"));
        response.sendRedirect("/librarianPanel");
    }

}