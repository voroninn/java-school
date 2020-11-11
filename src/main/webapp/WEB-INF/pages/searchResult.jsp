<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Search Result</title>
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
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <div class="btn-group">
                    <a href="<c:url value="/stations"/>" class="btn btn-outline-light">Stations</a>
                    <a href="<c:url value="/trains"/>" class="btn btn-outline-light">Trains</a>
                    <a href="<c:url value="/passengers"/>" class="btn btn-outline-light">Passengers</a>
                    <a href="<c:url value="/timetable"/>" class="btn btn-outline-light">Timetable</a>
                </div>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_USER')">
                <div class="btn-group">
                    <a href="/myaccount/${pageContext.request.userPrincipal.name}"
                       class="btn btn-outline-light">My Account</a>
                    <a href="/myaccount/${pageContext.request.userPrincipal.name}/tickets"
                       class="btn btn-outline-light">My Tickets</a>
                    <a href="<c:url value="/timetable"/>" class="btn btn-outline-light">Timetable</a>
                </div>
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
    <h2 class="text-center">Your best route from ${route[0].name}
        to ${route[route.size() - 1].name} on ${ticketForm.date}</h2>
    <div class="jumbotron">
        <div class="text-center">
            <div style="height: 50px">
                <strong>${schedules[0].departureTime} ${route[0].name}</strong>
                &LongRightArrow;
                <c:forEach var="station" items="${route}" begin="1" end="${route.size() - 2}">
                    ${station.name} &LongRightArrow;
                </c:forEach>
                <strong>${route[route.size() - 1].name} ${schedules[schedules.size() - 1].arrivalTime}</strong>
            </div>
            <div style="height: 50px">
                <p>Number of changes: ${numberOfChanges}</p>
            </div>
            <div style="height: 50px">
                <p>Price: ₪ <strong>${ticketForm.price}</strong></p>
            </div>
            <form:form action="/ticket/verify" modelAttribute="ticketForm" method="POST">
            <form:hidden path="departureTime" name="departureTime" value="${schedules.get(0).departureTime}"/>
            <form:hidden path="arrivalTime" name="arrivalTime"
                         value="${schedules.get(schedules.size() - 1).arrivalTime}"/>
            <div style="height: 50px">
                <button type="submit" class="btn btn-primary">Get a Ticket</button>
            </div>
        </div>
        <div class="text-center">
            <img src="${pageContext.request.contextPath}/assets/map.png" class="img-fluid" alt="Map of stations">
        </div>
    </div>
    </form:form>

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