<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/forms.css"
          rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<form class="form-registration col-12 col-md-8 col-lg-6 mx-auto" action="" method="POST">
    <c:set var="user" value="${user}"/>
    <c:set var="type" value="${user.getType()}"/>
    <c:set var="chat_id" value="${user.getChatId()}"/>
    <c:if test="${chat_id==0 && !type.equals('Librarian')}">
        <script>
            alert("Join our Telegram Bot @KitapHanaBot to receive notifications!");
        </script>
    </c:if>
    
    <div class="container">
        <div class="form-row">
            <div class="form-group col-4">
                <label for="name">Name</label>
                <input type="text" class="form-control" name="name" id = "name" placeholder="Name" value = "${user.getName()}" required="" autofocus="">
            </div>
            <div class="form-group col-4">
                <label for="surname">Surname</label>
                <input type="text" class="form-control" name="surname" id = "surname" placeholder="Surname" value = "${user.getSurname()}" required="">
            </div>
            <div class="form-group col-4">
                <label for="status">Status</label>
                <select id="status" name = "status" class="form-control" required="">
                    <c:set var="status" value="${user.getType()}"/>
                    <c:choose>
                        <c:when test="${status == 'Librarian'}">
                            <option selected style="background-color:black">Librarian</option>
                            <option style="background-color:black">Student</option>
                            <option style="background-color:black">Instructor</option>
                            <option style="background-color:black">Teacher Assistant</option>
                            <option style="background-color:black">Visiting Professor</option>
                            <option style="background-color:black">Professor</option>
                        </c:when>
                        <c:when test="${status == 'Teacher Assistant'}">
                            <option selected style="background-color:black">Teacher Assistant</option>
                            <option style="background-color:black">Student</option>
                            <option style="background-color:black">Instructor</option>
                            <option style="background-color:black">Visiting Professor</option>
                            <option style="background-color:black">Professor</option>
                        </c:when>
                        <c:when test="${status == 'Instructor'}">
                            <option selected style="background-color:black">Instructor</option>
                            <option style="background-color:black">Student</option>
                            <option style="background-color:black">Teacher Assistant</option>
                            <option style="background-color:black">Visiting Professor</option>
                            <option style="background-color:black">Professor</option>
                        </c:when>
                        <c:when test="${status == 'Visiting Professor'}">
                            <option selected style="background-color:black">Visiting Professor</option>
                            <option style="background-color:black">Student</option>
                            <option style="background-color:black">Instructor</option>
                            <option style="background-color:black">Teacher Assistant</option>
                            <option style="background-color:black">Professor</option>
                        </c:when>
                        <c:when test="${status == 'Professor'}">
                            <option selected style="background-color:black">Professor</option>
                            <option style="background-color:black">Student</option>
                            <option style="background-color:black">Instructor</option>
                            <option style="background-color:black">Teacher Assistant</option>
                            <option style="background-color:black">Visiting Professor</option>
                        </c:when>
                        <c:otherwise>
                            <option selected style="background-color:black">Student</option>
                            <option style="background-color:black">Instructor</option>
                            <option style="background-color:black">Teacher Assistant</option>
                            <option style="background-color:black">Visiting Professor</option>
                            <option style="background-color:black">Professor</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-6">
                <label for="phone_number">Phone number</label>
                <input type="text" class="form-control" id = phone_number name="phone_number" placeholder="Phone number" value = "${user.getPhoneNumber()}" required="">
            </div>
            <div class="form-group col-6">
                <label for="email">E-mail</label>
                <input type="email" class="form-control" id="email" name = "email" placeholder="E-mail" value = "${user.getEmail()}" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-6">
                <label for="password1">Password</label>
                <input type="text" class="form-control" id="password1" name="password1" placeholder="Password" value = "${user.getPassword()}" required="">
            </div>
            <div class="form-group col-6">
                <label for="password2">Confirm your password</label>
                <input type="text" class="form-control" id="password2" name="password2" placeholder="Confirm password" value = "${user.getPassword()}" required="">
            </div>
        </div>
        <c:set var="address" value="${user.getAddress()}"/>
        <div class="form-row">
            <div class="form-group col-6">
                <label for="country">Country</label>
                <input type="text" class="form-control" id="country" name="country" placeholder="Country" value = "${address.getCountry()}" required="">
            </div>
            <div class="form-group col-6">
                <label for="town">Town</label>
                <input type="text" class="form-control" id="town" name = "town" placeholder="Town" value = "${address.getTown()}" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-12">
                <label for="street">Street</label>
                <input type="text" class="form-control" id="street" name = "street" placeholder="Street" value = "${address.getStreet()}" required="">
            </div>
        </div>

        <div class="form-row">
            <div class="form-group col-4">
                <label for="house_number">House number</label>
                <input type="text" class="form-control" id="house_number" name = "house_number" placeholder="House number" value = "${address.getHouseNumber()}" required="">
            </div>
            <div class="form-group col-4">
                <label for="apartment_number">Apartment number</label>
                <input type="text" class="form-control" id="apartment_number"  name = "apartment_number" placeholder="Apartment number" value = "${address.getApartmentNumber()}" required="">
            </div>
            <div class="form-group col-4">
                <label for="postcode">Post code</label>
                <input type="text" class="form-control" id="postcode" name = "postcode" placeholder="Post code" value = "${address.getPostcode()}" required="">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
            </div>
            <div class="form-group col-12 col-md-3">
                <a class="btn btn-block" id = "button" onclick="goBack()">Cancel</a>
            </div>
            <div class="form-group col-12 col-md-3">
                <button class="btn btn-block btn-primary" type="submit">Save</button>
            </div>
        </div>
    </div>
</form>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
<script>
    function goBack() {
        window.history.back();
    }
</script>
</body>
</html>
