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

@WebServlet(urlPatterns="/main")
public class MainServlet extends HttpServlet {
    MainService mainService = new MainService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        ArrayList<Document> arr = mainService.fillPage();
        HttpSession session = request.getSession();
        session.setAttribute("list", arr);
        request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
    }
}
