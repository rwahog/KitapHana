package com.kitaphana.Servlet;

import com.kitaphana.Entities.User;
import com.kitaphana.Service.RegistrationService;
import com.kitaphana.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/librarianPanel/addUser", "/librarianPanel/editUser", "/profile"})
public class AddUserServlet extends HttpServlet {
    RegistrationService service = new RegistrationService();
    UserService userService = new UserService();
    User user;
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getRequestURI().endsWith("editUser") || request.getRequestURI().endsWith("profile")) {
            user = userService.findUserById(Long.parseLong(request.getParameter("id")));
            request.setAttribute("user", user);
        }
        request.getRequestDispatcher("/WEB-INF/views/editUser.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String status = request.getParameter("status");
        String phoneNumber = request.getParameter("phone_number");
        String email = request.getParameter("email");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        String country = request.getParameter("country");
        String town = request.getParameter("town");
        String street = request.getParameter("street");
        String houseNumber = request.getParameter("house_number");
        String apartmentNumber = request.getParameter("apartment_number");
        String postcode = request.getParameter("postcode");

        boolean isValidUser = true;
        isValidUser = service.checkIfPhoneNumberIsUnique(phoneNumber, password1, password2);
        String button = request.getParameter("button");
        if (isValidUser) {
            service.saveUser(name, surname, status, phoneNumber, password1, email, country, town, street, houseNumber, apartmentNumber, postcode);
            response.sendRedirect("/librarianPanel");
        }
        else {
            request.getRequestDispatcher("/WEB-INF/views/addUser.jsp").forward(request, response);
        }
    }
}
