<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>SBB Home</title>
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
<form>
    <div class="form-group row">
        <div class="col-sm-4 offset-sm-4">
            <input type="from" class="form-control" id="inputFrom" placeholder="From">
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-4 offset-sm-4">
            <input type="to" class="form-control" id="inputTo" placeholder="To">
        </div>
    </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-10 offset-sm-4">
            <button type="submit" class="btn btn-danger">Search</button>
        </div>
    </div>
</form>
<div style="height: 100px"></div>
<div class="">
    <img src="https://sbb.imgix.net/content/dam/internet/sharedimages/zug/Eurocity-Im-Lavaux.jpg?crop=focalpoint&fp-x=0.5128125&fp-y=0.53828126&fp-z=1&w=2656&h=960&auto=format,compress,cs=tinysrgb&q=45" class="img-fluid" alt="Train">
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