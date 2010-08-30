<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%-- 
--%>
<jsp:include page="top.jsp" />

<script type="text/javascript">
	dojo.require("dijit.TitlePane");
</script>

<jsp:include page="state-header.jsp" />

<h1>State Overview - Services</h1>

<div class="consoleContentPaneMinimal">

	<c:forEach var="service" items="${services}">
		<c:set var="titleString" value="Service ${service.serviceId} provided by bundle ${service.provider.bundleId} - ObjectClass ${service.formattedObjectClass}" />
		<div dojoType="dijit.TitlePane" title="${titleString}" open="false">
			<div class="consoleContentPaneSpace">
				<p>Provider <a href="<c:url value="bundle.htm?id=${service.provider.bundleId}&state=${state}" />">${service.provider.bundleId}</a> ${service.provider.symbolicName}-${service.provider.version}</p>
				<table id="service-properties">
					<tr class="sublevel1-odd">
						<th>Property</th>
						<th>Value</th>
					</tr>
					<c:forEach var="prop" items="${service.properties}" varStatus="loopStatus">
			<!-- ROW COLOURING -->
						<c:set var="rowStyle" value="odd" scope="page" />
						<c:if test="${(loopStatus.index % 2) eq 0}">
							<c:set var="rowStyle" value="even" scope="page" />
						</c:if>
			<!-- DISPLAY THE NEXT ROW -->
						<tr class="sublevel1-${rowStyle}">
							<td id="key">${prop.key}</td>
							<td id="value">${prop.value}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</c:forEach>

</div>

<jsp:include page="bottom.jsp" />