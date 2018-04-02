package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.Service.DocumentService;
import com.kitaphana.Service.LibrarianPanelService;
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

@WebServlet(urlPatterns = "/librarianPanel")
public class LibrarianPanelServlet extends HttpServlet {
    LibrarianPanelService service = new LibrarianPanelService();
    DocumentService documentService = new DocumentService();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ArrayList<User> arr = service.setUsersInfo();
        ArrayList<Document> arr2 = service.setDocsInfo();
        ArrayList<User> arr3 = service.setCheckoutsInfo();
        ArrayList<User> arr4 = service.setRenewsInfo();
        ArrayList<User> arr5 = service.setReturnsInfo();
        ArrayList<Document> arr6 = service.setWaintingsInfo();
        session.setAttribute("docs", arr2);
        session.setAttribute("users", arr);
        request.setAttribute("users_checkout", arr3);
        request.setAttribute("renews", arr4);
        request.setAttribute("returns", arr5);
        request.setAttribute("waiting_list", arr6);
        try {
            new LoginService().redirect(request, response, "librarianPanel", true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        String approval_checkout = request.getParameter("button");

            documentService.checkout_approval(request.getParameter("id_user"), request.getParameter("id_doc"));
            response.sendRedirect("/main");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}
