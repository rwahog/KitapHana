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
    <link href="${pageContext.request.contextPath}/resources/css/mainPage.css"
          rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

</head>
<body>
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="#">KitapHana</a>
    <div class="dropdown">
        <a class="nav-link dropdown-toggle" href="#"
           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            ${login}
        </a>
        <div class="dropdown-menu dropdown-menu-right">
            <a class="dropdown-item" href="#">Profile</a>
            <a class="dropdown-item" href="#">My Books</a>
            <a class="dropdown-item" href="/login.do">Log out</a>
        </div>
    </div>
</nav>
<section>
    <ul class="documents" action = "/mainpage" method="post">
        <p *ngIf="document && documents.length === 0">No documents found for this category</p>
        <app-document-item *ngFor="let document of documents" [document]="document"></app-document-item>
    </ul>
</section>
</body>

</html>