<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Submit Issue</title>
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
	<div>
		<sf:form action="processIssue" method="POST" modelAttribute="issue">
			<sf:label path="title">Title</sf:label>
			<sf:input type="text" path="title" placeholder="Title"
				required="required" />
			<br>
			<sf:label path="issueDesc">Description of Issue</sf:label>
			<sf:textarea path="issueDesc" placeholder="Description of issue..."
				required="required" />
			<br>
			<label>Department Name</label>
			<select id="departmentName" name="departmentName" placeholder="Department" required>
				<option value="Unassigned" label="Unassigned" />
				<option value="Front" label="Front" />
				<option value="Middle" label="Middle" />
				<option value="Back" label="Back" />
			</select>
			<sf:label path="priority">Priority</sf:label>			
			<select id="priority" name="priority" path="priority" placeholder="Priority" required>
				<option value="high" label="High" />
				<option value="low" label="Low" />
				<option value="none" label="none" />
			</select>
			<br>
			<input type="submit" value="Submit" />
		</sf:form>
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>