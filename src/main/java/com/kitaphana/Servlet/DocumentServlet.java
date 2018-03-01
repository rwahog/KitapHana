package com.kitaphana.Servlet;

import com.kitaphana.Service.Book;
import com.kitaphana.Service.Document;
import com.kitaphana.Service.DocumentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/document")
public class DocumentServlet extends HttpServlet {

    DocumentService service = new DocumentService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ArrayList<Book> arr = service.setDocInfo(request.getParameter("title"));
        HttpSession session = request.getSession();
        session.setAttribute("list", arr);
        request.getRequestDispatcher("/WEB-INF/views/document.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
    //v processe
    }
}
