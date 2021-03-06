<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>KitapHana</title>
	<link rel="icon" href="/resources/images/favicon-16x16.png" type="image/x-icon">
	<c:set var="path" value="${application.getRealPath(\"/\")}"/>
	<link href="${path}/webjars/bootstrap/4.0.0/css/bootstrap.min.css"
				rel="stylesheet">
	<link href="/resources/css/common.css"
				rel="stylesheet">
	<link href="/resources/css/forms.css"
				rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<form class="form-registration col-12 col-md-8 col-lg-6 mx-auto" action="" method="POST">
	<div class="container">
		<div class="form-row">
			<div class="form-group col-4">
				<label for="name">Name</label>
				<input type="text" class="form-control" name="name" id="name" placeholder="Name"
							 value="${user.getName()}" required="" autofocus="">
			</div>
			<div class="form-group col-4">
				<label for="surname">Surname</label>
				<input type="text" class="form-control" name="surname" id="surname" placeholder="Surname"
							 value="${user.getSurname()}" required="">
			</div>
			<div class="form-group col-4">
				<label for="status">Status</label>
				<select id="status" name="status" class="form-control" required="">
					<c:set var="status" value="${user.getType()}"/>
					<c:choose>
						<c:when test="${status == 'Patron'}">
							<option value="Patron" selected style="background-color:black">Patron</option>
							<option value="Student" style="background-color:black">Student</option>
							<option value="Instructor" style="background-color:black">Instructor</option>
							<option value="Teacher Assistant" style="background-color:black">Teacher Assistant</option>
							<option value="Visiting Professor" style="background-color:black">Visiting Professor</option>
							<option value="Professor" style="background-color:black">Professor</option>
						</c:when>
						<c:when test="${status == 'Teacher Assistant'}">
							<option value="Teacher Assistant" selected style="background-color:black">Teacher Assistant</option>
							<option value="Student" style="background-color:black">Student</option>
							<option value="Instructor" style="background-color:black">Instructor</option>
							<option value="Visiting Professor" style="background-color:black">Visiting Professor</option>
							<option value="Professor" style="background-color:black">Professor</option>
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
				<input type="text" class="form-control" id=phone_number name="phone_number"
							 placeholder="Phone number" value="${user.getPhoneNumber()}" required="">
			</div>
			<div class="form-group col-6">
				<label for="email">E-mail</label>
				<input type="email" class="form-control" id="email" name="email" placeholder="E-mail"
							 value="${user.getEmail()}" required="">
			</div>
		</div>

		<div class="form-row">
			<div class="form-group col-6">
				<label for="password1">Password</label>
				<input type="text" class="form-control" id="password1" name="password1"
							 placeholder="Password" value="${user.getPassword()}" required="">
			</div>
			<div class="form-group col-6">
				<label for="password2">Confirm your password</label>
				<input type="text" class="form-control" id="password2" name="password2"
							 placeholder="Confirm password" value="${user.getPassword()}" required="">
			</div>
		</div>
		<c:set value="${user.getAddress()}" var="address"/>
		<div class="form-row">
			<div class="form-group col-6">
				<label for="country">Country</label>
				<input type="text" class="form-control" id="country" name="country" placeholder="Country"
							 value="${address.getCountry()}" required="">
			</div>
			<div class="form-group col-6">
				<label for="town">Town</label>
				<input type="text" class="form-control" id="town" name="town" placeholder="Town"
							 value="${address.getTown()}" required="">
			</div>
		</div>

		<div class="form-row">
			<div class="form-group col-12">
				<label for="street">Street</label>
				<input type="text" class="form-control" id="street" name="street" placeholder="Street"
							 value="${address.getStreet()}" required="">
			</div>
		</div>

		<div class="form-row">
			<div class="form-group col-4">
				<label for="house_number">House number</label>
				<input type="text" class="form-control" id="house_number" name="house_number"
							 placeholder="House number" value="${address.getHouseNumber()}" required="">
			</div>
			<div class="form-group col-4">
				<label for="apartment_number">Apartment number</label>
				<input type="text" class="form-control" id="apartment_number" name="apartment_number"
							 placeholder="Apartment number" value="${address.getApartmentNumber()}" required="">
			</div>
			<div class="form-group col-4">
				<label for="postcode">Post code</label>
				<input type="text" class="form-control" id="postcode" name="postcode"
							 placeholder="Post code" value="${address.getPostcode()}" required="">
			</div>
		</div>
		<div class="form-row">
			<div class="form-group col-md-6">
			</div>
			<div class="form-group col-12 col-md-3">
				<a class="btn btn-block" id="button" onclick="goBack()">Cancel</a>
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
<script>
  var check = function () {
    if (document.getElementById('password1').value !==
        document.getElementById('password2').value) {
      document.getElementById('password1').setAttribute("pattern", "");
      document.getElementById('password1').setAttribute("title", 'Passwords do not match.');
    } else if (document.getElementById('password1').value.length < 6) {
      document.getElementById('password1').setAttribute("pattern", "");
      document.getElementById('password1').setAttribute("title", 'Password must be at least 6 characters.');
    } else {
      document.getElementById('password1').removeAttribute("pattern");
      document.getElementById('password1').removeAttr("title");
    }
  };
</script>
<script>
  var checkUnique = function () {
    var userPhone = document.getElementById('phone_number').value.replace(/[^0-9]+/g, '');
    var array = [];
    var i = 0;
    <c:forEach items="${info}" var="item">
    array.push("${item}");
    </c:forEach>
    if (array.indexOf(userPhone) > -1) {
      document.getElementById('phone_number').setAttribute("pattern", "");
      document.getElementById('phone_number').setAttribute("title", 'Such phone number already exists.\'');
    } else {
      document.getElementById('phone_number').removeAttribute("pattern");
      document.getElementById('phone_number').removeAttr("title");
    }
  }
</script>
</body>
</html>
