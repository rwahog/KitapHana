package com.kitaphana.Servlet;

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
    String name, surname;
    int id;
    private DocumentService service = new DocumentService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<Book> arr = service.setDocInfo(request.getParameter("id"));
        HttpSession session = request.getSession();
        session.setAttribute("list", arr);
        new LoginService().redirect(request, response, "document");
        //
        // request.getRequestDispatcher("/WEB-INF/views/document.jsp").forward(request, response);


    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        name = (String) request.getSession().getAttribute("name");
        surname = (String) request.getSession().getAttribute("surname");
        id = Integer.parseInt(request.getParameter("id"));
        System.out.println(name+surname+id);
        checkOut = service.checkOut(name, surname, id);
        if (checkOut) {
            request.getSession().setAttribute("name", name);
            request.getSession().setAttribute("surname", surname);
            response.sendRedirect("/verification");
        }
    }
}
