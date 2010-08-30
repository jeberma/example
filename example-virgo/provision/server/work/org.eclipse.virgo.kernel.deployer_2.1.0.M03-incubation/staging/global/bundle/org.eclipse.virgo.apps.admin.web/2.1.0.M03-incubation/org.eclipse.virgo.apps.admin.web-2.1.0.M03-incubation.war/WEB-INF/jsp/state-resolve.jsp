<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--  
--%>
<jsp:include page="top.jsp" />

<jsp:include page="state-header.jsp" />

<h1>${title}</h1>

<c:forEach var="fail" items="${failure}" >

	<c:set var="bundle" value="${fail.unresolvedBundle}" />
	<c:set var="hostsFragmentsMessage" value="NA" />
	<c:set var="hostsFragments" value="" />
	<c:choose>
		<c:when test="${!empty bundle.hosts}">
			<c:set var="hostsFragmentsMessage" value="Host: " />
			<c:set var="hostsFragments" value="${bundle.hosts}" />
		</c:when>
		<c:when test="${!empty bundle.fragments}">
			<c:set var="hostsFragmentsMessage" value="Fragments: " />
			<c:set var="hostsFragments" value="${bundle.fragments}" />						
		</c:when>
	</c:choose>

	<div class="consoleContentPane">
		<table id="resolve-overview-${bundle.symbolicName}-${bundle.version}">
			<tr class="name-sublevel1-even">
				<td>Bundle Symbolic Name</td>
				<td>${bundle.symbolicName}</td>
			</tr>
			<tr class="name-sublevel1-odd">
				<td>Bundle Version</td>
				<td>${bundle.version}</td>
			</tr>
			<tr class="name-sublevel1-even">
				<td>Bundle ID</td>
				<td>${bundle.bundleId}</td>
			</tr>
			<tr class="name-sublevel1-odd">
				<td>Hosts/Fragments</td>
				<td>
					${hostsFragmentsMessage}
					<c:forEach var="hfBundle" items="${hostsFragments}">
						<a href="<c:url value="bundle.htm?id=${hfBundle.bundleId}&state=${state}" />">${hfBundle.bundleId} (${hfBundle.symbolicName}-${hfBundle.version})</a>
					</c:forEach>
				</td>
			</tr>
			<c:choose>
				<c:when test="${state eq 'Live'}">
					<tr class="name-sublevel1-even">
						<td>Spring powered</td>
						<c:choose>
							<c:when test="${empty bundle.springName}">
								<td>No</td>
							</c:when>
							<c:otherwise>
								<td>Yes</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr class="name-sublevel1-odd">
						<td>State</td>
						<td>${bundle.state}</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr class="name-sublevel1-even">
						<td>State</td>
						<td><c:choose><c:when test="${bundle.resolved}">Resolved</c:when><c:otherwise>Unresolved</c:otherwise></c:choose></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		<p><pre>${fail.description}</pre></p>
	</div>
	<br/>
</c:forEach>

<jsp:include page="bottom.jsp" />