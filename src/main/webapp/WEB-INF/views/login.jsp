<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Kitaphana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/login.css"
          rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row">
            <form class="form-singin col-12" action="/login.do" method="POST">
                <h1 class="logo">KitapHana</h1>
                <h2 class="h3 mb-3 font-weight-normal">Please sign in</h2>
                <input type="text" name="login" class="form-control" placeholder="Phone number" required=""
                       autofocus=""><br>
                <input type="password" name="password" class="form-control" placeholder="Password" required=""><br>
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