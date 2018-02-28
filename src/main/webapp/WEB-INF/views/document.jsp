<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/document.css"
          rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="#">KitapHana</a>
    <div class="dropdown">
        <a class="nav-link dropdown-toggle" href="#"
           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Aygul Malikova
        </a>
        <div class="dropdown-menu dropdown-menu-right">
            <a class="dropdown-item" href="#">Profile</a>
            <a class="dropdown-item" href="#">Log out</a>
        </div>
    </div>
</nav>
<main class="body">
    <div class="document-details container-fluid mx-auto px-0">
        <div class="row" *ngIf="document != null; else documentNotFound">
            <div class="document" itemscope
                 itemtype="https://schema.org/CreativeWork">
                <div class="row">
                    <div class="col-md-4 col-12 document-cover" [class.fixed]="thumbIsFixed">
                        <img itemprop="image"
                             class="document-thumb-image"
                             src="../images/touchOfClass.jpg">
                    </div>
                    <div class="col-md-8 document-info">
                        <div itemprop="headline" class="document-title card-title">
                            <h2>Touch Of Class</h2>
                            <span class="badge badge-warning">Bestseller</span>
                        </div>
                        <ul class="document-authors">
                            <li *ngFor="let author of document.authors"
                                itemprop="author" class="document-author">Bertrand Meyer</li>
                        </ul>
                        <div itemprop="description"
                             class="document-description card-text italic">
                            Learning to Program Well with Objects and Contracts
                        </div>
                        <table class="document-extra col-12">
                            <thead>
                            <tr><th width="50%"></th><th width="50%"></th></tr>
                            </thead>
                            <tbody>
                            <tr *ngIf="document.publisher">
                                <td class="bold">Publisher</td>
                                <td>Springer
                                </td>
                            </tr>
                            <tr *ngIf="document.year">
                                <td class="bold">Year of publication</td>
                                <td>2009
                                </td>
                            </tr>
                            <tr>
                                <td class="bold">Type</td>
                                <td>Book
                                </td>
                            </tr>
                            <tr>
                                <td class="bold">Price</td>
                                <td>800 ₽</td>
                            </tr>
                            <tr>
                                <td class="bold">Copies available</td>
                                <td>3</td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="form-group">
                            <button class="btn btn-primary btn-block col-12 col-md-3">Check out</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

</main>
</body>
</html>