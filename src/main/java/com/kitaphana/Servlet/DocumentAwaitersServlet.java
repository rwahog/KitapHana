package com.kitaphana.Servlet;

import com.kitaphana.Service.DocumentHoldersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/awaiters")
public class DocumentAwaitersServlet extends HttpServlet {
  DocumentHoldersService service = new DocumentHoldersService();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    request.setAttribute("awaiters", service.setAwaitersInfo(request.getParameter("doc_id")));
    request.getRequestDispatcher("WEB-INF/views/documentAwaiters.jsp").forward(request, response);
  }
}
