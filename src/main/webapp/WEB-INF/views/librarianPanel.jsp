<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Kitaphana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/librarianPanel.css"
          rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/librarianPanel.js"></script>
</head>
<body>
<%@include file="header.jsp" %>
<main class="body container">
    <ul class="nav nav-tabs" id="myTab" role="tablist">
        <li class="nav-item">
            <a class="nav-link active" id="users-tab" data-toggle="tab" href="#users" role="tab" aria-controls="users" aria-selected="true">Users</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="documents-tab" data-toggle="tab" href="#documents" role="tab" aria-controls="documents" aria-selected="false">Documents</a>
        </li>
    </ul>
    <div class="tab-content users" id="myTabContent">
        <div class="tab-pane fade show active" id="users" role="tabpanel" aria-labelledby="users-tab">
            <div class="panel container-fluid mx-auto px-0">
                <table class="table table-hover table-dark">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">First</th>
                        <th scope="col">Last</th>
                        <th scope="col">Card Number</th>
                        <th scope="col">Type</th>
                        <th scope="col">Documents</th>
                        <th scope="col">Confirmed</th>
                        <th scope="col">Edit</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <td>${user.card_number}</td>
                            <td>${user.possible_type}</td>
                            <td>
                                <a href=""><img itemprop="image" src="/resources/images/doc.png">
                                </a>
                            </td>
                            <td></td>
                            <td>
                                <a href=""><img itemprop="image" src="/resources/images/pencil.png">
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="form-group">
                    <button class="btn btn-primary btn-block col-12 col-md-3" href="${pageContext.request.contextPath}/addUser">Add new User</button>
                </div>
            </div>
        </div>
        <div class="tab-pane fade" id="documents" role="tabpanel" aria-labelledby="documents-tab">
            <div class="panel container-fluid mx-auto px-0">
                <table class="table table-hover table-dark">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Title</th>
                        <th scope="col">Authors</th>
                        <th scope="col">Type</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Users</th>
                        <th scope="col">Edit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="doc" items="${docs}">
                    <tr>
                        <td>${doc.id}</td>
                        <td>${doc.title}</td>
                        <td>${doc.authors}</td>
                        <td>${doc.type}</td>
                        <td>${doc.amount}</td>
                        <td>
                            <a href=""><img itemprop="image" src="/resources/images/user.png">
                            </a>
                        </td>
                        <td>
                            <a href=""><img itemprop="image" src="/resources/images/pencil.png">
                            </a>
                        </td>

                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="form-group">
                    <button class="btn btn-primary btn-block col-12 col-md-3">Add new Document</button>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>
