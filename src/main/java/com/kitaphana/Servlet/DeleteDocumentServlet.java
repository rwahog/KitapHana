package com.kitaphana.Servlet;

import com.kitaphana.Service.DocumentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/deleteDoc")
public class DeleteDocumentServlet extends HttpServlet {

    DocumentService service = new DocumentService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        service.deleteDocument(Long.parseLong(request.getParameter("id")));
        response.sendRedirect("/librarianPanel");
    }
}
