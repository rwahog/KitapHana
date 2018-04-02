<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/list.css"
          rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<main class="body container">
    <div class="panel container-fluid mx-auto px-0">
        <h2>
            Documents ${user.name} ${user.surname}
        </h2>
        <table class="table table-hover table-dark documents">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Title</th>
                <th scope="col">Authors</th>
                <th scope="col">Type</th>
                <th scope="col">Deadline</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="doc" items="${docs}">
                <tr>
                    <td>${doc.id}</td>
                    <td>${doc.title}</td>
                    <td>${doc.authors}</td>
                    <td>${doc.type}</td>
                    <c:choose>
                        <c:when test="${doc.fine != null && doc.fine != 0}">
                            <td>${doc.fine} &#8381;</td>
                        </c:when>
                        <c:otherwise>
                            <td>${doc.deadline} days</td>
                        </c:otherwise>
                    </c:choose>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="form-group col-12 col-md-3">
        <a class="btn btn-block" id = "button" onclick="goBack()">Cancel</a>
    </div>
</main>
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