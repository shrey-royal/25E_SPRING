<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>MySpringApp</title>
	</head>
	<body>
		<form action="/sent" method="post">
		    <label for="name">Name:</label>
		    <input type="text" name="name" id="name" />
		    <label for="email">Email:</label>
		    <input type="email" name="email" id="email" />
			
			<input type="submit" value="Register" />
		</form>
	</body>
</html>