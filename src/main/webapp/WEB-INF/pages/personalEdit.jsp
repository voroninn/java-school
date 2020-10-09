<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>My Account</title>
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
                <a href="/myaccount/${pageContext.request.userPrincipal.name}"
                   class="nav-item nav-link active" style="color: white">My Account</a>
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
    <form:form action="/myaccount" modelAttribute="passengerForm" method="POST">
        <h2 class="form-heading text-center">Edit your personal data: </h2>
        <div class="jumbotron">
            <c:if test="${!empty passengerForm.firstName}">
                <input type="hidden" name="id" value="${passengerForm.id}">
            </c:if>
            <div class="form-group col-sm-4 offset-sm-4">
                <label for="firstName">First Name</label>
                <c:if test="${empty passengerForm.firstName}">
                    <form:input path="firstName" type="text" class="form-control" id="firstName" autofocus="true"/>
                </c:if>
                <c:if test="${!empty passengerForm.firstName}">
                    <form:input path="firstName" type="text" class="form-control"
                                id="firstName" placeholder="${passengerForm.firstName}" autofocus="true"/>
                    </c:if>
            </div>
            <div class="form-group col-sm-4 offset-sm-4">
                <label for="lastName">Last Name</label>
                <c:if test="${empty passengerForm.lastName}">
                    <form:input path="lastName" type="text" class="form-control" id="lastName"/>
                </c:if>
                <c:if test="${!empty passengerForm.lastName}">
                    <form:input path="lastName" type="text" class="form-control" id="lastName"
                                placeholder="${passengerForm.lastName}"/>
                </c:if>
            </div>
            <div class="form-group col-sm-4 offset-sm-4">
                <label for="birthDate">Birth Date</label>
                <c:if test="${empty passengerForm.birthDate}">
                    <form:input path="birthDate" type="text" class="form-control"
                                id="birthDate" placeholder="DD.MM.YYYY"/>
                </c:if>
                <c:if test="${!empty passengerForm.birthDate}">
                    <form:input path="birthDate" type="text" class="form-control"
                                id="birthDate" placeholder="<${passengerForm.birthDate}"/>
                </c:if>
            </div>
            <div class="form-group col-sm-4 offset-sm-4">
                <label for="passportNumber">Passport Number</label>
                <c:if test="${empty passengerForm.passportNumber}">
                    <form:input path="passportNumber" type="text" class="form-control"
                                id="passportNumber" placeholder=""/>
                </c:if>
                <c:if test="${!empty passengerForm.passportNumber}">
                    <form:input path="passportNumber" type="text" class="form-control"
                                id="passportNumber" placeholder="${passengerForm.passportNumber}"/>
                </c:if>
            </div>
            <div class="text-center">
                <button class="btn btn-lg btn-primary btn-center" type="submit">Submit</button>
            </div>
        </div>
    </form:form>
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