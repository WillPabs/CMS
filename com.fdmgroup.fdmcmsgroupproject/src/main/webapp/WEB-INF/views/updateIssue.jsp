<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Issue</title>
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
		<sf:form action="updateIssue" method="POST" modelAttribute="issue">
			<sf:label path="issueId">Issue Id</sf:label>
			<sf:input type="text" path="issueId" readonly="readonly" />
			<br>
			<sf:label path="author">Author Id</sf:label>
			<sf:input type="text" path="author" readonly="readonly" />
			<br>
			<sf:label path="issueDesc">Description of issue</sf:label>
			<sf:input type="text" path="issueDesc" readonly="readonly" />
			<br>

			<!-- give admin status a select button, otherwise render non-input field -->
			<c:choose>
				<c:when
					test="${ sessionScope.roleIdsSum == 4 || sessionScope.roleIdsSum == 9 }">
					<label>Status</label>
					<select id="status" name="status" path="status">
						<option value="">Status</option>
						<!-- 							<option value="OPEN" label="Open"/> -->
						<option value="PENDING" label="Pending" />
						<option value="RESOLVED" label="Resolved" />
						<!-- 							<option value="CLOSED" label="Closed" /> -->
					</select>
					<br>
				</c:when>
				<c:otherwise>
					<label>Status</label>
					<input type="text" name="status" readonly="readonly" />
					<br>
				</c:otherwise>
			</c:choose>
			<sf:label path="title">Title</sf:label>
			<sf:input type="text" path="title" placeholder="Title"
				required="required" />
			<br>
			<c:if test="${sessionScope.roleIdsSum == 1 }">
				<label>Description of Update</label>
				<textarea name="updateDetails"
					placeholder="Description of update..." required="required" /></textarea>
				<br>
			</c:if>

			<c:if
				test="${sessionScope.roleIdsSum == 4 || sessionScope.roleIdsSum== 9 }">
				<label>Admin Comments</label>
				<textarea name="adminComment" placeholder="Admin comments..."
					required="required" /></textarea>
				<br>
			</c:if>

			<!-- give admin status a select button, otherwise render non-input field -->

				<c:if
					test="${ sessionScope.roleIdsSum == 6 || sessionScope.roleIdsSum == 9 }">
					<label>Department Name</label>
					<select id="departmentName" name="departmentName"
						path="departmentName" placeholder="Department">
						<option value="1" label="Front" />
						<option value="2" label="Middle" />
						<option value="3" label="Back" />
					</select>
					<br>
				</c:if>
			<label>Priority</label>
			<select id="priority" name="priority" path="priority"
				placeholder="Priority">
				<option value="high" label="High" />
				<option value="low" label="Low" />
				<option value="none" label="none" />
			</select>
			<br>
			<label>Status</label>
			<c:set var="resolved" value="RESOLVED" />
			<c:if
				test="${ sessionScope.user.id == issue.author && issue.status == resolved }">
				<input type="checkbox" name="closed" value="CLOSED" />APPROVE<br>
			</c:if>
			<c:if
				test="${ sessionScope.roleIdsSum == 4 || sessionScope.roleIdsSum == 9 }">
				<input type="checkbox" name="closed" value="CLOSED" />REJECT<br>
			</c:if>
			<input type="submit" name="Submit" />
		</sf:form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>