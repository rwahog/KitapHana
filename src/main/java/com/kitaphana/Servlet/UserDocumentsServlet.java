package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Service.MyDocumentsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/librarianPanel/docsOfUser")
public class UserDocumentsServlet extends HttpServlet {

    MyDocumentsService service = new MyDocumentsService();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<Document> docs = service.setDocs(request.getParameter("id"));
        request.setAttribute("name", service.setNameAndSurname(request.getParameter("id")).get(0));
        request.setAttribute("surname", service.setNameAndSurname(request.getParameter("id")).get(1));
        request.setAttribute("docs", docs);
        getServletContext().getRequestDispatcher("/WEB-INF/views/documentsOfUser(adminPanel).jsp").forward(request, response);
    }
}
