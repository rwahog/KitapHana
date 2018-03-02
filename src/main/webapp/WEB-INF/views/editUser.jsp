<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/editUser.css"
          rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</head>
<body>
<%@include file="header.jsp" %>
<form class="form-registration col-12 col-md-8 col-lg-6 mx-auto" action="" method="POST">

    <div class="container">
        <div class="form-row">
            <div class="form-group col-4">
                <label for="name">Name</label>
                <input type="text" class="form-control" name="name" id = "name" placeholder="Name" value = "Aygul" required="" autofocus="">
            </div>
            <div class="form-group col-4">
                <label for="surname">Surname</label>
                <input type="text" class="form-control" name="surname" id = "surname" placeholder="Surname" value = "Malikova" required="">
            </div>
            <div class="form-group col-4">
                <label for="status">Status</label>
                <select id="status" name = "status" class="form-control" required="">
                    <option selected style="background-color:black" >Choose...</option>
                    <option style="background-color:black">Student</option>
                    <option style="background-color:black">Faculty member</option>
                </select>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-6">
                <label for="phone_number">Phone number</label>
                <input type="text" class="form-control" id = phone_number name="phone_number" placeholder="Phone number" value = "89370037100" required="">
            </div>
            <div class="form-group col-6">
                <label for="email">E-mail</label>
                <input type="email" class="form-control" id="email" name = "email" placeholder="E-mail" value = "a.malikova@innopolis.ru" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-6">
                <label for="password1">Password</label>
                <input type="text" class="form-control" id="password1" name="password1" placeholder="Password" value = "123" required="">
            </div>
            <div class="form-group col-6">
                <label for="password2">Confirm your password</label>
                <input type="text" class="form-control" id="password2" name="password2" placeholder="Confirm password" value = "123" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-6">
                <label for="country">Country</label>
                <input type="text" class="form-control" id="country" name="country" placeholder="Country" value = "Russia" required="">
            </div>
            <div class="form-group col-6">
                <label for="town">Town</label>
                <input type="text" class="form-control" id="town" name = "town" placeholder="Town" value = "Innopolis" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-12">
                <label for="street">Street</label>
                <input type="text" class="form-control" id="street" name = "street" placeholder="Street" value = "Universitetskaya" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-4">
                <label for="house_number">House number</label>
                <input type="text" class="form-control" id="house_number" name = "house_number" placeholder="House number" value = "1" required="">
            </div>
            <div class="form-group col-4">
                <label for="apartment_number">Apartment number</label>
                <input type="text" class="form-control" id="apartment_number"  name = "apartment_number" placeholder="Apartment number" value = "1" required="">
            </div>
            <div class="form-group col-4">
                <label for="postcode">Post code</label>
                <input type="text" class="form-control" id="postcode" name = "postcode" placeholder="Post code" value = "420" required="">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
            </div>
            <div class="form-group col-12 col-md-3">
                <a class="btn btn-block" id = "button" href="">Cancel</a>
            </div>
            <div class="form-group col-12 col-md-3">
                <button class="btn btn-block btn-primary" type="submit">Save</button>
            </div>
        </div>
    </div>
</form>
</body>
</html>