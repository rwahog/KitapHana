package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Service.UserDocumentsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/docsOfUser")
public class UserDocumentsServlet extends HttpServlet {

    UserDocumentsService service = new UserDocumentsService();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<Document> docs = service.fillPage(request.getParameter("id"));
        HttpSession session = request.getSession();
        session.setAttribute("docs", docs);
        request.getRequestDispatcher("WEB-INF/views/documentsOfUser(adminPanel).jsp").forward(request, response);
    }


}
