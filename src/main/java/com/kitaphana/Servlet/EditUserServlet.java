package com.kitaphana.Servlet;

import com.kitaphana.Entities.Address;
import com.kitaphana.Entities.User;
import com.kitaphana.Service.LoginService;
import com.kitaphana.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/editUser")
public class EditUserServlet extends HttpServlet {

    UserService userService = new UserService();
    User user = new User();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        user = userService.findUserById(Long.parseLong(request.getParameter("id")));
        request.setAttribute("user", user);
        try {
            new LoginService().redirect(request, response, "editUser", true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        int house_number = Integer.parseInt(request.getParameter("house_number"));
        int apartment_number = Integer.parseInt(request.getParameter("apartment_number"));
        String post_code = request.getParameter("postcode");

        boolean isValid = userService.isValid(Integer.parseInt(request.getParameter("id")), phone_number, password1, password2);
        if (isValid) {
            Address address = new Address(country, town, street, house_number, apartment_number, post_code);
            address.setAddressId(userService.getUserAddressId(Long.parseLong(request.getParameter("id"))));
            User user = new User(name, surname, phone_number, password1, email, address, status);
            user.setId(Long.parseLong(request.getParameter("id")));
            userService.editUserInfo(user);
            response.sendRedirect("/librarianPanel");
        } else {
            request.getRequestDispatcher("WEB-INF/views/editUser.jsp").forward(request, response);
        }
    }
}
