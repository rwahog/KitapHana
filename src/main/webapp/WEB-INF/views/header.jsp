<%@ page import="com.kitaphana.Service.UserService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link rel="icon" href="/resources/images/favicon-16x16.png" type="image/x-icon">
    <c:set var="path" value="${application.getRealPath(\"/\")}"/>
    <link href="${path}/webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/main.css"
          rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="/main">KitapHana</a>
    <div class="rightside">
        <%if (request.getSession(false) != null) {
                if (request.getSession(false).getAttribute("role") != null) {
            if (!request.getSession(false).getAttribute("role").equals("patron")) {%>
            <div class="nav-item" >
            <a class="nav-link" href = "/librarianPanel" > Librarian panel</a >
            </div >
        <%}}}%>

        <div class="dropdown nav-item">
            <a class="nav-link dropdown-toggle" href="#"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ${sessionScope["user"].getName()} ${sessionScope["user"].getSurname()}
            </a>
            <div class="dropdown-menu dropdown-menu-right">
                <a class="dropdown-item" href="/profile">Profile</a>
                <%if (session.getAttribute("librarian") == null) {%>
                    <a class="dropdown-item" href="/myDocs">My Docs</a>
                <%}%>
                <a class="dropdown-item" href="/logout">Log out</a>
            </div>
        </div>
    </div>
</nav>
<script src="webjars/js/jquery-3.2.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
</body>
</html>
