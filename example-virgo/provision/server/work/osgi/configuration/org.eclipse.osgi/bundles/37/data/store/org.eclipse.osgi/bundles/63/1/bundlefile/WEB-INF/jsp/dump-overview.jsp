<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%-- 
--%>

<jsp:include page="top.jsp" />

<c:choose>
		<c:when test="${empty formattedSelectedDump}">
			<h1>Dump Inspector</h1>
		</c:when>
		<c:otherwise>
			<h1>Dump Inspector: '${formattedSelectedDump}'</h1>
		</c:otherwise>
</c:choose>

<c:if test="${empty dumps}">
	There are no dumps available in the system for viewing.
</c:if>

<c:if test="${!empty dumps}">
	
	<table>
		<tr>
			<form name="dumpForm" action="<c:url value="entry.htm" />" method="get">
	            <td>
	                Dumps available for inspection:<br/>
			        <select class="dumpSelector" name="dumpID">
				        <c:forEach var="dump" items="${dumps}">
					         <c:choose>
	                            <c:when test="${selectedDump eq dump.key}">
	                                <option selected value="${dump.key}">${dump.value}</option>
	                            </c:when>
	                            <c:otherwise>
	                                <option value="${dump.key}">${dump.value}</option>
	                            </c:otherwise>
	                        </c:choose>
				        </c:forEach>
			        </select>
			        <br/> 
			        <input id="dump_selector_submit" type="submit" value="Select Dump"/>
	            </td>
			    <td>
			        Dump entries available for inspection:<br/>
			        <select class="dumpSelector" name="dumpEntryName">
				        <c:forEach var="entry" items="${entries}">
	                         <c:choose>
	                            <c:when test="${selectedEntry eq entry}">
	                                <option selected value="${entry}">${entry}</option>
	                            </c:when>
	                            <c:otherwise>
	                                <option value="${entry}">${entry}</option>
	                            </c:otherwise>
	                        </c:choose>
				        </c:forEach>
			        </select>
			        <br/>
	                <input id="dump_selector_submit" type="submit" value="Select Entry"/>
	            </td>
	        </form>
		</tr>
	
	</table>
	
	<c:if test="${!empty inspection}">
		<h2>Dump Entry Viewer</h2>
		<c:if test="${!empty selectedEntry}">
			<p>Viewing Entry '${selectedEntry}'.</p>
		</c:if>
		<div class="inspection-box">${inspection}</div>
	</c:if>
	
</c:if>

<jsp:include page="bottom.jsp" />