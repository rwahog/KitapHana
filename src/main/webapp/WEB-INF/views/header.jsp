<%@ page import="com.kitaphana.Service.MainService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/main.css"
          rel="stylesheet">
    <script src="webjars/js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
    <script src="webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="/main">KitapHana</a>
    <form class="form-inline">
        <input class="form-control mr-sm-2" type="search" name="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success my-8 my-sm-0" type="submit">Search</button>
    </form>
    <div class="rightside">
        <% if (MainService.isLiber){%>
        <div class="nav-item">
            <a class="nav-link" href="/librarianPanel">Librarian panel</a>
        </div>
        <%}%>

        <div class="dropdown nav-item">
            <a class="nav-link dropdown-toggle" href="#"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ${name} ${surname}
            </a>
            <div class="dropdown-menu dropdown-menu-right">
                <a class="dropdown-item" href="/profile">Profile</a>
                <a class="dropdown-item" href="#">My books</a>
                <a class="dropdown-item" href="/login.do">Log out</a>
            </div>
        </div>
    </div>
</nav>
</body>
