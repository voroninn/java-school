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
    <title>Create an account</title>
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
        </div>
        <div class="navbar-nav ml-auto">
            <a href="<c:url value="/login"/>" class="btn btn-outline-light">Login</a>
        </div>
    </div>
</nav>

<div style="height: 100px"></div>

<div class="container">
    <form:form method="POST" modelAttribute="userForm" class="needs-validation form-signin" novalidate="true">
        <h2 class="form-heading text-center">Create your account</h2>
        <div class="jumbotron">
            <spring:bind path="username">
                <div class="form-group col-sm-4 offset-sm-4">
                    <form:input type="text" path="username" class="form-control ${status.error ? 'is-invalid' : ''}"
                                placeholder="Username" autofocus="true" min="6" max="32" required="true"/>
                    <div class="invalid-feedback">
                        <c:set var="usernameError"><form:errors path="username"/></c:set>
                        <c:if test="${!empty usernameError}">${usernameError}</c:if>
                        <c:if test="${empty usernameError}">
                            Please enter a valid username containing from 6 to 32 characters.
                        </c:if>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="email">
                <div class="form-group col-sm-4 offset-sm-4">
                    <form:input type="email" path="email" class="form-control ${status.error ? 'is-invalid' : ''}"
                                placeholder="Email" min="6" max="32"
                                pattern="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$" required="true"/>
                    <div class="invalid-feedback">
                        <c:set var="emailError"><form:errors path="email"/></c:set>
                        <c:if test="${!empty emailError}">${emailError}</c:if>
                        <c:if test="${empty emailError}">
                            Please enter a valid email containing from 6 to 32 characters.
                        </c:if>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="password">
                <div class="form-group col-sm-4 offset-sm-4">
                    <form:input type="password" path="password" class="form-control ${status.error ? 'is-invalid' : ''}"
                                placeholder="Password" min="6" max="32" required="true"/>
                    <div class="invalid-feedback">
                        <c:set var="passwordError"><form:errors path="password"/></c:set>
                        <c:if test="${!empty passwordError}">${passwordError}</c:if>
                        <c:if test="${empty passwordError}">Please enter a valid password.</c:if>
                    </div>
                </div>
            </spring:bind>
            <spring:bind path="passwordConfirm">
                <div class="form-group col-sm-4 offset-sm-4">
                    <form:input type="password" path="passwordConfirm"
                                class="form-control ${status.error ? 'is-invalid' : ''}"
                                placeholder="Confirm your password" required="true"/>
                    <div class="invalid-feedback">
                        <c:set var="passwordConfirmError"><form:errors path="passwordConfirm"/></c:set>
                        <c:if test="${!empty passwordConfirmError}">${passwordConfirmError}</c:if>
                        <c:if test="${empty passwordConfirmError}">Please confirm your password.</c:if>
                    </div>
                </div>
            </spring:bind>
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