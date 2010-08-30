<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%-- 
--%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%-- 
--%><jsp:include page="top.jsp" />

<script type="text/javascript">
	dojo.require("dijit.TitlePane");
</script>

<jsp:include page="state-header.jsp" />

<h1>${title}</h1>

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

<table id="bundles-overview" class="bordered-table">
	<tr class="sublevel1-odd">
		<td>Bundle Symbolic name</td>
		<td>${bundle.symbolicName}</td>
	</tr>
	<tr class="sublevel1-even">
		<td>Bundle Version</td>
		<td>${bundle.version}</td>
	</tr>
	<tr class="sublevel1-odd">
		<td>Bundle ID</td>
		<td>${bundle.bundleId}</td>
	</tr>
	<tr class="sublevel1-even">
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
			<tr class="sublevel1-odd">
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
			<tr class="sublevel1-even">
				<td>State</td>
				<td><c:choose><c:when test="${bundle.resolved}">${bundle.state}</c:when><c:otherwise><a href="<c:url value="resolve.htm?id=${bundle.bundleId}&state=${state}" />">${bundle.state}</a></c:otherwise></c:choose></td>
			</tr>
		</c:when>
		<c:otherwise>
			<tr class="sublevel1-odd">
				<td>Spring powered</td>
				<td>NA</td>
			</tr>
			<tr class="sublevel1-even">
				<td>State</td>
				<td><c:choose><c:when test="${bundle.resolved}">Resolved</c:when><c:otherwise><a href="<c:url value="resolve.htm?id=${bundle.bundleId}&state=${state}" />">Unresolved</a></c:otherwise></c:choose></td>
			</tr>
		</c:otherwise>
	</c:choose>
	<tr class="sublevel1-odd">
		<td>Bundle Location</td>
		<td>${bundle.bundleLocation}</td>
	</tr>
</table>
	
	<c:set var="imports" value="${bundle.importPackages}" />
	<div dojoType="dijit.TitlePane" title="Imported Packages (${fn:length(imports)})" open="false">
		<div class="consoleContentPaneSpace">
			<table id="imports-detail" >
				<tr class="sublevel1-odd">
					<th>Package Name</th>
					<th>Version Constraint</th>
					<th>Provider</th>
					<th>Import Directives</th>
					<th>Import Attributes</th>
				</tr>
				<c:forEach var="import" items="${imports}" varStatus="loopStatus">
		<!-- CREATE THE PROVIDER STRING -->
					<c:choose>
						<c:when test="${import.provider eq null}">
							<c:set var="provider" value=""/>
						</c:when>
						<c:otherwise>
							<c:set var="provider" value="${import.provider.exportingBundle.bundleId} (${import.provider.exportingBundle.symbolicName} - ${import.provider.exportingBundle.version})"/>
						</c:otherwise>
					</c:choose>
		<!-- ROW COLOURING -->
					<c:set var="rowStyle" value="odd" scope="page" />
					<c:if test="${(loopStatus.index % 2) eq 0}">
						<c:set var="rowStyle" value="even" scope="page" />
					</c:if>
		<!-- DISPLAY THE NEXT ROW -->
					<tr class="sublevel1-${rowStyle}">
						<td><a href="<c:url value="packages.htm?name=${import.packageName}&state=${state}" />">${import.packageName}</a></td>
						<td>${import.versionConstraint}</td>
						<td><a href="<c:url value="bundle.htm?id=${import.provider.exportingBundle.bundleId}&state=${state}" />">${provider}</a></td>
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
	
	<c:set var="exports" value="${bundle.exportPackages}" />
	<div dojoType="dijit.TitlePane" title="Exported Packages (${fn:length(exports)})" open="false">
		<div class="consoleContentPaneSpace">
			<table id="exports-detail" >
				<tr class="sublevel1-odd">
					<th>Package Name</th>
					<th>Export Version</th>
					<th>Consumers</th>
					<th>Export Directives</th>
					<th>Export Attributes</th>
				</tr>
				<c:forEach var="export" items="${exports}" varStatus="loopStatus">
		<!-- ROW COLOURING -->
					<c:set var="rowStyle" value="odd" scope="page" />
					<c:if test="${(loopStatus.index % 2) eq 0}">
						<c:set var="rowStyle" value="even" scope="page" />
					</c:if>
		<!-- DISPLAY THE NEXT ROW -->
					<tr class="sublevel1-${rowStyle}">
						<td><a href="<c:url value="packages.htm?name=${export.packageName}&state=${state}" />">${export.packageName}</a></td>
						<td>${export.version}</td>
						<td>${fn:length(export.consumers)}</td>
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
	
	<c:set var="requires" value="${bundle.requiredBundles}" />
	<div dojoType="dijit.TitlePane" title="Required Bundles (${fn:length(requires)})" open="false">
		<div class="consoleContentPaneSpace">
			<table id="requires-detail" >
				<tr class="sublevel1-odd">
					<th>Bundle Name</th>
					<th>Version</th>
					<th>Provider</th>
					<th>Directives</th>
					<th>Attributes</th>
				</tr>
				<c:forEach var="require" items="${requires}" varStatus="loopStatus">
		<!-- CREATE THE PROVIDER STRING -->
					<c:choose>
						<c:when test="${require eq null}">
							<c:set var="provider" value=""/>
						</c:when>
						<c:otherwise>
							<c:set var="provider" value="${require.provider.exportingBundle.bundleId} (${require.provider.exportingBundle.symbolicName} - ${require.provider.exportingBundle.version})"/>
						</c:otherwise>
					</c:choose>
		<!-- ROW COLOURING -->
					<c:set var="rowStyle" value="odd" scope="page" />
					<c:if test="${(loopStatus.index % 2) eq 0}">
						<c:set var="rowStyle" value="even" scope="page" />
					</c:if>
		<!-- DISPLAY THE NEXT ROW -->
					<tr class="sublevel1-${rowStyle}">
						<td><a href="<c:url value="bundle.htm?name=${require.requiredBundleName}&state=${state}" />">${require.requiredBundleName}</a></td>
						<td>${require.versionConstraint}</td>
						<td>${provider}</td>
						<td>
							<c:forEach var="directive" items="${require.directives}">
								${directive.key}:=${directive.value}<br/>
							</c:forEach>
						</td>
						<td>
							<c:forEach var="attribute" items="${require.attributes}">
								${attribute.key}=${attribute.value}<br/>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
		
	<c:if test="${state eq 'Live'}">
		<c:if test="${!empty bundle.springName && !empty bundle.beans }">
			<div dojoType="dijit.TitlePane" title="Spring Context (${fn:length(bundle.beans)} beans)" open="false">
				<div class="consoleContentPaneSpace">
					<p>${bundle.springName}</p>
					<table id="spring-detail">
						<tr class="sublevel1-odd">
							<th>Bean Name</th>
							<th>Class</th>
							<th>Singleton</th>
							<th>Prototype</th>
						</tr>
						<c:forEach var="bean" items="${bundle.beans}" varStatus="loopStatus">
			<!-- ROW COLOURING -->
							<c:set var="rowStyle" value="odd" scope="page" />
							<c:if test="${(loopStatus.index % 2) eq 0}">
								<c:set var="rowStyle" value="even" scope="page" />
							</c:if>
			<!-- DISPLAY THE NEXT ROW -->
							<tr class="sublevel1-${rowStyle}">
								<td>${bean.name}</td>
								<td>${bean.type}</td>
								<td>${bean.prototype}</td>
								<td>${bean.singleton}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</c:if>
	
		<c:set var="consumedServices" value="${bundle.importedServices}" />			
		<div dojoType="dijit.TitlePane" title="Consumed Services (${fn:length(consumedServices)})" open="false">
			<div class="consoleContentPaneMinimal">
			
				<c:forEach var="service" items="${consumedServices}">
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
		
		</div>
		
		<c:set var="providedServices" value="${bundle.exportedServices}" />
		<div dojoType="dijit.TitlePane" title="Published Services (${fn:length(providedServices)})" open="false">
			<div class="consoleContentPaneMinimal">
			
				<c:forEach var="service" items="${providedServices}">
					<c:set var="titleString" value="Service ${service.serviceId} - ObjectClass ${service.formattedObjectClass}" />
					<div dojoType="dijit.TitlePane" title="${titleString}" open="false">
						<div class="consoleContentPaneSpace">
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
		</div>
	</c:if>

<jsp:include page="bottom.jsp" />