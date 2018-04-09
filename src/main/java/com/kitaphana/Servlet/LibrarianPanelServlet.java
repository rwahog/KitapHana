package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.Service.DocumentService;
import com.kitaphana.Service.LibrarianService;
import com.kitaphana.Service.LoginService;
import com.kitaphana.Service.UserService;

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
    LibrarianService service = new LibrarianService();
    DocumentService documentService = new DocumentService();
    UserService userService = new UserService();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        ArrayList<User> users = userService.findAll();
        ArrayList<Document> docs = documentService.findAll();
        ArrayList<User> arr3 = service.setCheckoutsInfo();
        ArrayList<User> arr4 = service.setRenewsInfo();
        ArrayList<User> arr5 = service.setReturnsInfo();
        ArrayList<Document> arr6 = service.setWaintingsInfo();
        session.setAttribute("docs", docs);
        session.setAttribute("users", users);
        request.setAttribute("usersCheckouts", arr3);
        request.setAttribute("renews", arr4);
        request.setAttribute("returns", arr5);
        request.setAttribute("waitingList", arr6);
        try {
            new LoginService().redirect(request, response, "librarianPanel", true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("checkout_approval") != null && Integer.parseInt(request.getParameter("checkout_approval")) == 1) {
            documentService.checkOutApproval(request.getParameter("user_id"), request.getParameter("doc_id"));
            try {
                documentService.sendMsg(documentService.getChatId(Long.parseLong(request.getParameter("user_id"))), "You have checked out new book successfully!");
            }
            catch (Exception e){
                e.printStackTrace();
            }
            response.sendRedirect("/main");
        } else if (request.getParameter("checkout_disapproval") != null && Integer.parseInt(request.getParameter("checkout_disapproval")) == 1) {
            documentService.checkOutDisapproval(request.getParameter("user_id"), request.getParameter("doc_id"));
            try {
                documentService.sendMsg(documentService.getChatId(Long.parseLong(request.getParameter("user_id"))), "You have checked out new book unsuccessfully :(");
            }
            catch (Exception e){
                e.printStackTrace();
            }
            response.sendRedirect("/main");
        } else if (request.getParameter("renew_approval") != null && Integer.parseInt(request.getParameter("renew_approval")) == 1) {
            documentService.renewDocApproval(request.getParameter("user_id"), request.getParameter("doc_id"));
            try {
                documentService.sendMsg(documentService.getChatId(Long.parseLong(request.getParameter("user_id"))), "Renew was approved :)");
            }
            catch (Exception e){
                e.printStackTrace();
            }
            response.sendRedirect("/main");
        } else if ((request.getParameter("renew_disapproval") != null && Integer.parseInt(request.getParameter("renew_disapproval")) == 1)){
            documentService.renewDocDisApproval(request.getParameter("user_id"), request.getParameter("doc_id"));
            try {
                documentService.sendMsg(documentService.getChatId(Long.parseLong(request.getParameter("user_id"))), "Renew wasn't approved :(");
            }
            catch (Exception e){
                e.printStackTrace();
            }
            response.sendRedirect("/main");
        } else if ((request.getParameter("return_approval") != null && Integer.parseInt(request.getParameter("return_approval")) == 1)) {
            documentService.returnDocApproval(request.getParameter("user_id"), request.getParameter("doc_id"));
            response.sendRedirect("/main");
        }
    }
}
