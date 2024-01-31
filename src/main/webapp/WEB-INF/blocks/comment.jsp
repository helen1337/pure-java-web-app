<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<h3>
    Comments
</h3>
<p class="fw-light">Feel free to share your thoughts and leave a comment below.</p>
<hr>
<c:if test="${not empty sessionScope.message}">
    <div class="alert alert-dark" role="alert">
            ${sessionScope.message}
    </div>
    <c:remove var="message" scope="session" />
</c:if>
<div class="container-comment">
    <form action="/my-blog/comment" id="commentForm"
          method="post" class="needs-validation" novalidate="">
        <input type="hidden" name="commAction" value="send">
        <input type="hidden" id="article_id" name="article_id" value="${article.id}">
        <div class="mb-3">
            <label for="nickname">Nickname</label>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text">@</span>
                </div>
                <input type="text" class="form-control"
                       id="nickname" placeholder="Nickname"
                       name="nickname" required="">
                <div class="invalid-feedback" style="width: 100%;">
                    Your nickname is required.
                </div>
            </div>
        </div>
        <div class="mb-3">
            <label for="content">Content</label>
            <input type="content" class="form-control"
                   id="content" name="content"
                   placeholder="" required="">
            <div class="invalid-feedback">
                Please enter a content.
            </div>
        </div>
        <div class="form-check">
            <input class="form-check-input" type="radio"
                   name="status" id="status1" value="anon" checked>
            <label class="form-check-label" for="status1">
                <p class="fw-light">Anonymously (cannot be deleted)</p>
            </label>
        </div>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <div class="form-check">
                    <input class="form-check-input" type="radio"
                           name="status" id="status2" value="user">
                    <label class="form-check-label" for="status2">
                        Link your comment to account to be able to manage your comment
                    </label>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-check">
                    <input class="form-check-input" type="radio"
                           name="status" id="status3" value="" disabled>
                    <label class="form-check-label" for="status3">
                        Please log in to be able to manage your comment in the future
                    </label>
                </div>
            </c:otherwise>
        </c:choose>
        <hr class="mb-4">
        <button class="btn btn-primary btn-lg btn-block" type="submit">
            Send comment
        </button>
    </form>
    <c:choose>
        <c:when test="${not empty commentsList}">
            <c:forEach var="comment" items="${commentsList}">
                <div class="col-md-12">
                    <div class="row no-gutters border rounded overflow-hidden flex-md-row mb-4 shadow-sm h-md-250 position-relative">
                        <div class="col p-4 d-flex flex-column position-static">
                            <p class="fw-light">${comment.time} by
                                <span class="fst-italic">
                                        ${comment.nickname}
                                </span>
                            </p>
                            <p class="card-text mb-auto">${comment.content}</p>
                            <c:if test="${sessionScope.user.user_id eq comment.user_id}">
                                <nav class="blog-pagination py-2" aria-label="Pagination">
                                    <form action="/my-blog/comment" id="commentDeleteForm${comment.id}"
                                          method="post" class="d-inline-block">
                                        <input type="hidden" name="commAction" value="delete">
                                        <input type="hidden" name="comment_id" value="${comment.id}">
                                        <input type="hidden" name="article_id" value="${article.id}">
                                        <button type="submit" class="btn btn-outline-secondary">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16"
                                                 height="16" fill="currentColor" class="bi bi-trash"
                                                 viewBox="0 0 16 16">
                                                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"></path>
                                                <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"></path>
                                            </svg>
                                            Delete
                                        </button>
                                    </form>
                                </nav>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:when>
    </c:choose>
</div>
</html>