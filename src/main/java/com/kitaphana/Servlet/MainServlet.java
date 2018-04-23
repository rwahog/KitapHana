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
    String searchBy = request.getParameter("criteria");
    ArrayList<Document> arr = null;
    if (searchBy.equals("title")) {
      arr = documentSearch.getDocumentsByPossibleTitle(query, 10, query.length()/4);
    } else if (searchBy.equals("keyword")) {
      arr = documentSearch.getDocumentsByPossibleKeyword(query, 10, query.length()/4);
    } else if (searchBy.equals("author")) {
      arr = documentSearch.getDocumentsByPossibleNameOrSurname(query, 10, query.length()/4);
    }
    request.setAttribute("list", arr);
    request.getRequestDispatcher("/WEB-INF/views/main.jsp").forward(request, response);
  }
}