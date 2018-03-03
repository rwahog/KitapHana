<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="webjars/jquery/3.2.1/jquery.min.js"></script>
    <script src="webjars/popper.js"></script>
    <script src="webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</head>
<body>
<%@ include file = "header.jsp" %>
<main class="body">
    <c:forEach var="document" items="${list}">
        <div class="document-details container-fluid mx-auto px-0">
            <div class="container" *ngIf="document != null; else documentNotFound">
                <div class="document" itemscope
                     itemtype="https://schema.org/CreativeWork">
                        <div class="row">
                        <div class="col-md-4 col-12 document-cover" [class.fixed]="thumbIsFixed">
                            <img itemprop="image"
                                 class="document-thumb-image"
                                 src="resources/images/${document.cover}">
                        </div>
                        <div class="col-md-8 document-info">
                            <div itemprop="headline" class="document-title card-title">
                                <h2 name = "title">${document.title}</h2>
                                <c:set var="bestseller" value="${document.isBest_seller()}"/>
                                <c:if test="${bestseller == 1}">
                                <span class="badge badge-warning">Bestseller</span>
                                </c:if>
                            </div>
                            <ul class="document-authors">
                                <li *ngFor="let author of document.authors"
                                    itemprop="author" class="document-author">${document.authors}</li>
                            </ul>
                            <div itemprop="description"
                                 class="document-description card-text italic">
                                ${document.description}
                            </div>
                            <table class="document-extra col-12">
                                <thead>
                                <tr><th width="50%"></th><th width="50%"></th></tr>
                                </thead>
                                <tbody>
                                <tr *ngIf="document.publisher">
                                    <td class="bold">Publisher</td>
                                    <td>${document.publisher}
                                    </td>
                                </tr>
                                <tr *ngIf="document.year">
                                    <td class="bold">Year of publication</td>
                                    <td>${document.year}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="bold">Type</td>
                                    <td>${document.type}
                                    </td>
                                </tr>
                                <tr>
                                    <td class="bold">Price</td>
                                    <td>${document.price} &#8381;</td>
                                </tr>
                                <tr>
                                    <td class="bold">Copies available</td>
                                    <td>${document.amount}</td>
                                </tr>
                                </tbody>
                            </table>
                            <c:set var="available" value="${document.getAmount()}"/>
                            <c:choose>
                                <c:when test="${available > 0}">
                                <div class="form-group">
                                    <a class="btn btn-primary btn-block col-12 col-md-3" href="/verification">Check out</a>
                                </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="form-group">
                                        <button class="btn btn-block btn-primary col-12 col-md-3" disabled>Check out</button>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
    </main>
</body>
</html>
