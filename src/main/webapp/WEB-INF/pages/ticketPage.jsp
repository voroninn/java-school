<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Ticket Info</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/assets/favicon.ico" rel="icon" type="image/x-icon" />
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
            <sec:authorize access="hasRole('ROLE_USER')">
                <a href="/myaccount/${pageContext.request.userPrincipal.name}"
                   class="nav-item nav-link active" style="color: white">My Account</a>
            </sec:authorize>
        </div>
        <div class="navbar-nav ml-auto">
            <sec:authorize access="!isAuthenticated()">
                <a href="<c:url value="/registration"/>" class="nav-item nav-link" style="color: white">Registration</a>
                <a href="<c:url value="/login"/>" class="nav-item nav-link" style="color: white">Login</a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <a href="#" class="nav-item nav-link disabled" style="color: white">
                    Logged in as <strong>${pageContext.request.userPrincipal.name}</strong></a>
                <a href="<c:url value="/logout"/>" class="btn btn-outline-light">Logout</a>
            </sec:authorize>
        </div>
    </div>
</nav>

<div style="height: 100px"></div>

<div class="container">
    <h2 class="text-center">Please check your travel details</h2>
    <div class="jumbotron">
        <p>Date: <fmt:formatDate value="${ticketForm.date}" pattern="dd.MM.yyyy"/></p>
        <p>From ${ticketForm.departureStation} at <fmt:formatDate value="${ticketForm.departureTime}" pattern="HH:mm"/></p>
        <p>To ${ticketForm.arrivalStation} at <fmt:formatDate value="${ticketForm.arrivalTime}" pattern="HH:mm"/></p>
        <c:if test="${!empty passenger.id}">
            <p>Passenger Info: ${passenger.firstName} ${passenger.lastName}
                <fmt:formatDate value="${passenger.birthDate}" pattern="dd.MM.yyyy"/> ${passenger.passportNumber}</p>
        </c:if>
                <span style="color: red">${message}</span>
                <a href="/myaccount/${pageContext.request.userPrincipal.name}"
                   class="btn btn-secondary" role="button">Edit</a>
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/ticket/buy"
               class="btn btn-success" role="button">Confirm and Buy</a>
        </div>
    </div>
</div>

<div style="height: 100px"></div>

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