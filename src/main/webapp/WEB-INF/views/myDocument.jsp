<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/tables.css"
          rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<main class="body container">
    <ul class="nav nav-tabs" id="myTab" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" id="docs-tab" data-toggle="tab" href="#docs" role="tab" aria-controls="docs" aria-selected="true">My documents</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="waiting-tab" data-toggle="tab" href="#waiting" role="tab" aria-controls="waiting" aria-selected="false">Waitings</a>
        </li>
    </ul>
    <div class="tab-content docs" id="myTabContent">
        <div class="tab-pane fade show active" id="docs" role="tabpanel" aria-labelledby="docs-tab">
            <div class="panel container-fluid mx-auto px-0">
                <table class="table table-hover table-dark docs">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Title</th>
                        <th scope="col">Authors</th>
                        <th scope="col">Type</th>
                        <th scope="col">Deadline</th>
                        <th scope="col">Return</th>
                        <th scope="col">Renew</th>
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
                            <c:choose>
                                <c:when test="${doc.fine != null && doc.fine != 0}">
                                    <td>${doc.fine} &#8381;</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${doc.deadline} days</td>
                                </c:otherwise>
                            </c:choose>
                            <form action="/myDocs" method="POST">
                                <td>
                                    <button type="submit" class="btn btn-primary btn-block" href="/verification" name="button" value="${doc.id}">Return</button>
                                </td>
                            </form>
                            <c:set var="count" value="${count + 1}" scope="page"/>
                            <td>
                                <button type="submit" class="btn btn-primary btn-block" href="/verification" name="button" value="${doc.id}">Renew</button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-pane fade show active" id="waiting" role="tabpanel" aria-labelledby="waiting-tab">
            <div class="panel container-fluid mx-auto px-0">
                <table class="table table-hover table-dark docs">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Title</th>
                        <th scope="col">Authors</th>
                        <th scope="col">Type</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>
                            <a class="title" href="">Touch of class
                            </a>
                        </td>
                        <td>Ber Meyer</td>
                        <td>Book</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
</body>
</html>
