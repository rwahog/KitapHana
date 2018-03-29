package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Service.LoginService;
import com.kitaphana.Service.UserDocumentsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/docsOfUser")
public class UserDocumentsServlet extends HttpServlet {

    UserDocumentsService service = new UserDocumentsService();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<Document> docs = service.fillPage(request.getParameter("id"));
        HttpSession session = request.getSession();
        request.setAttribute("name", service.setNameAndSurname(request.getParameter("id")).get(0));
        request.setAttribute("surname", service.setNameAndSurname(request.getParameter("id")).get(1));
        session.setAttribute("docs", docs);

        try {
            new LoginService().redirect(request, response, "documentsOfUser(adminPanel)", true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //request.getRequestDispatcher("WEB-INF/views/documentsOfUser(adminPanel).jsp").forward(request, response);
    }


}
