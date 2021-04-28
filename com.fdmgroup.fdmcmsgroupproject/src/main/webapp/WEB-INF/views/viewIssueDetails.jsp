<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Issue Details</title>
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
	<%@ include file="header.jsp"%>
	<div>
		<table>
			<caption>Issue Details</caption>
			<tr>
				<th>Category</th>
				<th>Details</th>
			</tr>
			<tr>
				<td>Issue Id</td>
				<td>${issue.issueId}</td>

			</tr>
			<tr>
				<td>Title</td>
				<td>${issue.title}</td>
			</tr>
			<tr>
				<td>Issue Description</td>
				<td>${issue.issueDesc}</td>
			</tr>
			<tr>
				<td>Administrator Comments</td>
				<td>${issue.adminComment}</td>
			</tr>
			<tr>
				<td>Department Assigned</td>
				<td>${issue.assignedToDeptId}</td>
			</tr>
			<tr>
				<td>Author Id</td>
				<td>${issue.author}</td>
			</tr>
			<tr>
				<td>Status</td>
				<td>${issue.status}</td>
			</tr>
			<tr>
				<td>Priority</td>
				<td>${issue.priority}</td>
			</tr>
			<tr>
				<td>Date Submitted</td>
				<td>${issue.dateSubmitted}</td>
			</tr>
			<tr>
				<td>Date Resolved</td>
				<td>${issue.dateResolved}</td>
			</tr>
		</table>
		<form class="update_button" method="post" action="updateIssueDetails">
			<input type="hidden" name="issueId" value="${issue.issueId}" />
			<input type="submit" value="Update" />
		</form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>