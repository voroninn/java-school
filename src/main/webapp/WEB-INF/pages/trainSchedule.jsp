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
                <a href="<c:url value="/timetable"/>" class="btn btn-outline-light">Timetable</a>
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
    <h2 class="form-heading text-center">Edit train schedule:</h2>
    <div class="jumbotron">
        <form action="/trains/edit/${train.id}/schedule" method="GET" class="col-sm-10 offset-1">
            <input type="hidden" name="reverse" value="true">
            <button class="col-sm-10 offset-1 btn btn-outline-info btn-block"
                    type="submit">Reverse Schedule
            </button>
        </form>
        <form action="/trains/edit/schedule" method="POST" class="needs-validation col-sm-10 offset-1" novalidate>
            <input type="hidden" name="isReversed" value="${isReversed}">
            <table class="col-sm-10 offset-1 table table-striped">
                <thead>
                <tr>
                    <th>Station</th>
                    <th>Arrival</th>
                    <th>Departure</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <c:if test="${!empty schedulesList[0].id}">
                        <input type="hidden" name="id0" value="${schedulesList[0].id}">
                    </c:if>
                    <input type="hidden" name="station0" value="${schedulesList[0].station.name}">
                    <td>${schedulesList[0].station.name}</td>
                    <td>
                        <input type="text" class="form-control"
                               name="arrivalTime1" disabled/>
                    </td>
                    <td>
                        <c:if test="${empty schedulesList[0].departureTime}">
                            <input type="text" class="form-control" name="departureTime0"
                                   placeholder="HH:MM" pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$" required/>
                        </c:if>
                        <c:if test="${!empty schedulesList[0].departureTime}">
                            <input type="text" class="form-control" name="departureTime0"
                                   value="${schedulesList[0].departureTime}"
                                   pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$" required/>
                        </c:if>
                    </td>
                </tr>
                <c:forEach var="schedule" items="${schedulesList}" begin="1" end="${schedulesList.size() - 2}">
                    <tr>
                        <c:if test="${!empty schedule.id}">
                            <input type="hidden" name="id${schedulesList.indexOf(schedule)}" value="${schedule.id}">
                        </c:if>
                        <input type="hidden" name="station${schedulesList.indexOf(schedule)}"
                               value="${schedule.station.name}">
                        <td>${schedule.station.name}</td>
                        <td>
                            <c:if test="${empty schedule.arrivalTime}">
                                <input type="text" class="form-control"
                                       name="arrivalTime${schedulesList.indexOf(schedule)}"
                                       placeholder="HH:MM" pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$" required/>
                            </c:if>
                            <c:if test="${!empty schedule.arrivalTime}">
                                <input type="text" class="form-control"
                                       name="arrivalTime${schedulesList.indexOf(schedule)}"
                                       value="${schedule.arrivalTime}"
                                       pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$" required/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${empty schedule.departureTime}">
                                <input type="text" class="form-control"
                                       name="departureTime${schedulesList.indexOf(schedule)}"
                                       placeholder="HH:MM" pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$" required/>
                            </c:if>
                            <c:if test="${!empty schedule.departureTime}">
                                <input type="text" class="form-control"
                                       name="departureTime${schedulesList.indexOf(schedule)}"
                                       value="${schedule.departureTime}"
                                       pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$" required/>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <c:if test="${!empty schedulesList[schedulesList.size() - 1].id}">
                        <input type="hidden" name="id${schedulesList.size() - 1}"
                               value="${schedulesList[schedulesList.size() - 1].id}">
                    </c:if>
                    <input type="hidden" name="station${schedulesList.size() - 1}"
                           value="${schedulesList[schedulesList.size() - 1].station.name}">
                    <td>${schedulesList[schedulesList.size() - 1].station.name}</td>
                    <td>
                        <c:if test="${empty schedulesList[schedulesList.size() - 1].arrivalTime}">
                            <input type="text" class="form-control" name="arrivalTime${schedulesList.size() - 1}"
                                   placeholder="HH:MM" pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$" required/>
                        </c:if>
                        <c:if test="${!empty schedulesList[schedulesList.size() - 1].arrivalTime}">
                            <input type="text" class="form-control" name="arrivalTime${schedulesList.size() - 1}"
                                   value="${schedulesList[schedulesList.size() - 1].arrivalTime}"
                                   pattern="^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$" required/>
                        </c:if>
                    </td>
                    <td>
                        <input type="text" class="form-control"
                               name="departureTime${schedulesList.size()}" disabled/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="invalid-feedback">Please enter time in "HH:MM" format.</div>
            <button class="col-sm-10 offset-1 btn btn-outline-info btn-block" type="submit">Submit</button>
        </form>
    </div>
</div>

<footer class="page-footer font-small">
    <div class="footer-copyright text-center py-3">© 2020 Copyright:
        <a href="#">JavaSchool</a>
    </div>
</footer>

<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            let forms = document.getElementsByClassName('needs-validation');
            let validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</body>
</html>