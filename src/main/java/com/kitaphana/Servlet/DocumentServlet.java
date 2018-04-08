package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Service.DocumentService;
import com.kitaphana.Service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/document")
public class DocumentServlet extends HttpServlet {
    boolean checkOut;
    long id_user;
    int id;
    long chat_id;
    private DocumentService service = new DocumentService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Document document = service.setDocInfo(request.getParameter("id"));
        request.setAttribute("document", document);
        try {
            new LoginService().redirect(request, response, "document", false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //
        // request.getRequestDispatcher("/WEB-INF/views/document.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        id_user = Long.parseLong(String.valueOf(request.getSession().getAttribute("id")));
        id = Integer.parseInt(request.getParameter("id"));
        chat_id = Long.parseLong(String.valueOf(request.getSession().getAttribute("chat_id")));
        checkOut = service.checkOut(id_user, id);
        if (checkOut) {
            response.sendRedirect("/verification");
            try {
                service.sendMsg(service.getChatId(id), "You have checked out new book successfully!");
            }
            catch (Exception e){
            }
        }
    }
}
