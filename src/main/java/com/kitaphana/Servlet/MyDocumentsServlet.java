package com.kitaphana.Servlet;


import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
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

@WebServlet(urlPatterns = "/myDocs")
public class MyDocumentsServlet extends HttpServlet {
  MyDocumentsService service = new MyDocumentsService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession session = request.getSession();
    ArrayList<Document> docs = service.setDocs(String.valueOf(((User) session.getAttribute("user")).getId()));
    try {
      ArrayList<Document> waitings = service.setWaitingsInfo(((User) session.getAttribute("user")).getId());
      request.setAttribute("myWaitings", waitings);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    request.setAttribute("myDocs", docs);
    request.getRequestDispatcher("WEB-INF/views/myDocument.jsp").forward(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String returnButton = request.getParameter("return");
    String renewButton = request.getParameter("renew");
    HttpSession session = request.getSession(false);
    String userId = String.valueOf(((User) session.getAttribute("user")).getId());
    if (returnButton != null) {
      service.returnDoc(returnButton, userId);
      response.sendRedirect("/myDocs");
    } else if (renewButton != null) {
      service.renewDoc(renewButton, userId);
      response.sendRedirect("/myDocs");
    }
  }
}
