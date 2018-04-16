<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <c:set var="path" value="${application.getRealPath(\"/\")}"/>
    <link href="${path}/webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${path}/resources/css/common.css"
          rel="stylesheet">
    <link href="${path}/resources/css/forms.css"
          rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<form class="form-registration col-12 col-md-8 col-lg-6 mx-auto" method="POST">
    <div class="container addDocument">
        <div class="form-row">
            <div class="form-group col-12">
                <label for="title">Title</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="Title" value="${doc.getTitle()}" required="" autofocus="">
            </div>
            <div class="form-group col-12">
                <label for="authors">Authors</label>
                <input type="text" class="form-control" id="authors" name="authors" value="${doc.getAuthorsAsString()}" placeholder="Keywords" required="">
            </div>
            <div class="form-group col-12">
                <label for="description">Description</label>
                <textarea type="text" class="form-control" id="description" name="description" placeholder="Description"
                          required="">${doc.getDescription()}</textarea>
            </div>
            <div class="form-group col-12">
                <label for="keywords">Keywords</label>
                <input type="text" class="form-control" id="keywords" name="keywords" value="${doc.getKeywordsAsString()}" placeholder="Keywords" required="">
            </div>
            <div class="form-group col-4">
                <label for="type">Type</label>
                <select id="type" class="form-control document-type" name="document-type">
                    <c:set var="type" value="${doc.getType()}"/>
                    <c:choose>
                        <c:when test="${type == 'book'}">
                            <option value="book" selected style="background-color:black">Book</option>
                            <option value="article" style="background-color:black">Journal Article</option>
                            <option value="av" style="background-color:black">AV Material</option>
                        </c:when>
                        <c:when test="${type == 'av'}">
                            <option value="av" selected style="background-color:black">AV Material</option>
                            <option value="book" style="background-color:black">Book</option>
                            <option value="article" style="background-color:black">Journal Article</option>
                        </c:when>
                        <c:otherwise>
                            <option value="book" style="background-color:black">Book</option>
                            <option value="av" style="background-color:black">AV Material</option>
                            <option value="article" selected style="background-color:black">Journal Article</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <div class="form-group col-4">
                <label for="inputName">Price</label>
                <input type="text" class="form-control" id="inputName" name="price" value="${doc.getPrice()}" placeholder="Price" required="">
            </div>
            <div class="form-group col-4">
                <label for="inputSurname">Amount</label>
                <input type="number" class="form-control" id="inputSurname" name="amount" value="${doc.getAmount()}" placeholder="Amount" required="">
            </div>
            <c:set var="type" value="${doc.getType()}"/>
            <c:choose>
                <c:when test="${type.equals('book')}">
                    <c:set var="edition_number" value="${doc.getEditionNumber()}"/>
                    <c:set var="publisher" value="${doc.getPublisher()}"/>
                    <c:set var="year" value="${doc.getYear()}"/>
                    <c:set var="bestseller" value="${doc.isBestseller()}"/>
                </c:when>
                <c:when test="${type.equals('ja')}">
                    <c:set var="editors" value="${doc.getEditors()}"/>
                    <c:set var="journal_name" value="${doc.getJournalName()}"/>
                    <c:set var="date" value="${doc.getDate()}"/>
                </c:when>
            </c:choose>
            <form method="POST">
            <div class="book-props row hidden" id="book">
                <div class="form-group col-3">
                    <label for="edition_number">Edition number</label>
                    <input type="text" class="form-control" id="edition_number" name="edition_number" value="${editionNumber}" placeholder="Edition number"
                           required="">
                </div>
                <div class="form-group col-4">
                    <label for="publisher">Publisher</label>
                    <input type="text" class="form-control" id="publisher" name="publisher" value="${publisher}" placeholder="Publisher" required="">
                </div>
                <div class="form-group col-3">
                    <label for="year">Year</label>
                    <input type="text" class="form-control" id="year" name="year" value="${year}" placeholder="Year" required="">
                </div>
                <div class="custom-control custom-checkbox my-1 mr-sm-2">
                    <c:choose>
                        <c:when test="${bestseller == 1}">
                            <input type="checkbox" class="custom-control-input" name="bestseller" value="1" checked id="customControlInline">
                        </c:when>
                        <c:otherwise>
                            <input type="checkbox" class="custom-control-input" name="bestseller" value="1" id="customControlInline">
                        </c:otherwise>
                    </c:choose>
                    <label class="custom-control-label" for="customControlInline">Bestseller</label>
                </div>
            </div>
            </form>
            <form method="POST">
            <div class="ja-props row hidden" id="article">
                <div class="form-group col-5">
                    <label for="editors">Editors</label>
                    <input type="text" class="form-control" id="editors" name="editors" value = "${editors}" placeholder="Editors" required="">
                </div>
                <div class="form-group col-4">
                    <label for="journal_name">Journal name</label>
                    <input type="text" class="form-control" id="journal_name" name="journal_name" value="${journalName}" placeholder="Journal name" required="">
                </div>
                <div class="form-group col-3">
                    <label for="date">Date</label>
                    <input type="text" class="form-control" id="date" name="date" value="${date}" placeholder="Date" required="">
                </div>
            </div>
            </form>
        </div>
        <%--<form>--%>
            <%--<div>--%>
                <%--<label for="file"></label>--%>
                <%--<input type="file" id="file" class="file">--%>
            <%--</div>--%>
        <%--</form>--%>
        <div class="form-row">
            <div class="form-group col-md-6">
            </div>
            <div class="form-group col-12 col-md-3">
                <a class="btn btn-block" id="button" onclick="goBack()">Cancel</a>
            </div>
            <div class="form-group col-12 col-md-3">
                <button class="btn btn-block btn-primary" type="submit">Save</button>
            </div>
        </div>
    </div>

</form>
<script src="${path}/resources/js/jquery-3.2.1.slim.min.js"></script>
<script src="/resources/js/popper.min.js"></script>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/masonry.pkgd.min.js"></script>
<script src="/resources/js/main.js"></script>
<%--<script src="${pageContext.request.contextPath}/resources/js/addDocument.js"></script>--%>
<script>
    function goBack() {
        window.history.back();
    }
</script>
<script>
    $(function() {
        $('.hidden').hide();
    });
    $(function() {
        $('#type').change(function(){
            $('.hidden').hide();
            $('#' + $(this).val()).show();
        });
    });
</script>
</body>
</html>
