<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registration</title>
    <link rel="icon" href="/resources/images/favicon-16x16.png" type="image/x-icon">
    <link href="${path}webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/forms.css"
          rel="stylesheet">
</head>
<body>
<form class="form-registration col-12 col-md-8 col-lg-6 mx-auto" action="/registration" method="POST">
    <h1 class="logo">KitapHana</h1>
    <div class="container" style="margin-top: 0">
        <div class="form-row">
            <div class="form-group col-4">
                <label for="name">Name</label>
                <input type="text" class="form-control" name="name" value="<%=String.valueOf(request.getAttribute("name"))%>" id = "name" placeholder="Name" required=""  autofocus="">
            </div>
            <div class="form-group col-4">
                <label for="surname">Surname</label>
                <input type="text" class="form-control" name="surname" value="<%=request.getAttribute("surname")%>" id = "surname" placeholder="Surname" required="">
            </div>
            <div class="form-group col-4">
                <label for="status">Status</label>
                <select id="status" name="status"  class="form-control" required="">
                    <option>Student</option>
                    <option>Instructor</option>
                    <option>Teacher Assistant</option>
                    <option>Professor</option>
                    <option>Visiting Professor</option>
                </select>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-6">
                <label for="phone_number">Phone number</label>
                <c:choose>
                    <c:when test='${requestScope["errorBoth"] != null}'>
                        <input type="text" class="form-control" id="phone_number" name="phone_number" value="<%=request.getAttribute("phone_number")%>" placeholder="Phone number" style="border-color: #76050f" required="">
                        <h5 style="color: #cccccc"><%=request.getAttribute("errorPhone")%></h5>
                    </c:when>
                    <c:when test='${requestScope["errorPhone"] != null}'>
                        <input type="text" class="form-control" id="phone_number" name="phone_number" value="<%=request.getAttribute("phone_number")%>" placeholder="Phone number" style="border-color: #76050f" required="">
                        <h5 style="color: #cccccc"><%=request.getAttribute("errorPhone")%></h5>
                    </c:when>
                    <c:otherwise>
                        <input type="text" class="form-control" id="phone_number" name="phone_number" value="<%=request.getAttribute("phone_number")%>" placeholder="Phone number" required="">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="form-group col-6">
                <label for="email">E-mail</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="E-mail" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-6">
                <label for="password1">Password</label>
                <c:choose>
                    <c:when test='${requestScope["errorBoth"] != null}'>
                        <input type="password" class="form-control" id="password1" name="password1" value="<%=request.getAttribute("password1")%>" placeholder="Password" style="border-color: #76050f" required="">
                        <h3 style="color: #cccccc"><%=request.getAttribute("errorPassword")%></h3>
                    </c:when>
                    <c:when test='${requestScope["errorPassword"] != null}'>
                        <input type="password" class="form-control" id="password1" name="password1" value="<%=request.getAttribute("password1")%>" placeholder="Password" style="border-color: #76050f" required="">
                        <h3 style="color: #cccccc"><%=request.getAttribute("errorPassword")%></h3>
                    </c:when>
                    <c:otherwise>
                        <input type="password" class="form-control" id="password1" name="password1" value="<%=request.getAttribute("password1")%>" placeholder="Password" required="">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="form-group col-6">
                <label for="password2">Confirm your password</label>
                <c:choose>
                    <c:when test='${requestScope["errorBoth"] != null}'>
                        <input type="password" class="form-control" id="password2" name="password2" value="<%=request.getAttribute("password2")%>" placeholder="Confirm your password" style="border-color: #76050f" required="">
                    </c:when>
                    <c:when test='${requestScope["errorPassword"] != null}'>
                        <input type="password" class="form-control" id="password2" name="password2" value="<%=request.getAttribute("password2")%>" placeholder="Confirm your password" style="border-color: #76050f" required="">
                    </c:when>
                    <c:otherwise>
                        <input type="password" class="form-control" id="password2" name="password2" value="<%=request.getAttribute("password2")%>" placeholder="Confirm your password" required="">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-6">
                <label for="country">Country</label>
                <input type="text" class="form-control" id="country" name="country" value="<%=request.getAttribute("country")%>" placeholder="Country" required="">
            </div>
            <div class="form-group col-6">
                <label for="town">Town</label>
                <input type="text" class="form-control" id="town" name="town" value="<%=request.getAttribute("town")%>" placeholder="Town" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-12">
                <label for="street">Street</label>
                <input type="text" class="form-control" id="street" name="street" value="<%=request.getAttribute("street")%>" placeholder="Street" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-4">
                <label for="house_number">House number</label>
                <input type="text" class="form-control" id="house_number" data-type="number" name="house_number" value="<%=request.getAttribute("house_number")%>" placeholder="House number" required="">
            </div>
            <div class="form-group col-4">
                <label for="apartment_number">Apartment number</label>
                <input type="text" class="form-control" id="apartment_number" data-type="number" name="apartment_number" value="<%=request.getAttribute("apartment_number")%>" placeholder="Apartment number" required="">
            </div>
            <div class="form-group col-4">
                <label for="postcode">Post code</label>
                <input type="text" class="form-control" id="postcode" data-type="number" name="postcode" value="<%=request.getAttribute("postcode")%>" placeholder="Post code" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-md-6">
            </div>
            <div class="form-group col-12 col-md-3">
                <a class="btn btn-block" id = "button" href="/login">Cancel</a>
            </div>
            <div class="form-group col-12 col-md-3">
                <button class="btn btn-block btn-primary" type="submit">Submit</button>
            </div>
        </div>
    </div>
</form>
<script src="/resources/js/jquery-3.2.1.slim.min.js"></script>
<script src="/resources/js/jquery.maskedinput.min.js"></script>
<script src="/resources/js/main.js"></script>
<script>
    $('input').each(function (i, el) {
        el = $(el);
        if (el.val() == 'null') {
            el.val('');
        }
    })
</script>
</body>
</html>
