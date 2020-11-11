<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <c:if test="${empty train.name}">
        <title>Add Train</title>
    </c:if>
    <c:if test="${!empty train.name}">
        <title>Edit Train</title>
    </c:if>
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
    <h2 class="form-heading text-center">Edit train data:</h2>
    <div class="jumbotron">
        <c:if test="${empty train.track.id || empty train.name || empty train.capacity}">
            <c:url value="/trains/add" var="var"/>
        </c:if>
        <c:if test="${!empty train.track.id && !empty train.name && !empty train.capacity}">
            <c:url value="/trains/edit" var="var"/>
        </c:if>
        <form:form action="${var}" modelAttribute="train" method="POST"
                   class="needs-validation col-sm-10 offset-1" novalidate="true">
            <c:if test="${!empty train.name}">
                <input type="hidden" name="id" value="${train.id}">
            </c:if>
            <spring:bind path="track.id">
                <div class="form-group col-sm-4 offset-sm-4">
                    <c:if test="${empty train.name}">
                        <label for="track">Track</label>
                        <form:input path="track.id" type="number" class="form-control"
                                    id="track" autofocus="true" min="1" max="5" required="true"/>
                    </c:if>
                    <c:if test="${!empty train.name}}">
                        <form:input path="track.id" type="hidden" class="form-control"
                                    id="track" value="${train.track.id}"/>
                    </c:if>
                    <div class="invalid-feedback">Please enter a number from 1 to 5.</div>
                </div>
            </spring:bind>
            <spring:bind path="name">
                <div class="form-group col-sm-4 offset-sm-4">
                    <label for="name">Name</label>
                    <c:if test="${empty train.name}">
                        <form:input path="name" type="text" class="form-control ${status.error ? 'is-invalid' : ''}"
                                    id="name" pattern="^[a-zA-Z]+ [MDCLXVI]+$" required="true"/>
                    </c:if>
                    <c:if test="${!empty train.name}">
                        <form:input path="name" type="text" class="form-control ${status.error ? 'is-invalid' : ''}"
                                    id="name" value="${train.name}" autofocus="true"
                                    pattern="^[a-zA-Z]+ [MDCLXVI]+$" required="true"/>
                    </c:if>
                    <div class="invalid-feedback">
                        <c:set var="nameError"><form:errors path="name"/></c:set>
                        <c:if test="${!empty nameError}">${nameError}</c:if>
                        <c:if test="${empty nameError}">
                            Train name should consist of a Latin word and a Roman numeral separated by space.
                        </c:if>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="capacity">
                <div class="form-group col-sm-4 offset-sm-4">
                    <label for="capacity">Capacity</label>
                    <c:if test="${empty train.capacity}">
                        <form:input path="capacity" type="number" class="form-control"
                                    id="capacity" min="10" max="50" required="true"/>
                    </c:if>
                    <c:if test="${!empty train.capacity}">
                        <form:input path="capacity" type="number" class="form-control"
                                    id="capacity" value="${train.capacity}" min="10" max="50" required="true"/>
                    </c:if>
                    <div class="invalid-feedback">Please enter a number from 10 to 50.</div>
                </div>
            </spring:bind>
            <div style="height: 30px"></div>
            <div class="text-center">
                <button class="btn btn-outline-info btn-center" type="submit">Submit and proceed to Schedule
                </button>
            </div>
        </form:form>
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