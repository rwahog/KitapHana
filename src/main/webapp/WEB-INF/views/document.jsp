<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="${path}webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="/resources/css/common.css"
          rel="stylesheet">
    <link href="/resources/css/document.css"
          rel="stylesheet">
</head>
<body>
<%@ include file = "header.jsp" %>
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
                                <h2 name = "title">${document.getTitle()}</h2>
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
                                <c:choose>
                                    <c:when test="${type.equals('book')}">
                                        <tr *ngIf="document.publisher">
                                            <td class="bold">Publisher</td>
                                            <td>${document.getPublisher()}</td>
                                        </tr>
                                        <tr *ngIf="document.year">
                                            <td class="bold">Year of publication</td>
                                            <td>${document.getYear()}</td>
                                        </tr>
                                    </c:when>
                                    <c:when test="${type.equals('ja')}">
                                        <tr *ngIf="document.journal_name">
                                            <td class="bold">Journal Name</td>
                                            <td>${document.getJournalName()}</td>
                                        </tr>
                                        <tr *ngIf="document.editors">
                                            <td class="bold">Editors</td>
                                            <td>${document.getEditors()}</td>
                                        </tr>
                                        <tr *ngIf="document.date">
                                            <td class="bold">Publication date</td>
                                            <td>${document.getDate()}</td>
                                        </tr>
                                    </c:when>
                                </c:choose>
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
                                <tr>
                                    <td class="bold">Number of days</td>
                                    <td>
                                        <div class="form-group col-4">
                                            <input type="number" max="7" min="0" class="form-control" id="number" placeholder="Days" required="" style="margin-top: 40px; margin-left: 30px;">
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                            <form class="form-singin col-12" action="/document?id=${document.getId()}" method="POST">
                                <%if (session.getAttribute("libr") == null) {%>
                                <c:set var="available" value="${document.getAmount()}"/>
                                    <c:choose>
                                        <c:when test="${available > 0}">
                                        <div class="form-group">
                                            <button class="btn btn-primary btn-block col-12 col-md-3" type="submit" name="checkout" value="checkout">Check out</button>
                                        </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="form-group">
                                                <button class="btn btn-block btn-primary col-12 col-md-3" type="submit" name="checkout" value="queue">Take a chance</button>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                <%}%>
                            </form>
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
