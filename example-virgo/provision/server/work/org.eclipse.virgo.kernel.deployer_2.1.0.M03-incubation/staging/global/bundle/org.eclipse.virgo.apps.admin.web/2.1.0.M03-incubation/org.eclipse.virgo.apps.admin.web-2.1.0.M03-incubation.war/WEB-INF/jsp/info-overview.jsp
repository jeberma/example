<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%-- 
--%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%-- 
--%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%-- 
--%><jsp:include page="top.jsp" />

<h1>Admin Console - Information</h1>

<p>Congratulations on installing the Virgo Web Server. This is the Web console provided with the server to allow management of a single instance.</p>

<h2>Documentation</h2>
<p>Documentation is available on-line for the Virgo Web Server.</p>
<ul>
  <li><a href="http://www.eclipse.org/virgo/documentation" target="_blank" title="Virgo Server - Documentation">Virgo Server - Documentation</a>, includes links to the User, Programmer and Application Development Guides</li>
	<li><a href="http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/index.html" target="_blank" title="Spring Framework">Spring Framework Documentation</a>.</li>
	<li><a href="http://static.springframework.org/osgi/docs/current/reference/html/" target="_blank" title="Spring Dynamic Modules">Spring Dynamic Modules Documentation</a>.</li>
</ul>

<h2>Server Properties</h2>
<table id="properties" class="bordered-table">
	<tr class="sublevel1-even">
		<th>Name</th>
		<th>Value</th>
	</tr>
	<c:choose>
		<c:when test="${empty properties}">
			<tr class="name-sublevel1-odd">
				<td id="property_null" colspan="2">No properties have been registered.</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach var="property" items="${properties}" varStatus="loopStatus">
				<c:set var="rowStyle" value="even" scope="page" />
				<c:if test="${(loopStatus.index % 2) eq 0}">
					<c:set var="rowStyle" value="odd" scope="page" />
				</c:if>
				<tr class="sublevel1-${rowStyle}">
					<td id="property_key">${property.key}</td>
					<td id="property_value">${property.value}</td>
				</tr>
				<c:remove var="rowStyle" />
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>

<jsp:include page="bottom.jsp" />