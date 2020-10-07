<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SBB Home</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>

<body>
<nav class="navbar navbar-expand-md navbar-light sticky-top" style="background-color: #491262">
    <a href="<c:url value="/"/>" class="navbar-brand" style="color: white">SBB</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <a href="<c:url value="/"/>" class="nav-item nav-link active" style="color: white">Home</a>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <div class="btn-group">
                    <a href="<c:url value="/stations"/>" class="btn btn-outline-light">Stations</a>
                    <a href="<c:url value="/trains"/>" class="btn btn-outline-light">Trains</a>
                    <a href="<c:url value="/passengers"/>" class="btn btn-outline-light">Passengers</a>
                </div>
            </sec:authorize>
        </div>
        <div class="navbar-nav ml-auto">
            <sec:authorize access="!isAuthenticated()">
                <a href="<c:url value="/registration"/>" class="nav-item nav-link" style="color: white">Registration</a>
                <a href="<c:url value="/login"/>" class="btn btn-outline-light">Login</a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <a href="<c:url value="/logout"/>" class="btn btn-outline-light">Logout</a>
            </sec:authorize>
        </div>
    </div>
</nav>

<div style="height: 100px"></div>

<div class="alert alert-success alert-dismissible fade show col-sm-6 offset-sm-3">
    You have been successfully logged in as <strong>${pageContext.request.userPrincipal.name}</strong>.
    <button type="button" class="close" data-dismiss="alert">&times;</button>
</div>

<form:form action="/searchResult" method="POST">
    <div class="form-group row">
        <div class="input-group col-sm-4 offset-sm-4">
            <div class="input-group-prepend">
                <label class="input-group-text" for="stationFrom">From</label>
            </div>
            <select class="custom-select" name="stationFrom" id="stationFrom">
                <option selected>Select Station</option>
                <c:forEach var="station" items="${stationsList}">
                    <option value="${station.name}">${station.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <div class="input-group col-sm-4 offset-sm-4">
            <div class="input-group-prepend">
                <label class="input-group-text" for="stationTo">To</label>
            </div>
            <select class="custom-select" name="stationTo" id="stationTo">
                <option selected>Select Station</option>
                <c:forEach var="station" items="${stationsList}">
                    <option value="${station.name}">${station.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-10 offset-sm-4">
            <button type="submit" class="btn btn-danger">Search</button>
        </div>
    </div>
</form:form>

<div style="height: 100px"></div>

<div class="">
    <img src="${pageContext.request.contextPath}/assets/spacetrain.jpg" class="img-fluid" alt="Train">
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