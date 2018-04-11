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
            <a class="nav-link active" id="users-tab" data-toggle="tab" href="#users" role="tab" aria-controls="users" aria-selected="true">Users</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="documents-tab" data-toggle="tab" href="#documents" role="tab" aria-controls="documents" aria-selected="false">Documents</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="checkouts-tab" data-toggle="tab" href="#checkouts" role="tab" aria-controls="checkouts" aria-selected="false">Checkouts</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="renews-tab" data-toggle="tab" href="#renews" role="tab" aria-controls="renews" aria-selected="false">Renews</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="returns-tab" data-toggle="tab" href="#returns" role="tab" aria-controls="returns" aria-selected="false">Returns</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" id="waitinglist-tab" data-toggle="tab" href="#waitinglist" role="tab" aria-controls="waitinglist" aria-selected="false">Waiting list</a>
        </li>
    </ul>
    <div class="tab-content users" id="myTabContent" style="position: relative !important; top:0px !important; margin-left: 0px !important;">
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
                        <th scope="col">Waiting</th>
                        <th scope="col">Confirmed</th>
                        <th scope="col">Edit</th>
                        <th scope="col">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.getId()}</td>
                            <td>${user.getName()}</td>
                            <td>${user.getSurname()}</td>
                            <td>${user.getCardNumber()}</td>
                            <td>${user.getPossibleType()}</td>
                            <td>
                                <a href="/docsOfUser?id=${user.getId()}"><img itemprop="image" src="/resources/images/doc.png">
                                </a>
                            </td>
                            <td>
                                <a href="/docsOfUser?id=${user.getId()}"><img itemprop="image" src="/resources/images/clock.png">
                                </a>
                            </td>
                            <td>
                                <c:set var="type" value="${user.getType()}"/>
                                <c:set var="possibleType" value="${user.getPossibleType()}"/>
                                <c:if test="${!type.equals('Patron') && type.equals(possibleType)}">
                                    <img itemprop="image" src="/resources/images/ok.png">
                                </c:if>
                            </td>
                            <td>
                                <a href="/editUser?id=${user.getId()}"><img itemprop="image" src="/resources/images/pencil.png">
                                </a>
                            </td>
                            <td>
                                <a href="/deleteUser?id=${user.getId()}"><img itemprop="image" src="/resources/images/bin.png">
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="form-group">
                    <a class="btn btn-primary btn-block col-12 col-md-3 link" id = "btn" href="/addUser">Add new User</a>
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
                        <th scope="col">Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="doc" items="${docs}">
                    <tr>
                        <td>${doc.getId()}</td>
                        <td>
                            <a class="title" href="/document2?id=${doc.getId()}">${doc.getTitle()}</a>
                        </td>
                        <td>${doc.getAuthorsAsString()}</td>
                        <td>${doc.getType()}</td>
                        <td>${doc.getAmount()}</td>
                        <td>
                            <a href="/holders?id=${doc.getId()}"><img itemprop="image" src="/resources/images/user.png"></a>
                        </td>
                        <td>
                            <a href="/editDoc?id=${doc.getId()}"><img itemprop="image" src="/resources/images/pencil.png"></a>
                        </td>
                        <td>
                            <a href="/deleteDoc?id=${doc.getId()}"><img itemprop="image" src="/resources/images/bin.png"></a>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="form-group">
                    <a class="btn btn-primary btn-block col-12 col-md-3 link"  href="/addDocument">Add new Document</a>
                </div>
            </div>
        </div>
        <div class="tab-pane fade" id="checkouts" role="tabpanel" aria-labelledby="checkouts-tab">
            <div class="panel container-fluid mx-auto px-0">
                <table class="table table-hover table-dark">
                    <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Name Surname</th>
                            <th scope="col">Status</th>
                            <th scope="col">Title</th>
                            <th scope="col">Type</th>
                            <th scope="col">Approve</th>
                            <th scope="col">Disapprove</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${usersCheckouts}">
                            <c:forEach var="doc" items="${user.getDocumentsArray()}">
                                <tr>
                                    <td>${user.getId()}</td>
                                    <td>${user.getName()} ${user.getSurname()}</td>
                                    <td>${user.getType()}</td>
                                    <td>
                                        <a class="title" href="/document2?id=${doc.getId()}">${doc.getTitle()}</a>
                                    </td>
                                    <td>${doc.getType()}</td>
                                    <td>
                                        <form method="POST" action="/librarianPanel">
                                            <input type="hidden" name="checkout_approval" value="1">
                                            <input type="hidden" name="doc_id" value="${doc.getId()}">
                                            <input type="hidden" name="user_id" value="${user.getId()}">
                                            <input type="image" style="border-style: none" src="/resources/images/ok.png"/>
                                        </form>
                                    </td>
                                    <td>
                                        <form method="POST" action="/librarianPanel">
                                            <input type="hidden" name="checkout_disapproval" value="1">
                                            <input type="hidden" name="doc_id" value="${doc.getId()}">
                                            <input type="hidden" name="user_id" value="${user.getId()}">
                                            <input type="image" style="border-style: none" src="/resources/images/remove.png"/>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-pane fade" id="renews" role="tabpanel" aria-labelledby="renews-tab">
            <div class="panel container-fluid mx-auto px-0">
                <table class="table table-hover table-dark">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name Surname</th>
                        <th scope="col">Status</th>
                        <th scope="col">Title</th>
                        <th scope="col">Type</th>
                        <th scope="col">Approve</th>
                        <th scope="col">Disapprove</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${renews}" var="user">
                        <c:forEach items="${user.getDocumentsArray()}" var="doc">
                            <tr>
                                <td>${user.getId()}</td>
                                <td>${user.getName()} ${user.getSurname()}</td>
                                <td>${user.getType()}</td>
                                <td>
                                    <a class="title" href="">${doc.getTitle()}</a>
                                </td>
                                <td>${doc.getType()}</td>
                                <td>
                                    <form method="POST" action="/librarianPanel">
                                        <input type="hidden" name="renew_approval" value="1">
                                        <input type="hidden" name="doc_id" value="${doc.getId()}">
                                        <input type="hidden" name="user_id" value="${user.getId()}">
                                        <input type="image" style="border-style: none" src="/resources/images/ok.png"/>
                                    </form>
                                </td>
                                <td>
                                    <form method="POST" action="/librarianPanel">
                                        <input type="hidden" name="renew_disapproval" value="1">
                                        <input type="hidden" name="doc_id" value="${doc.getId()}">
                                        <input type="hidden" name="user_id" value="${user.getId()}">
                                        <input type="image" style="border-style: none" src="/resources/images/remove.png"/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-pane fade" id="returns" role="tabpanel" aria-labelledby="returns-tab">
            <div class="panel container-fluid mx-auto px-0">
                <table class="table table-hover table-dark">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name Surname</th>
                        <th scope="col">Status</th>
                        <th scope="col">Title</th>
                        <th scope="col">Type</th>
                        <th scope="col">Approve</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${returns}" var="user">
                        <c:forEach items="${user.getDocumentsArray()}" var="doc">
                            <tr>
                                <td>${user.getId()}</td>
                                <td>${user.getName()} ${user.getSurname()}</td>
                                <td>${user.getType()}</td>
                                <td>
                                    <a class="title" href="">${doc.getTitle()}</a>
                                </td>
                                <td>${doc.getType()}</td>
                                <td>
                                    <form method="POST" action="/librarianPanel">
                                        <input type="hidden" name="return_approval" value="1">
                                        <input type="hidden" name="doc_id" value="${doc.getId()}">
                                        <input type="hidden" name="user_id" value="${user.getId()}">
                                        <input type="image" style="border-style: none" src="/resources/images/ok.png"/>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-pane fade" id="waitinglist" role="tabpanel" aria-labelledby="waitinglist-tab">
            <div class="panel container-fluid mx-auto px-0">
                <table class="table table-hover table-dark">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Title</th>
                        <th scope="col">Type</th>
                        <th scope="col">Number of requests</th>
                        <th scope="col">Outstanding Request</th>
                        <th scope="col">Waiters</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${waitingList}" var="doc">
                    <tr>
                        <td>${doc.getId()}</td>
                        <td>
                            <a class="title" href="/document2?id=${doc.getId()}">${doc.getTitle()}</a>
                        </td>
                        <td>${doc.getType()}</td>
                        <td>${doc.getRequests()}</td>
                        <td><input type="image" style="border-style: none" src="/resources/images/ok.png"/></td>
                        <td>
                            <a href="/awaiters?doc_id=${doc.getId()}"><img itemprop="image" src="/resources/images/people.png">
                            </a>
                        </td>

                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/librarianPanel.js"></script>
</body>
</html>
