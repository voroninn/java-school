<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Timetable</title>
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
            <a href="#" class="nav-item nav-link disabled" style="color: white">
                Logged in as <strong>${pageContext.request.userPrincipal.name}</strong></a>
            <a href="<c:url value="/logout"/>" class="btn btn-outline-light">Logout</a>
        </div>
    </div>
</nav>

<div style="height: 100px"></div>

<div class="container">
    <div class="jumbotron">
        <div id="accordion">
            <c:forEach var="station" varStatus="theCount" items="${stationsList}">
                <div class="card">
                    <div class="card-header" id="${station.name}">
                        <h5 class="mb-0">
                            <button class="btn btn-outline-dark btn-block" data-toggle="collapse"
                                    data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                                    ${station.name}
                            </button>
                        </h5>
                    </div>
                    <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                        <div class="card-body">
                            <table class="col-sm-10 offset-1 table table-sm table-striped">
                                <thead>
                                <tr>
                                    <th>Train</th>
                                    <th>Arrival</th>
                                    <th>Departure</th>
                                    <th>Direction</th>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <th>Actions</th>
                                    </sec:authorize>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="schedule" items="${schedules.get(theCount.index)}">
                                <tr>
                                    <td>${schedule.train.name}</td>
                                    <td>${schedule.arrivalTime}</td>
                                    <td>${schedule.departureTime}</td>
                                    <td>${schedule.endStation}</td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <td>
                                            <a href="/timetable/delay/${schedule.id}" class="btn btn-warning" role="button">Delay (15 min)</a>
                                            <a href="/timetable/cancel/${schedule.id}" class="btn btn-danger" role="button">Cancel</a>
                                        </td>
                                    </sec:authorize>
                                </tr>
                                </tbody>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
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