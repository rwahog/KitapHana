<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:set var="path" value="${request.getContextPath()}"/>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="${path}/webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${path}/resources/css/common.css"
          rel="stylesheet">
    <link href="${path}/resources/css/list.css"
          rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<main class="body container">
    <div class="panel container-fluid mx-auto px-0">
        <h2>
            Documents ${name} ${surname}
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
                <td>${doc.getId()}</td>
                <td>${doc.getTitle()}</td>
                <td>${doc.getAuthorsAsString()}</td>
                <td>${doc.getType()}</td>
                <c:choose>
                    <c:when test="${doc.fine != null && doc.fine != 0}">
                    <td>${doc.getFine()} &#8381;</td>
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
<script src="${path}/resources/js/jquery-3.2.1.slim.min.js"></script>
<script src="${path}/resources/js/popper.min.js"></script>
<script src="${path}/resources/js/bootstrap.min.js"></script>
<script src="${path}/resources/js/masonry.pkgd.min.js"></script>
<script src="${path}/resources/js/main.js"></script>
<script>
    function goBack() {
        window.history.back();
    }
</script>
</body>
</html>
