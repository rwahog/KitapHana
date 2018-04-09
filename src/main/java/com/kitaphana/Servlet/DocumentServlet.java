package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Service.DocumentService;
import com.kitaphana.Entities.Book;
import com.kitaphana.Service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

        try {
            new LoginService().redirect(request, response, "document", false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        id_user = Long.parseLong(String.valueOf(request.getSession().getAttribute("id")));
        id = Integer.parseInt(request.getParameter("id"));
        String checkout_button = request.getParameter("checkout");
        if (checkout_button.equals("checkout")) {
            checkOut = service.checkOut(id_user, id);
            if (checkOut) {
                response.sendRedirect("/verification");
            } else {
                String message = "You cannot check out documents due to the fact that your type is not confirmed.";
                request.setAttribute("message", message);
                request.getRequestDispatcher("/WEB-INF/views/failure.jsp").forward(request, response);
            }
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
