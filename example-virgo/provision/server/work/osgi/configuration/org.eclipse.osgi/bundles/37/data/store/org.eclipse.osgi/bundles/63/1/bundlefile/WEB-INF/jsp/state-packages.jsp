<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%-- 
--%>
<jsp:include page="top.jsp" />

<script type="text/javascript">
	dojo.require("dijit.TitlePane");
</script>

<jsp:include page="state-header.jsp" />

<h1>${title}</h1>

<div dojoType="dijit.TitlePane" title="Exporters" open="true">
	<div class="consoleContentPaneSpace">
		<table id="exports-detail" >
			<tr class="sublevel1-odd">
				<th>Bundle Id</th>
				<th>Bundle Symbolic Name</th>
				<th>Bundle Version</th>
				<th>Export Version</th>
				<th>Export Directives</th>
				<th>Export Attributes</th>
			</tr>
			<c:forEach var="export" items="${exporters}" varStatus="loopStatus">
	<!-- ROW COLOURING -->
				<c:set var="rowStyle" value="odd" scope="page" />
				<c:if test="${(loopStatus.index % 2) eq 0}">
					<c:set var="rowStyle" value="even" scope="page" />
				</c:if>
	<!-- DISPLAY THE NEXT ROW -->
				<tr class="sublevel1-${rowStyle}">
					<td><a href="<c:url value="bundle.htm?id=${export.exportingBundle.bundleId}&state=${state}" />">${export.exportingBundle.bundleId}</a></td>
					<td>${export.exportingBundle.symbolicName}</td>
					<td>${export.exportingBundle.version}</td>
					<td>${export.version}</td>
					<td>
						<c:forEach var="directive" items="${export.directives}">
							${directive.key}:=${directive.value}<br/>
						</c:forEach>
					</td>
					<td>
						<c:forEach var="attribute" items="${export.attributes}">
							${attribute.key}=${attribute.value}<br/>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<div dojoType="dijit.TitlePane" title="Importers" open="false">
	<div class="consoleContentPaneSpace">
		<table id="imports-detail" >
			<tr class="sublevel1-odd">
				<th>Bundle Id</th>
				<th>Bundle Symbolic Name</th>
				<th>Bundle Version</th>
				<th>Import Version Constraint</th>
				<th>Import Directives</th>
				<th>Import Attributes</th>
			</tr>
			<c:forEach var="import" items="${importers}" varStatus="loopStatus">
	<!-- ROW COLOURING -->
				<c:set var="rowStyle" value="odd" scope="page" />
				<c:if test="${(loopStatus.index % 2) eq 0}">
					<c:set var="rowStyle" value="even" scope="page" />
				</c:if>
	<!-- DISPLAY THE NEXT ROW -->
				<tr class="sublevel1-${rowStyle}">
					<td><a href="<c:url value="bundle.htm?id=${import.importingBundle.bundleId}&state=${state}" />">${import.importingBundle.bundleId}</a></td>
					<td>${import.importingBundle.symbolicName}</td>
					<td>${import.importingBundle.version}</td>
					<td>${import.versionConstraint}</td>
					<td>
						<c:forEach var="directive" items="${import.directives}">
							${directive.key}:=${directive.value}<br/>
						</c:forEach>
					</td>
					<td>
						<c:forEach var="attribute" items="${import.attributes}">
							${attribute.key}=${attribute.value}<br/>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>

<jsp:include page="bottom.jsp" />