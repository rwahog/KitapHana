package com.kitaphana.Servlet;

import com.kitaphana.Service.Document;
import com.kitaphana.Service.MainService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/main")
public class MainServlet extends HttpServlet {
    MainService mainService = new MainService();
    public boolean isLiber;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        ArrayList<Document> arr = mainService.fillPage();
        session.setAttribute("list", arr);
        String phone_number = (String) session.getAttribute("login");
        String password = (String) session.getAttribute("password");
        isLiber = mainService.isLibrarian(phone_number, password);
        request.getSession().setAttribute("is_librarian", isLiber);
        request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("title");
        request.getSession().setAttribute("name", request.getSession().getAttribute("name"));
        request.getSession().setAttribute("surname", request.getSession().getAttribute("surname"));
    }
}
