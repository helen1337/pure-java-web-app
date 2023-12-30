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
</head>
<c:import url="/WEB-INF/blocks/header.jsp"/>
<body>
<div class="container body">
    <div class="py-5 text-center">
        <h2>All Articles</h2>
    </div>
    <form method="get" action="/my-blog/all_articles">
        <select name="theme" class="form-select mb-2" aria-label="Default select example">
            <option value="" selected>All themes</option>
            <c:choose>
                <c:when test="${not empty themeList}">
                    <c:forEach var="theme" items="${themeList}">
                        <option value="${theme}">${theme}</option>
                    </c:forEach>
                </c:when>
            </c:choose>
        </select>
        <button type="submit" class="btn btn-light mb-4">Search</button>

        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-dark" role="alert">
                    ${sessionScope.message}
            </div>
            <c:remove var="message" scope="session" />
        </c:if>


        <c:choose>
            <c:when test="${not empty articlesList}">
                <c:forEach var="article" items="${articlesList}">
                    <div class="col-md-12">
                        <div class="row no-gutters border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                            <div class="col p-4 d-flex flex-column position-static">
                                <strong class="d-inline-block mb-2 text-primary">${article.theme}</strong>
                                <h3 class="mb-0">${article.title}</h3>
                                <div class="mb-1 text-muted">${article.time}</div>
                                <p class="card-text mb-auto">${article.content}</p>
                                <a href="/my-blog/article?id=${article.id}" class="stretched-link">Continue reading</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
        </c:choose>
    </form>
</div>
</body>
<c:import url="/WEB-INF/blocks/footer.jsp"/>
</html>
