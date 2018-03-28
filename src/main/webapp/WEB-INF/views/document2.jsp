<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="/resources/css/common.css"
          rel="stylesheet">
    <link href="/resources/css/document.css"
          rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
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
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<script src="webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="webjars/popper.js"></script>
<script src="webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>