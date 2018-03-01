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
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="#">KitapHana</a>
    <form class="form-inline">
        <input class="form-control mr-sm-2" type="search" name="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success my-8 my-sm-0" type="submit">Search</button>
    </form>
    <div class="rightside">
        <% if (MainService.isLiber){%>

        <div class="nav-item">
            <a class="nav-link" href="#">Librarian panel</a>
        </div>
        <%}%>
        <div class="dropdown nav-item">
            <a class="nav-link dropdown-toggle" href="#"
               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                ${name} ${surname}
            </a>
            <div class="dropdown-menu dropdown-menu-right">
                <a class="dropdown-item" href="#">Profile</a>
                <a class="dropdown-item" href="#">My books</a>
                <a class="dropdown-item" href="/login.do">Log out</a>
            </div>
        </div>
    </div>
</nav>


<div class="cards container">
    <c:forEach var="document" items="${list}">
        <div class="col-lg-4 col-6 card-holder">
            <div class="card">
                <div class="img-container">
                    <img class="card-img-top" src="/resources/images/${document.cover}" alt="Card image cap">
                </div>
                <div class="card-body">
                    <a class="card-title" href=""><c:out value="${document.title}"/></a>
                    <p class="card-text"><c:out value="${document.authors}"/></p>
                    <p class="card-text">
                        <small class="text-muted"><c:out value="${document.keywords}"/></small>
                    </p>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<%--<div class="card">--%>
<%--<img class="card-img-top" src="..." alt="Some pic">--%>
<%--<div class="card-body">--%>
<%--<h5 class="card-title" href="">${Title}</h5>--%>
<%--<p class="card-text">&{Author}</p>--%>
<%--<p class="card-text"><small class="text-muted">${Keywords}</small></p>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="card">--%>
<%--<img class="card-img-top" src="..." alt="Some pic">--%>
<%--<div class="card-body">--%>
<%--<h5 class="card-title" href="">${Title}</h5>--%>
<%--<p class="card-text">&{Author}</p>--%>
<%--<p class="card-text"><small class="text-muted">${Keywords}</small></p>--%>
<%--</div>--%>
<%--</div>--%>
</body>
</html>