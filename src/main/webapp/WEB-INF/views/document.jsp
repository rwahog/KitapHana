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
<%@ include file = "header.jsp" %>
<main class="body">
    <c:forEach var="document" items="${list}">
        <div class="document-details container-fluid mx-auto px-0">
            <div class="row" *ngIf="document != null; else documentNotFound">
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
                                <h2>${document.title}</h2>
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
                                Learning to Program Well with Objects and Contracts //kakoy description delat'
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
                                    <td>${document.price} ₽</td> //podpravit' otobrazhenie rublya
                                </tr>
                                <tr>
                                    <td class="bold">Copies available</td>
                                    <td>${document.amount}</td>
                                </tr>
                                </tbody>
                            </table>
                            <c:set var="available" value="${document.getAmount()}"/>
                            <c:if test="${available > 0}">
                            <div class="form-group">
                                <button class="btn btn-primary btn-block col-12 col-md-3">Check out</button>
                            </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
    </main>
</body>
</html>
