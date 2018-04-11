package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Service.DocumentService;
import com.kitaphana.Service.LoginService;
import com.kitaphana.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/main")
public class MainServlet extends HttpServlet {
    DocumentService documentService = new DocumentService();
    UserService userService = new UserService();
    public boolean isLiber;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        ArrayList<Document> arr = documentService.findAll();
        session.setAttribute("list", arr);

        String phone_number = (String) session.getAttribute("login");
        String password = (String) session.getAttribute("password");
        isLiber = userService.isLibrarian(phone_number);
        request.getSession().setAttribute("is_librarian", isLiber);
        try {
            new LoginService().redirect(request, response, "main", false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("title");
        request.getSession().setAttribute("name", request.getSession().getAttribute("name"));
        request.getSession().setAttribute("surname", request.getSession().getAttribute("surname"));
        request.getSession().setAttribute("title", id);
    }
}