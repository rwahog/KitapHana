<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link rel="icon" href="/resources/images/favicon-32x32.png" type="image/x-icon">
    <link href="${path}webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/verification.css"
          rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<div class="alert alert-success" role="alert">
    <h4 class="text-center">${message}</h4>
    <a class="ref" href="${back}">Back to Kitaphana</a>
</div>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
