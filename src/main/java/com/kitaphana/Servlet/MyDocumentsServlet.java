package com.kitaphana.Servlet;


import com.kitaphana.Entities.Document;
import com.kitaphana.Service.LoginService;
import com.kitaphana.Service.MyDocumentsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(urlPatterns = "/myDocs")
public class MyDocumentsServlet extends HttpServlet {
    MyDocumentsService service = new MyDocumentsService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ArrayList<Document> docs = service.setDocs(session.getAttribute("id").toString());
        try {
            ArrayList<Document> waitings = service.setWaitingsInfo(Long.parseLong(session.getAttribute("id").toString()));
            request.setAttribute("myWaitings", waitings);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("myDocs", docs);
        request.getRequestDispatcher("WEB-INF/views/myDocument.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String button = request.getParameter("button");
        String renew_button = request.getParameter("renew");
        HttpSession session = request.getSession();
        if (button != null) {
            service.returnDoc(button, session.getAttribute("id").toString());
            response.sendRedirect("/myDocs?id=" + session.getAttribute("id"));
        } else if (renew_button != null) {
            service.renewDoc(renew_button, session.getAttribute("id").toString());
            response.sendRedirect("myDocs?id=" + session.getAttribute("id") );
        }
    }
}
