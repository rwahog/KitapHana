<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>KitapHana</title>
    <link href="webjars/bootstrap/4.0.0/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/common.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/editDocument.css"
          rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/masonry.pkgd.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/addDocument.js"></script>
</head>
<body>
<%@include file="header.jsp" %>
<form class="form-registration col-12 col-md-8 col-lg-6 mx-auto">
    <div class="container addDocument">
        <div class="form-row">
            <div class="form-group col-12">
                <label for="title">Title</label>
                <input type="text" class="form-control" id="title" placeholder="Title" value="Touch of class" required="" autofocus="">
            </div>
            <div class="form-group col-12">
                <label>Authors</label>
                <div class="form-row">
                    <div class="col-4">
                        <input class="form-control" type="text" name="author_name" value="Bertrand" placeholder="First name">
                    </div>
                    <div class="col-4">
                        <input class="form-control" type="text" name="author_lastname" value="Meyer" placeholder="Last name">
                    </div>
                    <div class="col-3">
                        <input class="btn btn-primary px-4" id="addNewAuthor" type="button" value="Add author">
                    </div>
                </div>
                <div class="author-list my-3"></div>
            </div>
            <div class="form-group col-12">
                <label for="description">Description</label>
                <textarea type="text" class="form-control" id="description" placeholder="Description"
                          required="">Learning to Program Well with Objects and Contracts</textarea>
            </div>
            <div class="form-group col-12">
                <label for="keywords">Keywords</label>
                <input type="text" class="form-control" id="keywords" value="Programming, eiffel" placeholder="Keywords" required="">
            </div>
            <div class="form-group col-4">
                <label for="type">Type</label>
                <select id="type" class="form-control document-type" name="document-type">
                    <option selected value="1">Book</option>
                    <option value="2">Journal Article</option>
                    <option value="3">AV material</option>
                </select>
            </div>
            <div class="form-group col-4">
                <label for="inputName">Price</label>
                <input type="text" class="form-control" id="inputName" value="800p" placeholder="Price" required="">
            </div>
            <div class="form-group col-4">
                <label for="inputSurname">Amount</label>
                <input type="number" class="form-control" id="inputSurname" value="4" placeholder="Amount" required="">
            </div>
            <div class="book-props row hidden">
                <div class="form-group col-3">
                    <label for="edition_number">Edition number</label>
                    <input type="text" class="form-control" id="edition_number" value="3" placeholder="Edition number"
                           required="">
                </div>
                <div class="form-group col-4">
                    <label for="publisher">Publisher</label>
                    <input type="text" class="form-control" id="publisher" value="Springer" placeholder="Publisher" required="">
                </div>
                <div class="form-group col-3">
                    <label for="year">Year</label>
                    <input type="text" class="form-control" id="year" value="2008" placeholder="Year" required="">
                </div>
                <div class="custom-control custom-checkbox my-1 mr-sm-2">
                    <input type="checkbox" class="custom-control-input" checked id="customControlInline">
                    <label class="custom-control-label" for="customControlInline">Bestseller</label>
                </div>
            </div>
            <div class="ja-props row hidden">
                <div class="form-group col-5">
                    <label for="editors">Editors</label>
                    <input type="text" class="form-control" id="editors" value = "" placeholder="Editors" required="">
                </div>
                <div class="form-group col-4">
                    <label for="journal_name">Journal name</label>
                    <input type="text" class="form-control" id="journal_name" value="" placeholder="Journal name" required="">
                </div>
                <div class="form-group col-3">
                    <label for="date">Date</label>
                    <input type="text" class="form-control" id="date" value="" placeholder="Date" required="">
                </div>
            </div>
        </div>
        <form>
            <div>
                <label for="file"></label>
                <input type="file" id="file" class="file">
            </div>
        </form>
        <div class="form-row">
            <div class="form-group col-md-6">
            </div>
            <div class="form-group col-12 col-md-3">
                <a class="btn btn-block" id="button" href="">Cancel</a>
            </div>
            <div class="form-group col-12 col-md-3">
                <button class="btn btn-block btn-primary" type="submit">Save</button>
            </div>
        </div>
    </div>
</form>
</body>
</html>