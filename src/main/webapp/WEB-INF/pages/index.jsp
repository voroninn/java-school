<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SBB Home</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/assets/favicon.ico" rel="icon" type="image/x-icon"/>
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
                <a href="<c:url value="/login"/>" class="btn btn-outline-light">Login</a>
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

<div class="media">
    <div class="media-body align-self-center col-sm-4">
        <form:form action="/schedule" modelAttribute="ticketForm" method="POST">
            <spring:bind path="departureStation">
                <div class="form-group row">
                    <div class="input-group text-center col-sm-11 offset-sm-1">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="departureStation">From</label>
                        </div>
                        <form:select class="custom-select ${status.error ? 'is-invalid' : ''}" path="departureStation"
                                     name="departureStation" id="departureStation">
                            <option value="">Select Station</option>
                            <c:forEach var="station" items="${stationsList}">
                                <option value="${station.name}">${station.name}</option>
                            </c:forEach>
                        </form:select>
                        <div class="invalid-feedback"><form:errors path="departureStation"/></div>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="arrivalStation">
                <div class="form-group row">
                    <div class="input-group text-center col-sm-11 offset-sm-1">
                        <div class="input-group-prepend">
                            <label class="input-group-text" for="arrivalStation">To</label>
                        </div>
                        <form:select class="custom-select ${status.error ? 'is-invalid' : ''}" path="arrivalStation"
                                     name="arrivalStation" id="arrivalStation">
                            <option selected value="">Select Station</option>
                            <c:forEach var="station" items="${stationsList}">
                                <option value="${station.name}">${station.name}</option>
                            </c:forEach>
                        </form:select>
                        <div class="invalid-feedback"><form:errors path="arrivalStation"/></div>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="date">
                <div class="form-group row">
                    <div class="col-sm-11 offset-sm-1">
                        <form:input class="form-control ${status.error ? 'is-invalid' : ''}" path="date" name="date"
                                    id="date" placeholder="DD.MM.YYYY"/>
                        <div class="invalid-feedback"><form:errors path="date"/></div>
                    </div>
                </div>
            </spring:bind>
            <div class="form-group row">
                <div class="col-sm-11 offset-sm-1">
                    <button type="submit" class="btn btn-danger">Search</button>
                </div>
            </div>
        </form:form>
    </div>
    <img class="img-fluid align-self-center mr-3 col-sm-8" src="${pageContext.request.contextPath}/assets/graph.png"
         alt="Map of stations">
</div>

<div style="height: 100px"></div>

<div class="text-center">
    <img src="${pageContext.request.contextPath}/assets/spacetrain.jpg" class="img-fluid" alt="Space train">
</div>

<footer class="page-footer font-small">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#">JavaSchool</a>
    </div>
</footer>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/validate.js/0.13.1/validate.min.js"></script>
</body>
</html>