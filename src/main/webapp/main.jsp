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
<div class="container py-4 body">
    <header class="pb-3 mb-4 border-bottom">
        <a class="d-flex align-items-center text-body-emphasis text-decoration-none">
            <span class="fs-4">About Us</span>
        </a>
        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-dark" role="alert">
                    ${sessionScope.message}
            </div>
            <c:remove var="message" scope="session" />
        </c:if>
    </header>

    <div class="p-5 mb-4 bg-body-tertiary rounded-3">
        <div class="container-fluid py-5">
            <h1 class="display-5 fw-bold">Welcome to My blog!</h1>
            <p class="col-md-8 fs-4">Your personal haven for self-expression and exploration!
                Immerse yourself in a world of diverse articles covering a variety of topics,
                where your thoughts find a home and your creativity knows no bounds.</p>
            <a class="btn btn-primary btn-lg" href="/my-blog/login">Log in!</a>
        </div>
    </div>

    <div class="row align-items-md-stretch">
        <div class="col-md-6">
            <div class="h-100 p-5 text-bg-dark rounded-3">
                <h2>Take a look at our articles!</h2>
                <p>Discover the joy of following your favorite topics and authors,
                    creating a personalized reading experience tailored to your interests.
                    Whether you're interested in the latest technology trends, heartwarming stories,
                    or deep thoughts, My Blog is your gateway to a community of like-minded people
                    sharing their unique perspectives.</p>
                <a class="btn btn-outline-light" href="/my-blog/all_articles">Go to our articles</a>
            </div>
        </div>
        <div class="col-md-6">
            <div class="h-100 p-5 bg-body-tertiary border rounded-3">
                <h2>Add borders</h2>
                <p>Check the weather effortlessly, seamlessly integrating your daily life with your digital journey.</p>
                <a class="btn btn-outline-secondary" href="weather.jsp">Go to weather</a>
            </div>
        </div>
    </div>
    <div class="py-5">
        <p class="lead">Experience the warmth of a platform that celebrates diversity,
            encourages creativity and fosters meaningful connections.
            My blog is more than just a website;
            it's a virtual sanctuary where ideas bloom,
            conversations bloom, and the outlook is always bright.
            Join us to follow our development,
            as more opportunities will appear here over time!
        </p>
    </div>
</div>
</body>
<c:import url="/WEB-INF/blocks/footer.jsp"/>
</html>
