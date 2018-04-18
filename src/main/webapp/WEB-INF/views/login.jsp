<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Kitaphana</title>
    <link rel="icon" href="/resources/images/favicon-32x32.png" type="image/x-icon">
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="/resources/css/common.css"
          rel="stylesheet">
    <link href="/resources/css/login.css"
          rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row">
            <form class="form-singin col-12" action="/login" method="POST">
                <h1 class="logo">KitapHana</h1>
                <h2 class="h3 mb-3 font-weight-normal">Please sign in</h2>
                <c:choose>
                    <c:when test='${requestScope["errorMessage"] != null}'>
                        <input type="text" id ="email" name="login" class="form-control" placeholder="Phone number" required="" autofocus="" style="border-color: #76050f"><br>
                        <input type="password" id = "password" name="password" class="form-control" placeholder="Password" required="" style="border-color: #76050f"><br>
                        <p style="color: #cc1010; font-weight: bold; text-align: center"><%=request.getAttribute("errorMessage")%></p>
                    </c:when>
                    <c:otherwise>
                        <input type="text" id ="email" name="login" class="form-control" placeholder="Phone number" required="" autofocus=""><br>
                        <input type="password" id = "password" name="password" class="form-control" placeholder="Password" required=""><br>
                    </c:otherwise>
                </c:choose>
                <div class="checkbox mb-3">
                    <label>
                        <input type="checkbox" name="remember" value="remember-me"> Remember me
                    </label>
                </div>
                <button class="btn btn-block btn-primary" type="submit"> Sign in</button>
                <h5> or </h5>
                <a class="btn btn-block" id="button" href="/registration">Register</a>
            </form>
        </div>
    </div>
</body>
</html>