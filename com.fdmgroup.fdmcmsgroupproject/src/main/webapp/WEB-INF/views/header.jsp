<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<header>
	<div class="header-bar">
		<div class="row">
			<a href="home"><img width="126" height="60" class="inline-logo"
				src="<c:url value="/resources/assets/fdm-logo.png"/>" /></a>
			<c:if test="${sessionScope.user != null}">
				<h1 style="color: white;">
					Welcome <c:out value="${sessionScope.user.firstName}" />
				</h1>
			</c:if>
		</div>
	</div>
	</header>
</body>
</html>