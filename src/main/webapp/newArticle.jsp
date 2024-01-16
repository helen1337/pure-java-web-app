<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="css/styles.css" rel="stylesheet">
    <link href="js/validator.js" rel="script">
</head>
<c:import url="/WEB-INF/blocks/header.jsp"/>
<body>
<form action="/my-blog/article" method="post" class="body">
    <c:if test="${empty action}">
        <c:set var="action" value="add"/>
    </c:if>
    <input type="hidden" id="action" name="action" value="${action}">
    <c:if test="${not empty article}">
        <input type="hidden" id="id" name="id" value="${article.id}">
    </c:if>
    <div class="containerBody">
        <div class="py-5 text-center">
            <h2><c:choose><c:when test="${action eq 'add'}">New Article</c:when><c:otherwise>Edit article</c:otherwise></c:choose></h2>
        </div>
        <div class="col-md-8 mx-auto">
            <div class="needs-validation">
                <label for="title">Title</label>
                <input type="text" name="title" placeholder="Enter article" id="title" class="form-control" required=""
                       value="<c:if test="${not empty article}">${article.title}</c:if>"><br>
                <div class="invalid-feedback">
                    Valid title is required.
                </div>

                <label for="author">Author</label>
                <input type="text" name="author" placeholder="Enter author" id="author" class="form-control" required=""
                       value="<c:choose><c:when test="${not empty article}">${article.author}</c:when><c:otherwise><c:if test="${not empty sessionScope.user}">${sessionScope.user.login}</c:if></c:otherwise></c:choose>"><br>
                <div class="invalid-feedback">
                    Valid author name is required.
                </div>

                <label for="theme">Theme</label>
                <input type="text" name="theme" placeholder="Enter theme" id="theme" class="form-control" required=""
                       value="<c:if test="${not empty article}">${article.theme}</c:if>"><br>
                <div class="invalid-feedback">
                    Valid theme name is required.
                </div>

                <label for="content">Full text</label>
                <textarea type="full_text" name="content"  class="form-control" placeholder="Enter the full text of the article" id="content" required=""
                          value=""
                          style="height: 100px"><c:if test="${not empty article}">${article.content}</c:if></textarea><br>
                <div class="invalid-feedback">
                    Valid text is required.
                </div>

                <c:if test="${action eq 'add'}">
                    <button type="submit" class="btn btn-light mb-4">Create an article</button>
                </c:if>
                <c:if test="${action eq 'edit'}">
                    <button type="submit" class="btn btn-light mb-4">Edit an article</button>
                </c:if>

                <c:if test="${not empty sessionScope.message_fail}">
                    <div class="alert alert-dark" role="alert">
                            ${sessionScope.message_fail}
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</form>
</body>
<c:import url="/WEB-INF/blocks/footer.jsp"/>
</html>