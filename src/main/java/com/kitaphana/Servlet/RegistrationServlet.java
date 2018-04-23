package com.kitaphana.Servlet;

import com.kitaphana.Entities.Address;
import com.kitaphana.Entities.Patron;
import com.kitaphana.Service.DBService;
import com.kitaphana.Service.PatronService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
  private PatronService service = new PatronService();
  private DBService dbService = new DBService();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    request.setAttribute("checker", dbService.findAllPhones());
    request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws IOException, ServletException {
    String name = request.getParameter("name");
    String surname = request.getParameter("surname");
    String status = request.getParameter("status");
    String phoneNumber = request.getParameter("phone_number").replaceAll("[^0-9]", "");
    String email = request.getParameter("email");
    String password1 = request.getParameter("password1");
    String password2 = request.getParameter("password2");
    String country = request.getParameter("country");
    String town = request.getParameter("town");
    String street = request.getParameter("street");
    int houseNumber = Integer.parseInt(request.getParameter("house_number"));
    int apartmentNumber = Integer.parseInt(request.getParameter("apartment_number"));
    String postcode = request.getParameter("postcode");

//    if (!isValidPassword && !isValidUser) {
//      request.setAttribute("errorBoth", "Your password is not matching or it too short " +
//              "and the user is already exits with this prone number");
//      request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
//    } else if (!isValidPassword && isValidUser) {
//      request.setAttribute("errorPassword", "Your password is not matching or it too short");
//      request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
//    } else if (!isValidUser && isValidPassword) {
//      request.setAttribute("errorPhone", "Your password is not matching or it too short");
//      request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request, response);
//    } else {
//      Address address = new Address(country, town, street, houseNumber, apartmentNumber, postcode);
//      Patron patron = new Patron(name, surname, phoneNumber, password1, email, address, status);
//      service.savePatron(patron);
//      HttpSession session = request.getSession();
//      patron = service.findUserByPhoneNumber(phoneNumber);
//      session.setAttribute("user", patron);
////            String message = "New user " + user.getName() + " " + user.getSurname() + " (id: " + service.getUserId(phoneNumber) + ")" + " is waiting for type confirming.";
////            dbService.sendMessageToLibrarians(message);
//      response.sendRedirect("/main");
    }
  }