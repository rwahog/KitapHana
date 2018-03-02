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
<%@include file="header.jsp" %>
<div class="cards container">
    <c:forEach var="document" items="${list}">
        <div class="col-lg-4 col-6 card-holder">
            <div class="card">
                <div class="img-container">
                    <img class="card-img-top" src="/resources/images/${document.cover}" alt="Card image cap">
                </div>
                <div class="card-body">
                    <a class="card-title" href="<%=request.getContextPath()%>/document?title=${document.title}" name="title"><c:out value="${document.title}"/></a>
                    

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
