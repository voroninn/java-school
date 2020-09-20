<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:if test="${empty passenger.firstName}">
        <title>Add Passenger</title>
    </c:if>
    <c:if test="${!empty passenger.firstName}">
        <title>Edit Passenger</title>
    </c:if>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-md navbar-light fixed-top" style="background-color: #b22222">
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
<c:if test="${empty passenger.firstName}">
    <c:url value="/add" var="var"/>
</c:if>
<c:if test="${!empty passenger.firstName}">
    <c:url value="/edit" var="var"/>
</c:if>
<form action="${var}" method="POST" class="col-sm-10 offset-1">
    <c:if test="${!empty passenger.firstName}">
        <input type="hidden" name="id" value="${passenger.id}">
    </c:if>
    <div style="height: 100px">
        <label for="firstName">First Name</label>
        <c:if test="${empty passenger.firstName}">
            <input type="text" class="form-control" id="firstName">
        </c:if>
        <c:if test="${!empty passenger.firstName}">
            <input type="text" class="form-control" id="firstName" placeholder="${passenger.firstName}">
        </c:if>
    </div>
    <div style="height: 100px">
        <label for="lastName">Last Name</label>
        <c:if test="${empty passenger.lastName}">
            <input type="text" class="form-control" id="lastName">
        </c:if>
        <c:if test="${!empty passenger.lastName}">
            <input type="text" class="form-control" id="lastName" placeholder="${passenger.lastName}">
        </c:if>
    </div>
    <div style="height: 100px">
        <label for="birthDate">Birth Date</label>
        <c:if test="${empty passenger.birthDate}">
            <input type="text" class="form-control"
                   id="birthDate" placeholder="DD.MM.YYYY"
                   value = "<fmt:formatDate value="${passenger.birthDate}" pattern="dd.MM.yyyy"/>"/>
        </c:if>
        <c:if test="${!empty passenger.birthDate}">
                        <input type="text" class="form-control"
                   id="birthDate" placeholder="${passenger.birthDate}"
                   value = "<fmt:formatDate value="${passenger.birthDate}" pattern="dd.MM.yyyy"/>"/>
        </c:if>
    </div>
    <div style="height: 100px">
        <label for="passportNumber">Passport Number</label>
        <c:if test="${empty passenger.passportNumber}">
            <input type="text" class="form-control" id="passportNumber">
        </c:if>
        <c:if test="${!empty passenger.passportNumber}">
            <input type="text" class="form-control" id="passportNumber" placeholder="${passenger.passportNumber}">
        </c:if>
    </div>
    <button class="col-sm-10 offset-1 btn btn-outline-info btn-block"
            type="submit">Submit</button>
</form>
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
