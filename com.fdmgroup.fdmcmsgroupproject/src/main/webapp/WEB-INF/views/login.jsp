<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>
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
	<c:if test="${requestScope.errorLogin == 1}">
		<p>Fields were left blank</p>
	</c:if>
	<c:if test="${requestScope.errorUsername == 1 || requestScope.errorPassword == 1}">
		<p>Username or Password not found</p>
	</c:if>
	<form method="post" action="loginUser">
		<div class="field-container">
			<label> Username: </label> <input type="text" name="loginUsername"
				required /> <br>
		</div>
		<div class="field-container">
			<label> Password: </label> <input type="password" name="loginPassword"
				required /> <br>
		</div>

		<input class="loginBtn" type="submit" value="Login" />
		<br>
	</form>
	<%@ include file="footer.jsp" %>
</body>
</html>