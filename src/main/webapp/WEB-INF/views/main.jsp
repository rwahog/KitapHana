<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
    <script type="text/javascript">
        $('card-deck').scrollspy({
            target: '.navbar'
        });
    </script>
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="#">KitapHana</a>
    <form class="form-inline">
        <input class="form-control mr-sm-2" type="search" name="search" placeholder="Search" aria-label="Search">
        <button class="btn btn-outline-success my-8 my-sm-0" type="submit">Search</button>
    </form>
    <div class="dropdown">
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
</nav>


<div class="card-deck">
    <c:forEach var="document" items="${list}">
        <div class="form-group">
            <div class="card col-4">
                <div class="img-container">
                    <img class="card-img-top" src="/resources/images/${document.cover}" alt="Card image cap">
                </div>
                <div class="card-body">
                    <a class="card-title" href=""><c:out value="${document.title}"  /></a>
                    <p class="card-text"><c:out value="${document.authors}" /></p>
                    <p class="card-text"><small class="text-muted"><c:out value="${document.keywords}" /></small></p>
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