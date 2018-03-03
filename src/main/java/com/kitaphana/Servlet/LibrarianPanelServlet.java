package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.Service.LibrarianPanelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/librarianPanel")
public class LibrarianPanelServlet extends HttpServlet {
    LibrarianPanelService service = new LibrarianPanelService();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ArrayList<User> arr = service.setUsersInfo();
        ArrayList<Document> arr2 = service.setDocsInfo();
        session.setAttribute("docs", arr2);
        session.setAttribute("users", arr);
        request.getRequestDispatcher("/WEB-INF/views/librarianPanel.jsp").forward(request, response);
    }
}
