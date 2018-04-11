package com.kitaphana.Servlet;

import com.kitaphana.Entities.Document;
import com.kitaphana.Entities.User;
import com.kitaphana.Service.*;

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
    DBService dbService = new DBService();
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
        String userId = request.getParameter("user_id");
        String docId = request.getParameter("doc_id");
        if (request.getParameter("checkout_approval") != null && Integer.parseInt(request.getParameter("checkout_approval")) == 1) {
            String userChatId = dbService.findColumn(request.getParameter("user_id"), "users", "chat_id");
            documentService.checkOutApproval(userId, docId);
            String message = "You have checked out new book successfully!";
            dbService.sendMessageToUser(message, userChatId);
            response.sendRedirect("/main");
        } else if (request.getParameter("checkout_disapproval") != null && Integer.parseInt(request.getParameter("checkout_disapproval")) == 1) {
            String userChatId = dbService.findColumn(request.getParameter("user_id"), "users", "chat_id");
            documentService.checkOutDisapproval(userId, docId);
            String message = "Your request to check out the book was rejected";
            dbService.sendMessageToUser(message, userChatId);
            response.sendRedirect("/main");
        } else if (request.getParameter("renew_approval") != null && Integer.parseInt(request.getParameter("renew_approval")) == 1) {
            String userChatId = dbService.findColumn(request.getParameter("user_id"), "users", "chat_id");
            documentService.renewDocApproval(userId, docId);
            String message = "Renew has approved";
            dbService.sendMessageToUser(message, userChatId);
            response.sendRedirect("/main");
        } else if ((request.getParameter("renew_disapproval") != null && Integer.parseInt(request.getParameter("renew_disapproval")) == 1)){
            String userChatId = dbService.findColumn(request.getParameter("user_id"), "users", "chat_id");
            documentService.renewDocDisApproval(userId, docId);
            String message = "Renew hasn't approved";
            dbService.sendMessageToUser(message, userChatId);
            response.sendRedirect("/main");
        } else if ((request.getParameter("return_approval") != null && Integer.parseInt(request.getParameter("return_approval")) == 1)) {
            String userChatId = dbService.findColumn(request.getParameter("user_id"), "users", "chat_id");
            documentService.returnDocApproval(userId, docId);
            String message = "You returned document successfully.";
            dbService.sendMessageToUser(message, userChatId);
            response.sendRedirect("/main");
        } else if ((request.getParameter("outstanding_request") != null && Integer.parseInt(request.getParameter("outstanding_request")) == 1)) {
            documentService.outstandingRequest(docId);
            response.sendRedirect("/main");
        }
    }
}
