<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Form</title>
</head>
<body>
<h2>User Form</h2>
<form action="/users/save" method="post">
    <input type="hidden" name="id" value="${user.id}" />
    Name: <input type="text" name="name" value="${user.name}" /><br>
    Email: <input type="email" name="email" value="${user.email}" /><br>
    <button type="submit">Save</button>
</form>
</body>
</html>