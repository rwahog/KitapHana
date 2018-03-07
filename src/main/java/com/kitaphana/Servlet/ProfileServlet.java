package com.kitaphana.Servlet;

import com.kitaphana.Entities.User;
import com.kitaphana.Service.EditUserService;
import com.kitaphana.Service.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    EditUserService serviceEdit = new EditUserService();
    ProfileService service = new ProfileService();
    User user = new User();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        user = service.setUserInfo(session.getAttribute("id").toString());
        session.setAttribute("user", user);
        request.getRequestDispatcher("WEB-INF/views/profile.jsp").forward(request, response);
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//        String status = request.getParameter("status");
//        String phone_number = request.getParameter("phone_number");
//        String email = request.getParameter("email");
//        String password1 = request.getParameter("password1");
//        String password2 = request.getParameter("password2");
//        String country = request.getParameter("country");
//        String town = request.getParameter("town");
//        String street = request.getParameter("street");
//        String house_number = request.getParameter("house_number");
//        String apartment_number = request.getParameter("apartment_number");
//        String post_code = request.getParameter("postcode");

//        boolean isValid = serviceEdit.isValid(Integer.parseInt(request.getParameter("id")), phone_number, password1, password2);
//        if (isValid) {
//            serviceEdit.editUser(name, surname, status, phone_number, password1, email, country, town, street, house_number, apartment_number, post_code, Integer.parseInt(request.getParameter("id")), user.getAddress().getId_address());
//            response.sendRedirect("/main");
//        } else {
//            request.getRequestDispatcher("WEB-INF/views/profile.jsp").forward(request, response);
//        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String status = request.getParameter("status");
        String phone_number = request.getParameter("phone_number");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String country = request.getParameter("country");
        String town = request.getParameter("town");
        String street = request.getParameter("street");
        String house_number = request.getParameter("house_number");
        String apartment_number = request.getParameter("apartment_number");
        String post_code = request.getParameter("postcode");

        boolean isValid = serviceEdit.isValid(Integer.parseInt(request.getParameter("id")), phone_number, password1, password2);
        if (isValid) {
            serviceEdit.editUser(name, surname, status, phone_number, password1, email, country, town, street, house_number, apartment_number, post_code, Integer.parseInt(request.getParameter("id")), user.getAddress().getId_address());
            response.sendRedirect("/main");
        } else {
            request.getRequestDispatcher("WEB-INF/views/profile.jsp").forward(request, response);
        }
    }
}
