package com.kitaphana.Servlet;

import com.kitaphana.Service.DeleteUserService;
import com.kitaphana.Service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    DeleteUserService service = new DeleteUserService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getSession().getAttribute("phone_number")==null){
            new LoginService().redirect(request,response, "main");
        }else {
            service.deleteUser(request.getParameter("id"));
            response.sendRedirect("/librarianPanel");
        }

    }

}