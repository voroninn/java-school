<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Log in</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/assets/favicon.ico" rel="icon" type="image/x-icon" />
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
    </div>
</nav>
<div style="height: 100px"></div>

<div class="container">
    <form method="POST" action="<c:url value="/login"/>" class="form-signin">
        <h2 class="form-heading text-center">Log in <sec:authorize access="hasRole('ROLE_ADMIN')">as user
        </sec:authorize></h2>
        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span style="color: green">${message}</span>
            <span style="color: red">${error}</span>
            <div class="jumbotron">
                <div class="form-group col-sm-4 offset-sm-4">
                    <input name="username" type="text" class="form-control" placeholder="Username"
                           autofocus="true"/>
                </div>
                <div class="form-group col-sm-4 offset-sm-4">
                    <input name="password" type="password" class="form-control" placeholder="Password"/>
                </div>
                <div class="form-group text-center">
                    <div class="form-check">
                        <input name="remember-me" id="remember-me" type="checkbox" class="form-check-input"/>
                        <label class="form-check-label" for="remember-me">Remember me</label>
                    </div>
                </div>
                    <div class="text-center">
                        <button class="btn btn-lg btn-primary btn-center" type="submit">Log in</button>
                    </div>
            </div>
            <h4 class="text-center"><a href="<c:url value="/registration"/>">Create an account</a></h4>
        </div>
    </form>
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