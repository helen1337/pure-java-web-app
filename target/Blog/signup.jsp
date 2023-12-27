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
        <h2>Signup</h2>
        <p class="lead">Fill out the form for sign up!</p>
    </div>
        <div class="col-md-8 mx-auto">
            <form method="post" action="/my-blog/login?action=signUp" class="needs-validation" novalidate="">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="firstName">First name</label>
                        <input type="firstName" name="firstName"
                                class="form-control" id="firstName" placeholder="" value="" required="">
                        <div class="invalid-feedback">
                            Valid first name is required.
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="lastName">Last name</label>
                        <input type="lastName" name="lastName"
                               class="form-control" id="lastName" placeholder="" value="" required="">
                        <div class="invalid-feedback">
                            Valid last name is required.
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="login">Login</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">@</span>
                        </div>
                        <input type="login" name="login"
                               class="form-control" id="login" placeholder="" required="">
                        <div class="invalid-feedback" style="width: 100%;">
                            Your login is required.
                        </div>
                    </div>
                    <c:if test="${not empty sessionScope.message}">
                        <div class="alert alert-dark" role="alert">
                                ${sessionScope.message}
                        </div>
                        <c:remove var="message" scope="session" />
                    </c:if>
                </div>


                <div class="mb-3">
                    <label for="password">Password</label>
                    <input type="password" name="password"
                           class="form-control" id="password" placeholder="" required="">
                    <div class="invalid-feedback">
                        Please enter a password.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="email"> Email</label>
                    <input type="email" name="email"
                           class="form-control" id="email" placeholder="you@example.com" required="">
                    <div class="invalid-feedback">
                        Please enter a valid email address for shipping updates.
                    </div>
                </div>

                <div class="mb-3">
                    <label for="city">City</label>
                    <input type="city" name="city"
                           class="form-control" id="city" placeholder="city" required="">
                    <div class="invalid-feedback">
                        Please enter your city.
                    </div>
                </div>

                <hr class="mb-4">
                <input type="hidden" id="innerAction" name="innerAction" value="trySignUp">
                <button type="submit" class="btn btn-light mb-4">Sign up</button>
                <c:if test="${not empty sessionScope.message_fail}">
                    <div class="alert alert-dark" role="alert">${sessionScope.message_fail}</div>
                </c:if>
            </form>
        </div>
    </div>
</div>

</body>
<c:import url="/WEB-INF/blocks/footer.jsp"/>

</html>