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
    <title>Error</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link href="${pageContext.request.contextPath}/assets/favicon.ico" rel="icon" type="image/x-icon"/>
</head>

<body>
<div style="height: 100px"></div>

<div class="container">
    <h2 class="text-center">Illegal Operation</h2>
    <div class="jumbotron">
        <div class="text-center">
            <div style="height: 50px">
                <h4>The operation you tried to perform is prohibited.</h4>
            </div>
            <div style="height: 50px">
                <h5>${message}</h5>
            </div>
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/" class="btn btn-warning" role="button">Back to Main</a>
            </div>
        </div>
        <div style="height: 50px"></div>
        <div class="text-center">
            <img src="${pageContext.request.contextPath}/assets/illegal_operation.jpg"
                 class="img-fluid" alt="Prohibited">
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