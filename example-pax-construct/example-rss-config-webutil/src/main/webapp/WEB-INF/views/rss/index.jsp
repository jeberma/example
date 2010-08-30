<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Rss Feeds</title>
	</head>
	<body>
		<ul>
			<c:forEach var="feed" items="${feeds}">
				<li>
					<a href="${feed.url}">${feed.url}</a>
				</li>
			</c:forEach>
		</ul>
		<div>
			<a href="<c:url value="/app/rss/create"/>">New Rss Feed</a>
		</div>
	</body>
</html>