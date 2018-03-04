package com.kitaphana.Servlet;

import com.kitaphana.Service.AddDocumentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/addDocument")
public class AddDocumentServlet extends HttpServlet {

    AddDocumentService service = new AddDocumentService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("WEB-INF/views/addDocument.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String title = request.getParameter("title");
        String authors = request.getParameter("author-list");
        String description = request.getParameter("description");
        String keywords = request.getParameter("keywords");
        String type = request.getParameter("document-type");
        String price = request.getParameter("price");
        String amount = request.getParameter("amount");
        boolean unique = service.checkUnique(title, authors, type);
        switch (type) {
            case "book":
                String edition_number = request.getParameter("edition_number");
                String publisher = request.getParameter("publisher");
                String year = request.getParameter("year");
                String bestseller = request.getParameter("bestseller");
                if (unique) {
                    service.saveBook(title, authors, description, keywords, price, amount, edition_number, publisher, year, bestseller);
                    response.sendRedirect("/librarianPanel");
                }
            case "article":
                String editors = request.getParameter("editors");
                String journal_name = request.getParameter("journal_name");
                String date = request.getParameter("date");
        }
    }
}
