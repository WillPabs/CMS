<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Issue List</title>
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
	<!-- make sure its set to post -->
	<%@ include file="header.jsp"%>
	<div>

		<div>
			<table>
				<c:choose>
					<c:when test="${listType == 5}">
						<caption>All Unassigned Issues</caption>
					</c:when>
					<c:when test="${listType == 3}">
						<caption>All Department Issues</caption>
					</c:when>
					<c:when test="${listType == 1}">
						<caption>All Your Issues</caption>
					</c:when>
				</c:choose>
				<tr>
					<th>Issue Id</th>
					<th>Title</th>
					<th>Issue Description</th>
					<th>Admin Comments</th>
					<th>Assigned To</th>
					<th>Submitted By</th>
					<th>Status</th>
					<th>Priority</th>
					<th>Date Submitted</th>
					<th>Date Resolved</th>
					<th></th>
				</tr>
				<c:forEach var="issue" items="${issuesList}">
					<tr>
						<td><c:out value="${issue.issueId}"></c:out></td>
						<td><c:out value="${issue.title}"></c:out></td>
						<td><c:out value="${issue.issueDesc}"></c:out></td>
						<td><c:out value="${issue.adminComment}"></c:out></td>
						<td><c:out value="${issue.assignedToDeptId}"></c:out></td>
						<td><c:out value="${issue.author}"></c:out></td>
						<td><c:out value="${issue.status}"></c:out></td>
						<td><c:out value="${issue.priority}"></c:out></td>
						<td><c:out value="${issue.dateSubmitted}"></c:out></td>
						<td><c:out value="${issue.dateResolved}"></c:out></td>
						<td class="view_button">
							<form method="post" action="viewIssueDetails">
								<input type="hidden" name="issueId" value="${issue.issueId}" />
								<input type="submit" value="View" />
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>