<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<header>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/my-blog">My blog</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Articles
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="/my-blog/all_articles">All Articles</a></li>
                        <li><a class="dropdown-item" href="/my-blog/article?action=add">New article</a></li>
                        <c:if test="${not empty sessionScope.user}">
                            <li><a class="dropdown-item" href="/my-blog/all_articles?action=searchAuthorArticles&author=${sessionScope.user.login}">My article</a></li>
                        </c:if>
                    </ul>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="/my-blog/weather">Weather in your city</a>
                </li>

                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item">
                        <a class="nav-link" href="/my-blog/profile">My profile</a>
                    </li>
                </c:if>

                <li class="nav-item">
                    <a class="nav-link" href="/my-blog">About Us</a>
                </li>
            </ul>

            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a class="btn btn-outline-secondary" href="/my-blog/login?action=logOut">Log out</a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-outline-secondary" href="/my-blog/login">Log in</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
</header>
</html>