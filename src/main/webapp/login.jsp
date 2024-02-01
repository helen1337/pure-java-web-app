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
    <div class="container h-200 body">
        <div class="row d-flex justify-content-center align-items-center h-200">
            <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card-body p-5 text-center">
                    <div class="mb-md-5 mt-md-4">
                        <h5 class="text-uppercase">My Blog Account</h5>
                        <p class="text-dark-50">Please enter your login and password!</p>
                        <form method="post" action="/my-blog/login" class="needs-validation" novalidate="">
                        <div class="form-outline form-white mb-3">
                            <div class="input-group">
                                <div class="input-group-prepend">
                                    <span class="input-group-text custom-at-sign">@</span>
                                </div>
                                <input type="login" name="login" class="form-control form-control-lg" id="login" placeholder="" required="">
                                <div class="invalid-feedback" style="width: 100%;">Your login is required.</div>
                            </div>
                            <label class="form-label" for="login">Login</label>
                        </div>
                        <div class="form-outline form-white mb-4">
                            <input type="password" name="password" class="form-control" id="password" placeholder="" required="">
                            <div class="invalid-feedback">Please enter a password.</div>
                            <label class="form-label" for="password">Password</label>
                        </div>
                        <c:if test="${not empty sessionScope.message}">
                            <div class="alert alert-dark" role="alert">
                                    ${sessionScope.message}
                            </div>
                            <c:remove var="message" scope="session" />
                        </c:if>
                        <input type="hidden" id="action" name="action" value="tryLogin">
                        <button type="submit" class="btn btn-light mb-4">Log in</button>
                        <p>Don't have an account? <a href="/my-blog/login?action=signUp" class="text-dark-50 fw-bold">Sign Up</a></p>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
<c:import url="/WEB-INF/blocks/footer.jsp"/>
</body>
</html>