package com.kitaphana.Servlet;

import com.kitaphana.Service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/librarianPanel")
public class LibrarianPanelServlet extends HttpServlet {
    AdminService adminService = new AdminService();
    LibrarianPanelService service = new LibrarianPanelService();
    DocumentService documentService = new DocumentService();
    UserService userService = new UserService();
    DBService dbService = new DBService();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("docs", documentService.findAll());
        request.setAttribute("usersCount", userService.numberOfUnconfirmedUsers());
        request.setAttribute("users", userService.findAll());
        request.setAttribute("checkouts", service.setCheckoutsInfo());
        request.setAttribute("renews", service.setRenewsInfo());
        request.setAttribute("returns", service.setReturnsInfo());
        request.setAttribute("waitingList", service.setWaintingsInfo());
        request.setAttribute("history", adminService.logging());
        request.setAttribute("librarians", adminService.findAll());
        request.getRequestDispatcher("/WEB-INF/views/librarianPanel.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String userId = request.getParameter("user_id");
        String docId = request.getParameter("doc_id");
        if (request.getParameter("checkout_approval") != null && Integer.parseInt(request.getParameter("checkout_approval")) == 1) {
//            String userChatId = dbService.findColumn(request.getParameter("user_id"), "users", "chat_id");
            documentService.checkOutApproval(userId, docId);
//            String message = "You have checked out new book successfully!";
//            dbService.sendMessageToUser(message, userChatId);
            response.sendRedirect("/main");
        } else if (request.getParameter("checkout_disapproval") != null && Integer.parseInt(request.getParameter("checkout_disapproval")) == 1) {
//            String userChatId = dbService.findColumn(request.getParameter("user_id"), "users", "chat_id");
            documentService.checkOutDisapproval(userId, docId);
//            String message = "Your request to check out the book was rejected";
//            dbService.sendMessageToUser(message, userChatId);
            response.sendRedirect("/main");
        } else if (request.getParameter("renew_approval") != null && Integer.parseInt(request.getParameter("renew_approval")) == 1) {
//            String userChatId = dbService.findColumn(request.getParameter("user_id"), "users", "chat_id");
            documentService.renewDocApproval(userId, docId);
//            String message = "Renew has been approved";
//            dbService.sendMessageToUser(message, userChatId);
            response.sendRedirect("/main");
        } else if ((request.getParameter("renew_disapproval") != null && Integer.parseInt(request.getParameter("renew_disapproval")) == 1)){
//            String userChatId = dbService.findColumn(request.getParameter("user_id"), "users", "chat_id");
            documentService.renewDocDisApproval(userId, docId);
//            String message = "Renew hasn't been approved";
//            dbService.sendMessageToUser(message, userChatId);
            response.sendRedirect("/main");
        } else if ((request.getParameter("return_approval") != null && Integer.parseInt(request.getParameter("return_approval")) == 1)) {
//            String userChatId = dbService.findColumn(request.getParameter("user_id"), "users", "chat_id");
            documentService.returnDocApproval(userId, docId);
//            String message = "You returned document successfully.";
//            dbService.sendMessageToUser(message, userChatId);
            response.sendRedirect("/main");
        } else if ((request.getParameter("outstanding_request") != null && Integer.parseInt(request.getParameter("outstanding_request")) == 1)) {
            documentService.outstandingRequest(docId);
            response.sendRedirect("/main");
        }
    }
}
