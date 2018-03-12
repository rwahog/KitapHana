package com.kitaphana.Servlet;

import com.kitaphana.Service.LoginService;
import com.kitaphana.Service.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private RegistrationService service = new RegistrationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        new LoginService().redirect(request, response, "registration");
        //
        // request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
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

        boolean isValidUser = true;
        isValidUser = service.checkIfPossibleToRegister(phone_number, password1, password2);
        if (isValidUser) {
            service.saveUser(name, surname, status, phone_number, password1, email, country, town, street, house_number, apartment_number, post_code);
            request.getSession().setAttribute("name", name);
            request.getSession().setAttribute("surname", surname);
            request.getSession().setAttribute("id", service.getUserId(phone_number));
            request.getSession().setAttribute("login", phone_number);
            request.getSession().setAttribute("password", password1);
            response.sendRedirect("/main");
        }
        else {
            request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
        }
    }
}
