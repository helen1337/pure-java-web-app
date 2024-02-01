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
    <script src="js/validator.js"></script>
</head>
<c:import url="/WEB-INF/blocks/header.jsp"/>
<body>
<div class="containerBody body">
        <div class="py-5 text-center">
            <h2>My profile</h2>
        </div>

    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-dark" role="alert">
                ${sessionScope.message}
        </div>
        <c:remove var="message" scope="session" />
    </c:if>

        <form method="post" action="/my-blog/profile" class="needs-validation" novalidate="">
            <c:if test="${empty action}">

                <div class="card mb-3">
                    <div class="card-body">

                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">First name</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.firstName}
                            </div>
                        </div>

                        <hr>

                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Last name</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.lastName}
                            </div>
                        </div>

                        <hr>

                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Login</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.login}
                            </div>
                        </div>

                        <hr>

                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Email</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.email}
                            </div>
                        </div>

                        <hr>

                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">City</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.city}
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>

            <c:if test="${action eq 'edit'}">
                <input type="hidden" id="action" name="action" value="edit">

            <div class="card mb-3 <%--needs-validation" novalidate="--%>">
                <div class="card-body">

                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">First name</h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            <input type="firstName" name="firstName"
                                   class="form-control" id="firstName" placeholder="" value="${sessionScope.user.firstName}" required="">
                            <div class="invalid-feedback">
                                Valid first name is required.
                            </div>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">Last name</h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            <input type="lastName" name="lastName"
                                   class="form-control" id="lastName" placeholder="" value="${sessionScope.user.lastName}" required="">
                            <div class="invalid-feedback">
                                Valid last name is required.
                            </div>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">Login</h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            ${sessionScope.user.login}
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">Password</h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            <input type="password" name="password"
                                   class="form-control" id="password" placeholder="" value="${sessionScope.user.password}" required="">
                            <div class="invalid-feedback">
                                Valid password is required.
                            </div>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">Email</h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            <input type="email" name="email"
                                   class="form-control" id="email" placeholder="" value="${sessionScope.user.email}" required="">
                            <div class="invalid-feedback">
                                Valid email is required.
                            </div>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-sm-3">
                            <h6 class="mb-0">City</h6>
                        </div>
                        <div class="col-sm-9 text-secondary">
                            <input type="city" name="city"
                                   class="form-control" id="city" placeholder="" value="${sessionScope.user.city}" required="">
                            <div class="invalid-feedback">
                                Valid city is required.
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <nav class="blog-pagination" aria-label="Pagination">
                <button type="submit" class="btn btn-outline-primary rounded-pill">Edit</button>
            </nav>
            </c:if>

        </form>

        <form method="get" action="/profile">
            <c:if test="${empty action}">
            <nav class="blog-pagination" aria-label="Pagination">
                <a class="btn btn-outline-primary rounded-pill" href="/my-blog/profile?action=edit">Edit</a>
                <a class="btn btn-outline-secondary rounded-pill" href="/my-blog/profile?action=delete">Delete</a>
            </nav>
            </c:if>
        </form>
</div>
</body>
<c:import url="/WEB-INF/blocks/footer.jsp"/>
</html>