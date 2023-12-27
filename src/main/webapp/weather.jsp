<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<div class="body">
    <div class="py-5 text-center">
        <h2>Weather in your city</h2>
    </div>
    <div class="container-input-city">
        <form action="/my-blog/weather" method="get">
            <div class="row g-3 center mb-4">
                <div class="col-auto">
                    <label for="city" class="visually-hidden">City</label>
                    <input type="city" name="city" class="form-control" id="city" placeholder="City">
                </div>
                <div class="col-auto">
                    <input type="hidden" id="action" name="action" value="searchByCity">
                    <button type="submit" class="btn btn-outline-secondary">Show weather</button>
                </div>
            </div>

            <c:if test="${not empty sessionScope.message}">
                <div class="alert alert-dark" role="alert">
                        ${sessionScope.message}
                </div>
                <c:remove var="message" scope="session" />
            </c:if>

        <c:if test="${not empty weatherForecast}">
            <div class="card-body">
                <h4>${weatherForecast.city}</h4>
                <h4>${weatherForecast.city}</h4>
                <p><strong>${weatherForecast.weatherParam}: ${weatherForecast.additionWeatherParams}</strong></p>
                <p>longitude:${weatherForecast.longitude}, latitude:${weatherForecast.latitude}</p>
                <p>Current temperature:<strong>${weatherForecast.temp}°C</strong></p>
                <p>Feels like: <strong>${weatherForecast.tempFeelsLike}°C</strong></p>
                <p>Atmospheric Pressure: <strong>${weatherForecast.atmPressure} hPa</strong></p>
                <p>Humidity: <strong>${weatherForecast.humidity}%</strong></p>
                <p>Visibility: <strong>${weatherForecast.humidity} km</strong></p>
                <p>Wind: <strong>${weatherForecast.windSpeed} m/s<strong>, dir:</strong> ${weatherForecast.windDir} </strong></p>
                <p>Rain: <strong>${weatherForecast.rain} mm</strong></p>
                <p>Snow: <strong>${weatherForecast.snow} mm</strong></p>
                <p>Sunrise: <strong>${weatherForecast.sunriseTime}</strong></p>
                <p>Sunset: <strong>${weatherForecast.sunsetTime}</strong></p>
        </div>
        </c:if>
        </form>
    </div>
</div>
</body>


<c:import url="/WEB-INF/blocks/footer.jsp"/>

</html>

