<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%-- 
--%>
<!-- 

-->
<div class="stateHeader">
	
	<div class="stateNavigation">
		<a href="<c:url value="bundles.htm?state=${state}" />">Bundles Overview</a>
		<c:if test="${state eq 'Live'}">
			<a href="<c:url value="services.htm" />">Services Overview</a>
		</c:if>
	</div>
	<form class="dumpStateSelector" name="dumpForm" action="<c:url value="bundles.htm" />" method="get">
		<fieldset>
			Viewing state '${fState}':  
			<select name="state">
				<option value="Live">Live</option>
				<c:forEach var="source" items="${stateSources}">
					<c:choose>
						<c:when test="${source.key eq state}">
							<option selected value="${source.key}">${source.value}</option>
						</c:when>
						<c:otherwise>
							<option value="${source.key}">${source.value}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
			<input id="state_selector_submit" type="submit" value="Go"/>
		</fieldset>
	</form>
	<form class="search" name="searchForm" action="<c:url value="search.htm" />" method="get">
		<fieldset>
			Search: 
			<input type="hidden" name="state" value="${state}" />
			<input type="text" name="term" /> 
			<input type="submit" name="submit" value="Go" />
		</fieldset>
	</form>
</div>
