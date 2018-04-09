<%@ page import="com.kitaphana.Service.UserService" %>
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
</head>
<body>
<%@include file="header.jsp" %>
<nav class="navbar search-menu">
    <form class="form-group col-4">
        <input class="form-control" type="search" placeholder="Search" aria-label="Search">
    </form>
    <div class="form-group col-3">
        <select id="searchBy" class="form-control">
            <option selected>Title</option>
            <option>Author</option>
            <option>Keywords</option>
        </select>
    </div>
    <div class="checkbox col-2">
        <label>
            <input type="checkbox" value="notref" id = "notref"> Not reference
        </label>
    </div>
    <div class="checkbox col-2">
        <label>
            <input type="checkbox" value="bestseller" id = "bestseller"> Bestseller
        </label>
    </div>

    <button class="btn btn-outline-success my-8 my-sm-0" type="submit">Search</button>
</nav>

<main class="body container">
    <ul class="nav nav-tabs type" id="myTab" role="tablist" style="position: relative; margin-top: 10px">
        <li class="nav-item docs">
            <a class="nav-link active" id="books-tab" data-toggle="tab" href="#books" role="tab" aria-controls="books" aria-selected="true">Books</a>
        </li>
        <li class="nav-item docs">
            <a class="nav-link" id="journals-tab" data-toggle="tab" href="#journals" role="tab" aria-controls="journals" aria-selected="false">Journals</a>
        </li>
        <li class="nav-item docs">
            <a class="nav-link" id="AVmaterials-tab" data-toggle="tab" href="#AVmaterials" role="tab" aria-controls="AVmaterials" aria-selected="false">AV Materials</a>
        </li>
    </ul>
    <div class="tab-content books" id="myTabContent">
        <div class="tab-pane fade show active" id="books" role="tabpanel" aria-labelledby="books-tab">
            <div class="cards container">
                <c:forEach var="document" items="${list}">
                    <div class="col-lg-4 col-6 card-holder">
                        <div class="card">
                            <div class="img-container">
                                <img class="card-img-top" src="/resources/images/${document.getCover()}" alt="Card image cap">
                            </div>
                            <div class="card-body">
                                <a class="card-title" href="<%=request.getContextPath()%>/document?id=${document.getId()}" name="title">
                                    <c:out value="${document.getTitle()}"/></a>
                                <p class="card-text"><c:out value="${document.getAuthors()}"/></p>
                                <p class="card-text">
                                    <small class="text-muted"><c:out value="${document.getKeywords()}"/></small>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="tab-pane fade" id="journals" role="tabpanel" aria-labelledby="journals-tab">
            <div class="cards container">
                <c:forEach var="document" items="${list}">
                    <div class="col-lg-4 col-6 card-holder">
                        <div class="card">
                            <div class="img-container">
                                <img class="card-img-top" src="/resources/images/${document.getCover()}" alt="Card image cap">
                            </div>
                            <div class="card-body">
                                <a class="card-title" href="<%=request.getContextPath()%>/document?id=${document.getId()}" name="title">
                                    <c:out value="${document.getTitle()}"/></a>
                                <p class="card-text"><c:out value="${document.getAuthors()}"/></p>
                                <p class="card-text">
                                    <small class="text-muted"><c:out value="${document.getKeywords()}"/></small>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div class="tab-pane fade" id="AV" role="tabpanel" aria-labelledby="AV-tab">
            <div class="cards container">
                <c:forEach var="document" items="${list}">
                    <div class="col-lg-4 col-6 card-holder">
                        <div class="card">
                            <div class="img-container">
                                <img class="card-img-top" src="/resources/images/${document.getCover()}" alt="Card image cap">
                            </div>
                            <div class="card-body">
                                <a class="card-title" href="<%=request.getContextPath()%>/document?id=${document.getId()}" name="title">
                                    <c:out value="${document.getTitle()}"/></a>
                                <p class="card-text"><c:out value="${document.getAuthors()}"/></p>
                                <p class="card-text">
                                    <small class="text-muted"><c:out value="${document.getKeywords()}"/></small>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>
    </div>
</main>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>
