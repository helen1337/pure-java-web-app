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
<body>
<c:import url="/WEB-INF/blocks/header.jsp"/>
<div class="container body">
    <div class="py-5">

            <h3 class="pb-4 mb-4 fst-italic border-bottom">
                Article
            </h3>

            <form action="/article" method="get" id="articleForm" role="form">
                <input type="hidden" id="action" name="action">
                <article class="blog-post">
                <h2 class="display-5 link-body-emphasis mb-1">${article.title}</h2>
                <p class="blog-post-meta">${article.time} by <a href="/my-blog/all_articles?action=searchAuthorArticles&author=${article.id}">${article.author}</a></p>

                <p>Theme: <a href="/my-blog/all_articles?theme=${article.theme}">${article.theme}</a></p>
                <hr>
                <blockquote class="blockquote">
                    <p>Content</p>
                </blockquote>
                <p class="pb-4 mb-4 fst-italic border-bottom">${article.content}</p>
                </article>
            </form>

            <form action="/article" method="post">
                <c:if test="${sessionScope.user.user_id eq article.user_id}">
                    <nav class="blog-pagination" aria-label="Pagination">
                        <a class="btn btn-outline-primary rounded-pill" href="/my-blog/article?id=${article.id}&action=edit">Edit</a>
                        <a class="btn btn-outline-secondary rounded-pill" href="/my-blog/article?id=${article.id}&action=delete">Delete</a>
                    </nav>
                </c:if>
            </form>
        </div>
    </div>
</body>
<c:import url="/WEB-INF/blocks/footer.jsp"/>
</html>