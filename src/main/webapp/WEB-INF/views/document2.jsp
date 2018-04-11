<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
<body>
<%@include file="header.jsp" %>
<main class="body">
    <c:set var="document" value="${document}"/>
    <div class="document-details container-fluid mx-auto px-0">
        <div class="container" *ngIf="document != null; else documentNotFound">
            <div class="document" itemscope
                 itemtype="https://schema.org/CreativeWork">
                <div class="row">
                    <div class="col-md-4 col-12 document-cover" [class.fixed]="thumbIsFixed">
                        <img itemprop="image"
                             class="document-thumb-image"
                             src="resources/images/${document.getCover()}">
                    </div>
                    <div class="col-md-8 document-info">
                        <div itemprop="headline" class="document-title card-title">
                            <h2>${document.getTitle()}</h2>
                            <c:set value="${document.getType()}" var="type"/>
                            <c:if test="${type.equals('book')}">
                                <c:set var="bestseller" value="${document.isBestseller()}"/>
                                <c:if test="${bestseller == 1}">
                                    <span class="badge badge-warning">Bestseller</span>
                                </c:if>
                            </c:if>
                        </div>
                        <ul class="document-authors">
                            <li *ngFor="let author of document.authors"
                                itemprop="author" class="document-author">${document.getAuthorsAsString()}</li>
                        </ul>
                        <div itemprop="description"
                             class="document-description card-text italic">
                            ${document.getDescription()}
                        </div>
                        <table class="document-extra col-12">
                            <thead>
                            <tr><th width="50%"></th><th width="50%"></th></tr>
                            </thead>
                            <tbody>
                            <c:if test="${type.equals('book')}">
                                <tr *ngIf="document.publisher">
                                    <td class="bold">Publisher</td>
                                    <td>${document.getPublisher()}</td>
                                </tr>
                                <tr *ngIf="document.year">
                                    <td class="bold">Year of publication</td>
                                    <td>${document.getYear()}</td>
                                </tr>
                            </c:if>
                            <tr>
                                <td class="bold">Type</td>
                                <td>${document.getType()}</td>
                            </tr>
                            <tr>
                                <td class="bold">Price</td>
                                <td>${document.getPrice()} &#8381;</td>
                            </tr>
                            <tr>
                                <td class="bold">Copies available</td>
                                <td>${document.getAmount()}</td>
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
