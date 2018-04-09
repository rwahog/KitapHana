<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/forms.css"
          rel="stylesheet">
</head>
<body>
<%@include file="header.jsp" %>
<form class="form-registration col-12 col-md-8 col-lg-6 mx-auto" method="post">
    <div class="container addDocument">
        <div class="form-row">
            <div class="form-group col-12">
                <label for="title">Title</label>
                <input type="text" class="form-control" id="title" name="title" placeholder="Title" required="" autofocus="">
            </div>
            <div class="form-group col-12">
                <label>Authors</label>
                <textarea type="text" class="form-control" name="authors" id="authors" placeholder="Authors"
                          required=""></textarea>
            </div>
            <div class="form-group col-12">
                <label for="description">Description</label>
                <textarea type="text" class="form-control" name="description" id="description" placeholder="Description"
                          required=""></textarea>
            </div>
            <div class="form-group col-12">
                <label for="keywords">Keywords</label>
                <input type="text" class="form-control" name="keywords" id="keywords" placeholder="Keywords" required="">
            </div>
            <div class="form-group col-4">
                <label for="type">Type</label>
                <select id="type" class="form-control document-type" name="document-type">
                    <option value="" selected>Choose...</option>
                    <option value="book">Book</option>
                    <option value="article">Journal Article</option>
                    <option value="av">AV material</option>
                </select>
            </div>
            <div class="form-group col-4">
                <label for="inputName">Price</label>
                <input type="text" class="form-control" id="inputName" name="price" placeholder="Price" required="">
            </div>
            <div class="form-group col-4">
                <label for="inputSurname">Amount</label>
                <input type="number" class="form-control" id="inputSurname" name="amount" placeholder="Amount" required="">
            </div>
            <form method="POST" name="book">
            <div class="book-props row hidden" id="book">
                <div class="form-group col-3">
                    <label for="edition_number">Edition number</label>
                    <input type="text" class="form-control" id="edition_number" name="edition_number" placeholder="Edition number"
                           required="">
                </div>
                <div class="form-group col-4">
                    <label for="publisher">Publisher</label>
                    <input type="text" class="form-control" id="publisher" name="publisher" placeholder="Publisher" required="">
                </div>
                <div class="form-group col-3">
                    <label for="year">Year</label>
                    <input type="text" class="form-control" id="year" name="year" placeholder="Year" required="">
                </div>
                <div class="custom-control custom-checkbox my-1 mr-sm-2">
                    <input type="checkbox" name="bestseller" value="1" class="custom-control-input" id="customControlInline">
                    <label class="custom-control-label" for="customControlInline">Bestseller</label>
                </div>
            </div>
            </form>
            <form method="POST" name="article">
            <div class="ja-props row hidden" id="article">
                <div class="form-group col-5">
                    <label for="editors">Editors</label>
                    <input type="text" class="form-control" name="editors" id="editors" placeholder="Editors" required="">
                </div>
                <div class="form-group col-4">
                    <label for="journal_name">Journal name</label>
                    <input type="text" class="form-control" name="journal_name" id="journal_name" placeholder="Journal name" required="">
                </div>
                <div class="form-group col-3">
                    <label for="date">Date</label>
                    <input type="text" class="form-control" name="date" id="date" placeholder="Date" required="">
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
    </div>
</form>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/addDocument.js"></script>
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
