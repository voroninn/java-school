<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Passengers</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-md navbar-light sticky-top" style="background-color: #b22222">
    <a href="#" class="navbar-brand" style="color: white">SBB</a>
    <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarCollapse">
        <div class="navbar-nav">
            <a href="<c:url value="/"/>" class="nav-item nav-link active" style="color: white">Home</a>
            <a href="#" class="nav-item nav-link" style="color: white">Timetable</a>
            <a href="#" class="nav-item nav-link" style="color: white">Tickets</a>
            <a href="<c:url value="/passengers"/>" class="nav-item nav-link" style="color: white">Passengers</a>
        </div>
        <div class="navbar-nav ml-auto">
            <a href="#" class="nav-item nav-link" style="color: white">Login</a>
        </div>
    </div>
</nav>
<div style="height: 100px"></div>
<table class="col-sm-10 offset-1 table table-striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Birth Date</th>
        <th>Passport Number</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="passenger" items="${passengersList}">
        <tr>
            <td>${passenger.id}</td>
            <td>${passenger.firstName}</td>
            <td>${passenger.lastName}</td>
            <td>${passenger.birthDate}</td>
            <td>${passenger.passportNumber}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
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
