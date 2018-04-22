package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Service.DocumentSearch;
import com.kitaphana.Service.DocumentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/main")
public class MainServlet extends HttpServlet {
    DocumentService documentService = new DocumentService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        ArrayList<Document> arr = documentService.findAll();
        request.setAttribute("list", arr);
        request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DocumentSearch documentSearch = new DocumentSearch();
        String query = request.getParameter("query");
        ArrayList<Document> arr = documentSearch.getDocumentsByPossibleTitle(query, 50, 100);
        request.setAttribute("list", arr);
        request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
    }
}