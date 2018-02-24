<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Kitaphana</title>
    <%--<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/"/>--%>
    <%--<link rel="stylesheet" href="css/login.css"/>--%>
    <%--<link rel="stylesheet" href="css/common.css"/>--%>
</head>
<div class="container">
    <div class="row">
        <form class="form-singin col-12" action ="/login.do" method="POST">
            <h1 class="logo">KitapHana</h1>
            <h2 class="h3 mb-3 font-weight-normal">Please sign in</h2>
            <input type="text" name="login" class="form-control" placeholder="Phone number" required="" autofocus=""><br>
            <input type="password" name="password" class="form-control" placeholder="Password" required=""><br>
            <div class="checkbox mb-3">
                <label>
                    <input type="checkbox" value="remember-me"> Remember me
                </label>
            </div>
            <button class="btn btn-block btn-primary" type="submit">Sign in</button>
            <h5> or </h5>
            <button class="btn btn-block"> Register </button>
        </form>
    </div>
</div>
</body>
</html>