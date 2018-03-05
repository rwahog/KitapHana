<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/myDocuments.css"
          rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</head>
<body>
<%@include file="header.jsp" %>
<main class="body container">
    <div class="panel container-fluid mx-auto px-0">
        <h2>
            My documents
        </h2>
        <table class="table table-hover table-dark documents">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Title</th>
                <th scope="col">Authors</th>
                <th scope="col">Type</th>
                <th scope="col">Deadline</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:set var="count" value="1" scope="page" />
            <c:forEach var="doc" items="${myDocs}">
            <tr>
                <td>${count}</td>
                <td>
                    <a class="title" href="">${doc.getTitle()}
                    </a>
                </td>
                <td>${doc.authors}</td>
                <td>${doc.type}</td>
                <td>5 days</td>
                <form action="/myDocs" method="POST">
                <td>
                    <button type="submit" class="btn btn-primary btn-block" href="/verification" name="button" value="${doc.id}">Return</button>
                </td>
                </form>
                <c:set var="count" value="${count + 1}" scope="page"/>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>
