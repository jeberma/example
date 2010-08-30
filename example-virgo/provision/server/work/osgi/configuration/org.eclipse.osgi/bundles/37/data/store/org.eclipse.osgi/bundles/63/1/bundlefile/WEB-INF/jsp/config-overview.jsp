<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%-- 
--%><jsp:include page="top.jsp" />

<script type="text/javascript">
	dojo.require("dijit.TitlePane");
</script>

<h1>Configuration Admin - Overview</h1>

<p>
	Configuration sets present in the system.
	<div class="consoleFootnote">Note: A Configuration artifact must be in the 'Active' state to be visible in config admin.</div>
</p>

<div class="consoleContentPaneMinimal">
	<c:forEach var="config" items="${configurations}">
		
		<div dojoType="dijit.TitlePane" id="${config.key}" title="${config.key}" open="false">
			<div class="consoleContentPaneSpace">
				<table id="config-${config.key}">
					<tr class="sublevel1-odd">
						<th>Property</th>
						<th>Value</th>
					</tr>
					<c:forEach var="prop" items="${config.value}" varStatus="loopStatus">
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

<script type="text/javascript" >
	if(location.hash){
		var paneId = location.hash.replace("#", "");
		pane = dojo.byId(paneId);
		if(pane){
			dojo.attr(pane, "open", "true");
		}
	}
</script>

<jsp:include page="bottom.jsp" />