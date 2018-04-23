package com.kitaphana.Servlet;

import com.kitaphana.Entities.Employee;
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
    String action = request.getParameter("action");
    String name = request.getParameter("name");
    String surname = request.getParameter("surname");
    String privilege = request.getParameter("privilege");
    String phone_number = request.getParameter("phone_number");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String country = request.getParameter("country");
    String town = request.getParameter("town");
    String street = request.getParameter("street");
    String houseNumber = request.getParameter("house_number");
    String apartmentNumber = request.getParameter("apartment_number");
    String postcode = request.getParameter("postcode");
    if (action.equals("edit")) {

    }
  }
}

