package com.kitaphana.Servlet;

import com.kitaphana.Entities.Book;
import com.kitaphana.Service.DBService;
import com.kitaphana.Service.DocumentService;
import com.kitaphana.Service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/addDocument")
public class AddDocumentServlet extends HttpServlet {

    DocumentService service = new DocumentService();
    DBService dbService = new DBService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            new LoginService().redirect(request,response, "addDocument", true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //request.getRequestDispatcher("WEB-INF/views/addDocument.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String title = request.getParameter("title");
        String authors = request.getParameter("authors");
        String description = request.getParameter("description");
        String keywords = request.getParameter("keywords");
        String type = request.getParameter("document-type");
        int price = Integer.parseInt(request.getParameter("price"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        boolean unique = dbService.checkUnique(title, authors, type);
        switch (type) {
            case "book":
                int edition_number = Integer.parseInt(request.getParameter("edition_number"));
                String publisher = request.getParameter("publisher");
                int year = Integer.parseInt(request.getParameter("year"));
                int bestseller;
                if (request.getParameter("bestseller") == null) {
                    bestseller = 0;
                } else {
                    bestseller = Integer.parseInt(request.getParameter("bestseller"));
                }
                if (unique) {
                    Book book = new Book(title, authors, keywords, price, amount, type, description, publisher, year, edition_number, bestseller);
                    service.saveDocument(book);
                    response.sendRedirect("/librarianPanel");
                }
                break;
            case "article":
                String editors = request.getParameter("editors");
                String journal_name = request.getParameter("journal_name");
                String date = request.getParameter("date");
                break;
        }
    }
}
