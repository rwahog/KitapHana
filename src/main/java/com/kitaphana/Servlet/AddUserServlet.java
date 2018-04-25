package com.kitaphana.Servlet;

import com.kitaphana.Entities.Address;
import com.kitaphana.Entities.Patron;
import com.kitaphana.Entities.User;
import com.kitaphana.Service.RegistrationService;
import com.kitaphana.Service.PatronService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/librarianPanel/addUser", "/librarianPanel/editUser"})
public class AddUserServlet extends HttpServlet {
  RegistrationService service = new RegistrationService();
  PatronService patronService = new PatronService();
  User user;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    HttpSession session = request.getSession(false);
    if (request.getRequestURI().endsWith("editUser")) {
      user = patronService.findUserById(Long.parseLong(request.getParameter("id")));
      request.setAttribute("user", user);
    } else if (request.getRequestURI().endsWith("profile")) {
      user = patronService.findUserByPhoneNumber(((User) session.getAttribute("user"))
              .getPhoneNumber());
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
    int houseNumber = Integer.parseInt(request.getParameter("house_number"));
    int apartmentNumber = Integer.parseInt(request.getParameter("apartment_number"));
    String postcode = request.getParameter("postcode");

    boolean isValidUser = true;
      Patron patron;
      if (request.getParameter("id") != null) {
        Address address = new Address(country, town, street, houseNumber, apartmentNumber,
                postcode);
        patron = new Patron(name, surname, phoneNumber, password1, email, address, status);
        patron.setType(status);
        patron.setId(Long.parseLong(request.getParameter("id")));
        patronService.editPatronInfo(patron, "Librarian");
        response.sendRedirect("/librarianPanel");
//      patronService.editPatronInfo(name, surname, status, phoneNumber, password1, email, country,
//              town, street,
//              houseNumber, apartmentNumber, postcode);
//      response.sendRedirect("/librarianPanel");
    }
  }
}
