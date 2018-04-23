package com.kitaphana.Servlet;

import com.kitaphana.Entities.Address;
import com.kitaphana.Entities.Employee;
import com.kitaphana.Entities.Librarian;
import com.kitaphana.Service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/librarianPanel/admin/editLibrarian", "/librarianPanel/admin/deleteLibrarian", "/librarianPanel/admin/addLibrarian"})
public class AdminServlet extends HttpServlet {

  AdminService adminService = new AdminService();
  Employee employee;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    if (request.getRequestURI().endsWith("deleteLibrarian")) {
      adminService.deleteLibrarian(request.getParameter("id"));
      response.sendRedirect("/librarianPanel");
    } else if (request.getRequestURI().endsWith("editLibrarian")) {
      employee = adminService.findById(request.getParameter("id"));
      request.setAttribute("action", "edit");
      request.setAttribute("employee", employee);
    } else if (request.getRequestURI().endsWith("addLibrarian")) {
      request.setAttribute("action", "add");
    }
    request.getRequestDispatcher("/WEB-INF/views/librarian.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    long librarianId = Long.parseLong(request.getParameter("id"));
    String action = request.getParameter("action");
    String name = request.getParameter("name");
    String surname = request.getParameter("surname");
    int privilege = Integer.parseInt(request.getParameter("privilege"));
    String phone_number = request.getParameter("phone_number");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String country = request.getParameter("country");
    String town = request.getParameter("town");
    String street = request.getParameter("street");
    int houseNumber = Integer.parseInt(request.getParameter("house_number"));
    int apartmentNumber = Integer.parseInt(request.getParameter("apartment_number"));
    String postcode = request.getParameter("postcode");
    Address address = new Address(country, town, street, houseNumber,
            apartmentNumber, postcode);
    Librarian librarian = new Librarian(name, surname, privilege, phone_number,
            email, password, address);
    if (action.equals("add")) {
      adminService.addLibrarian(librarian);
      response.sendRedirect("/librarianPanel");
    } else if (action.equals("edit")) {
      librarian.setId(librarianId);
      adminService.updateLibrarian(librarian);
      response.sendRedirect("/librarianPanel");
    }
  }
}

