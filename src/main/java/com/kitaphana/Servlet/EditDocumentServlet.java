package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Service.EditDocumentService;
import com.kitaphana.Service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = "/editDoc")
public class EditDocumentServlet extends HttpServlet {

    EditDocumentService service = new EditDocumentService();
    Document doc = new Document();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doc = service.setDocInfo(Integer.parseInt(request.getParameter("id")));
        HttpSession session = request.getSession();
        session.setAttribute("doc", doc);
        new LoginService().redirect(request, response, "editDocument");
        //
        // request.getRequestDispatcher("WEB-INF/views/editDocument.jsp").forward(request, response);
    }
}
