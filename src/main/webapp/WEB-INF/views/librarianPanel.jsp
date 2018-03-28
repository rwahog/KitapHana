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
                        <th scope="col">Waiting</th>
                        <th scope="col">Confirmed</th>
                        <th scope="col">Edit</th>
                        <th scope="col">Delete</th>
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
                                <a href="/docsOfUser?id=${user.id}"><img itemprop="image" src="/resources/images/doc.png">
                                </a>
                            </td>
                            <td>
                                <a href="/docsOfUser?id=${user.id}"><img itemprop="image" src="/resources/images/clock.png">
                                </a>
                            </td>
                            <td>

                            </td>
                            <td>
                                <a href="/editUser?id=${user.id}"><img itemprop="image" src="/resources/images/pencil.png">
                                </a>
                            </td>
                            <td>
                                <a href="/deleteUser?id=${user.id}"><img itemprop="image" src="/resources/images/bin.png">
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="form-group">
                    <a class="btn btn-primary btn-block col-12 col-md-3" id = "btn" href="${pageContext.request.contextPath}/addUser">Add new User</a>
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
                        <td>${doc.id}</td>
                        <td>
                                <a class="title" href="">${doc.title}</a>
                        </td>
                        <td>${doc.authors}</td>
                        <td>${doc.type}</td>
                        <td>${doc.amount}</td>
                        <td>
                            <a href="/holders?id=${doc.id}"><img itemprop="image" src="/resources/images/user.png">
                            </a>
                        </td>
                        <td>
                            <a href="/editDoc?id=${doc.id}"><img itemprop="image" src="/resources/images/pencil.png">
                            </a>
                        </td>
                        <td>
                            <a href="/deleteDoc?id=${doc.id}"><img itemprop="image" src="/resources/images/bin.png">
                            </a>
                        </td>

                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="form-group">
                    <a class="btn btn-primary btn-block col-12 col-md-3" href="/addDocument">Add new Document</a>
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
                        <tr>
                            <td>1</td>
                            <td>Lol lolovich</td>
                            <td>Student</td>
                            <td>
                                <a class="title" href="">Touch of class</a>
                            </td>
                            <td>Book</td>
                            <td>
                                <a href=""><img itemprop="image" src="/resources/images/ok.png">
                                </a>
                            </td>
                            <td>
                                <a href=""><img itemprop="image" src="/resources/images/remove.png">
                                </a>
                            </td>
                        </tr>
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
                    <tr>
                        <td>1</td>
                        <td>Lol lolovich</td>
                        <td>Student</td>
                        <td>
                            <a class="title" href="">Touch of class</a>
                        </td>
                        <td>Book</td>
                        <td>
                            <a href=""><img itemprop="image" src="/resources/images/ok.png">
                            </a>
                        </td>
                        <td>
                            <a href=""><img itemprop="image" src="/resources/images/remove.png">
                            </a>
                        </td>
                    </tr>
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
                        <th scope="col">Disapprove</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>Lol lolovich</td>
                        <td>Student</td>
                        <td>
                            <a class="title" href="">Touch of class</a>
                        </td>
                        <td>Book</td>
                        <td>
                            <a href=""><img itemprop="image" src="/resources/images/ok.png">
                            </a>
                        </td>
                        <td>
                            <a href=""><img itemprop="image" src="/resources/images/remove.png">
                            </a>
                        </td>
                    </tr>
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
                        <th scope="col">Waiters</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1</td>
                        <td>
                            <a class="title" href="">Touch of class</a>
                        </td>
                        <td>Book</td>
                        <td>6</td>
                        <td>
                            <a href=""><img itemprop="image" src="/resources/images/people.png">
                            </a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
</body>
</html>
