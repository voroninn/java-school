<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Edit Schedule</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/assets/favicon.ico" rel="icon" type="image/x-icon"/>
</head>

<body>
<nav class="navbar navbar-expand-md navbar-light fixed-top" style="background-color: #491262">
    <a href="<c:url value="/"/>" class="navbar-brand" style="color: white">SBB</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <a href="<c:url value="/"/>" class="nav-item nav-link active" style="color: white">Home</a>
            <div class="btn-group">
                <a href="<c:url value="/stations"/>" class="btn btn-outline-light">Stations</a>
                <a href="<c:url value="/trains"/>" class="btn btn-outline-light">Trains</a>
                <a href="<c:url value="/passengers"/>" class="btn btn-outline-light">Passengers</a>
            </div>
        </div>
        <div class="navbar-nav ml-auto">
            <a href="#" class="nav-item nav-link disabled" style="color: white">
                Logged in as <strong>${pageContext.request.userPrincipal.name}</strong></a>
            <a href="<c:url value="/logout"/>" class="btn btn-outline-light">Logout</a>
        </div>
    </div>
</nav>

<div style="height: 100px"></div>

<div class="container">
    <h2 class="text-center">The following stations are currently on Track ${track}:</h2>
    <div class="jumbotron">
        <div style="height: 30px"></div>
        <div class="text-center">
            <h4>
                ${stationsList.get(0).name} -
                <c:forEach var="stationItem" items="${stationsList}" begin="1" end="${stationsList.size() - 2}">
                    ${stationItem.name} -
                </c:forEach>
                ${stationsList.get(stationsList.size() - 1).name}
            </h4>
            <div style="height: 30px"></div>
            <p>Where would you like to append <strong>${station.name}?</strong></p>
        </div>
        <div style="height: 100px"></div>
        <form action="/stations/edit/track" method="POST" class="col-sm-10 offset-1">
            <input type="hidden" name="appendLocation" value="before">
            <input type="hidden" name="track" value="${track}">
            <input type="hidden" name="length" value="${length}">
            <button class="col-sm-10 offset-1 btn btn-outline-info btn-block"
                    type="submit">Before ${stationsList.get(0).name}</button>
        </form>
        <div style="height: 30px"></div>
        <form action="/stations/edit/track" method="POST" class="col-sm-10 offset-1">
            <input type="hidden" name="appendLocation" value="after">
            <input type="hidden" name="track" value="${track}">
            <input type="hidden" name="length" value="${length}">
            <button class="col-sm-10 offset-1 btn btn-outline-info btn-block"
                    type="submit">After ${stationsList.get(stationsList.size() - 1).name}</button>
        </form>
    </div>
</div>

<footer class="page-footer font-small">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#">JavaSchool</a>
    </div>
</footer>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</body>
</html>