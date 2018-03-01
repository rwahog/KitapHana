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
                        <th scope="col">Confirmed</th>
                        <th scope="col">Edit</th>
                    </tr>
                    </thead>
                    <tbody>
                    <td>1</td>
                    <td>Aygul</td>
                    <td>Malikova</td>
                    <td>37383920</td>
                    <td>Student</td>
                    <td>
                        <img itemprop="image" src="/resources/images/ok.png">
                    </td>
                    <td>
                        <a href=""><img itemprop="image" src="/resources/images/pencil.png">
                        </a>
                    </td>
                    </td>
                    </tr>

                    <tr>
                        <td>2</td>
                        <td>Jacob</td>
                        <td>Thornton</td>
                        <td>27585915</td>
                        <td>Faculty member</td>
                        <td></td>
                        <td>
                            <a href=""><img itemprop="image" src="/resources/images/pencil.png">
                            </a>
                        </td>
                    </tr>
                    </tbody>
                </table>
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
                        <th scope="col">Edit</th>
                    </tr>
                    </thead>
                    <tbody>

                    <td>1</td>
                    <td>Touch of Class</td>
                    <td>Bertrand Meyer</td>
                    <td>Book</td>
                    <td>2</td>
                    <td>
                        <a href=""><img itemprop="image" src="/resources/images/pencil.png">
                        </a>
                    </td>
                    </td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>Algothims</td>
                        <td>Cormen</td>
                        <td>Book</td>
                        <td>3</td>
                        <td>
                            <a href=""><img itemprop="image" src="/resources/images/pencil.png">
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