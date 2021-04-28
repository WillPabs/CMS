<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CMS</title>
<link href="<c:url value="/resources/style.css" />" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Roboto&display=swap"
	rel="stylesheet">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1" />
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<body>
	<%@ include file="header.jsp"%>
	
	<c:if test="${confirmIssueSubmitted == 1}">
			<p>Issue # </p><c:out value="${ issueId }"/><p> Submitted Successfully</p>
	</c:if>
	<sf:form action="submitIssue" method="post" modelAttribute="issue">
		<input class="registerBtn" type="submit" value="Submit New Issue" />
	</sf:form>
	

	<c:choose>
		<c:when test="${ sessionScope.roleIdsSum == 9 }">
			<form action="generalAdminList" method="post">
				<input class="registerBtn" type="submit" value="View All Issues" />
			</form>
			<form action="departmentAdminList" method="post">
				<input class="registerBtn" type="submit"
					value="View Department Issues" />
			</form>
			<form action="basicUserList" method="post">
				<input class="registerBtn" type="submit" value="View Your Issues" />
			</form>
		</c:when>
		<c:when test="${ sessionScope.roleIdsSum == 6 }">
			<form action="generalAdminList" method="post">
				<input class="registerBtn" type="submit" value="View All Issues" />
			</form>
			<form action="basicUserList" method="post">
				<input class="registerBtn" type="submit" value="View Your Issues" />
			</form>
		</c:when>
		<c:when test="${ sessionScope.roleIdsSum == 4 }">
			<form action="departmentAdminList" method="post">
				<input class="registerBtn" type="submit"
					value="View Department Issues" />
			</form>
			<form action="basicUserList" method="post">
				<input class="registerBtn" type="submit" value="View Your Issues" />
			</form>
		</c:when>
		<c:otherwise>
			<form action="basicUserList" method="post">
				<input class="registerBtn" type="submit" value="View Your Issues" />
			</form>
		</c:otherwise>
	</c:choose>

	<form action="logout" method="post">
		<input class="registerBtn" type="submit" value="Logout" />
	</form>
	<%@ include file="footer.jsp"%>
</body>
</html>