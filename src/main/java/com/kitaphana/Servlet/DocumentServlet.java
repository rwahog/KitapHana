package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.Service.DocumentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/document")
public class DocumentServlet extends HttpServlet {
  boolean checkOut;
  long id_user;
  int id;
  private DocumentService service = new DocumentService();

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    Document document = service.setDocInfo(request.getParameter("id"));
    request.setAttribute("document", document);
    request.setAttribute("doc_button_available", "true");
    request.getRequestDispatcher("WEB-INF/views/document.jsp").forward(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession session = request.getSession(false);
    long id_user = ((User)session.getAttribute("user")).getId();
    id = Integer.parseInt(request.getParameter("id"));
    String checkout_button = request.getParameter("checkout");
    if (checkout_button.equals("checkout")) {
        String message = service.checkOut(id_user, id);
        request.setAttribute("message", message);
        request.getRequestDispatcher("/WEB-INF/views/failure.jsp").forward(request, response);

    } else if (checkout_button.equals("queue")) {
      try {
        checkOut = service.queue(id_user, id);
        if (checkOut) {
          response.sendRedirect("/verification");
        } else {
          String message = "You cannot be enqueued due to the fact that your type is not confirmed.";
          request.setAttribute("message", message);
          request.getRequestDispatcher("/WEB-INF/views/failure.jsp").forward(request, response);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
