package com.kitaphana.Servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = service.setUserInfo(session.getAttribute("id").toString());
        session.setAttribute("user", user);
        request.getRequestDispatcher("WEB-INF/views/profile.jsp").forward(request, response);
    }
}
