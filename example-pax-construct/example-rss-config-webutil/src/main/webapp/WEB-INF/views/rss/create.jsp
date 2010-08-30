<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Create Rss Feed</title>
	</head>
	<body>
		<form action="<c:url value="/app/rss/doCreate"/>" method="POST">
			<div>
				<label>Rss Feed Url</label>
				<input type="text" name="url"/>
			</div>
			<div>
				<input type="submit" value="Submit"/>
			</div>
		</form>
	</body>
</html>