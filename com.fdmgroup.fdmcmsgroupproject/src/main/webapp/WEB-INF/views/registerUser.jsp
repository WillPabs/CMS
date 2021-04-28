<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register User</title>
<link href="<c:url value="/resources/style.css" />" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap"
	rel="stylesheet">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1" />
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
</head>
<body>
	<%@ include file="header.jsp" %>
	<a href="home">Home</a>
		<c:if test="${requestScope.errorPassword == 1}">
			<p>Password doesn't match</p>
		</c:if>
		<c:if test="${requestScope.errorUsername == 1}">
			<p>Username already exists</p>
		</c:if>
		<c:if test="${requestScope.errorAdded == 1}">
			<p>Error registering User</p>
		</c:if>
		<c:if test="${requestScope.errorEmail == 1}">
			<p>Email already exists</p>
		</c:if>
	<form method="post" action="addUser" >
		<div class="field-container">
			<label> Email: </label>
			<input type="email" name="email" required />
			<br>
		</div>
		<div class="field-container">
			<label > Department: </label>
			<select id="departmentName" name="departmentName" placeholder="Department" required>
<!-- 				<option value="">Department</option> -->
				<option value="Unassigned" label="Unassigned" />
				<option value="Front" label="Front" />
				<option value="Middle" label="Middle" />
				<option value="Back" label="Back" />
			</select>
			<br>
		</div>
		<div class="field-container">
			<label > First Name: </label>
			<input type="text" name="firstName" required />
			<br>
		</div>
		<div class="field-container">
			<label path="lastName"> Last Name: </label>
			<input type="text" name="lastName" required />
			<br>
		</div>
		<div class="field-container">
			<label> Username: </label>
			<input type="text" name="username" required />
			<br>
		</div><div class="field-container">
			<label> Password: </label>
			<input type="password" name="password" required />
			<br>
		</div>
		<div class="field-container">
			<label> Confirm Password: </label> 
			<input type="password" name="confirmPassword" required /> <br>
		</div>
		<input class="registerBtn" type="submit" value="Register" />
		<br>
	</form>
	<%@ include file="footer.jsp" %>
</body>
</html>