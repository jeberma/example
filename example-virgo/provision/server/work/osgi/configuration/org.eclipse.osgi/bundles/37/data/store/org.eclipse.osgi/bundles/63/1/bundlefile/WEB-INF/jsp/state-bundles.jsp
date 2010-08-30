<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%-- 
--%>
<jsp:include page="top.jsp" />

<jsp:include page="state-header.jsp" />

<h1>State Overview - Bundles</h1>

<table id="bundles-overview" class="bordered-table">
	<tr>
		<th>Bundle Id</th>
		<th></th>
		<th>Bundle Symbolic Name</th>
		<th>Bundle Version</th>
		<th>Hosts/Fragments</th>
		<th>State</th>
	</tr>
	<c:choose>
		<c:when test="${empty bundles}">
			<tr class="name-sublevel1-even">
				<td id="deploy_application_null" colspan="6">No state information available.</td>
			</tr>
		</c:when>
		<c:otherwise>

			<c:forEach var="bundle" items="${bundles}" varStatus="loopStatus">
<!-- CREATE THE HOSTS FRAGMENTS STRING -->
				<c:set var="hostsFragmentsMessage" value="" />
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
<!-- CREATE THE RESOLVE STRING -->
				<c:choose>
					<c:when test="${bundle.resolved}">
						<c:set var="resolveMessage" value="Resolved" />
					</c:when>
					<c:otherwise>
						<c:set var="resolveMessage" value="Unresolved" />
					</c:otherwise>
				</c:choose>
<!-- DO THE ROW COLOURING -->
					<c:set var="rowStyle" value="odd" scope="page" />
					<c:if test="${(loopStatus.index % 2) eq 0}">
						<c:set var="rowStyle" value="even" scope="page" />
					</c:if>
<!-- PRINT OUT A LIVE OR STATIC TABLE ROW -->
					<c:choose>
						<c:when test="${state eq 'Live'}">			
							<tr class="sublevel1-${rowStyle}">
								<td id="bundle-id"><a href="<c:url value="bundle.htm?id=${bundle.bundleId}&state=${state}" />">${bundle.bundleId}</a></td>
								<td class="table-icon" id="bundle-spring"><c:if test="${!empty bundle.springName}"><img src="<c:url value="/resources/images/spring.png"/>" height="16" width ="16" alt="S" /></c:if></td>
								<td id="bundle-name">${bundle.symbolicName}</td>
								<td id="bundle-version">${bundle.version}</td>
						    	<td id="bundle-hf">${hostsFragmentsMessage}
									<c:forEach var="hfBundle" items="${hostsFragments}">
										<a href="<c:url value="bundle.htm?id=${hfBundle.bundleId}&state=${state}" />">${hfBundle.bundleId}</a>
									</c:forEach>
						    	</td>
						    	<td id="bundle-resolved">
						    		<c:choose>
						    			<c:when test="${bundle.resolved}">
						    				${bundle.state}
						    			</c:when>
						    			<c:otherwise>
						    				<a href="<c:url value="resolve.htm?id=${bundle.bundleId}&state=${state}" />">${bundle.state}</a>
						    			</c:otherwise>
						    		</c:choose>
						    	</td>
							</tr>
						</c:when>
						<c:otherwise>	
							<tr class="sublevel1-${rowStyle}">
								<td id="bundle-id"><a href="<c:url value="bundle.htm?id=${bundle.bundleId}&state=${state}" />">${bundle.bundleId}</a></td>
								<td id="bundle-spring" class="table-icon"></td>
								<td id="bundle-name">${bundle.symbolicName}</td>
								<td id="bundle-version">${bundle.version}</td>
						    	<td id="bundle-hf">${hostsFragmentsMessage}
									<c:forEach var="hfBundle" items="${hostsFragments}">
										<a href="<c:url value="bundle.htm?id=${hfBundle.bundleId}&state=${state}" />">${hfBundle.bundleId}</a>
									</c:forEach>
						    	</td>
						    	<td id="bundle-resolved">
						    		<c:choose>
						    			<c:when test="${bundle.resolved}">
						    				${resolveMessage}
						    			</c:when>
						    			<c:otherwise>
						    				<a href="<c:url value="resolve.htm?id=${bundle.bundleId}&state=${state}" />">${resolveMessage}</a>
						    			</c:otherwise>
						    		</c:choose>
						    	</td>
							</tr>
						</c:otherwise>
					</c:choose>
				<c:remove var="rowStyle" />
			</c:forEach>
		     
		</c:otherwise>
	</c:choose>
</table>

<jsp:include page="bottom.jsp" />