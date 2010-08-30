<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %><%--
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%--
--%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
	<title>Virgo Admin Console</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link rel="shortcut icon" href="<c:url value="/resources/images/favicon.ico"             />" />
	<link rel="stylesheet" href="<c:url value="/resources/js/dijit/themes/tundra/tundra.css" />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/resources/styles/main.css"                   />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/resources/styles/local.css"                  />" type="text/css" />
	<link rel="stylesheet" href="<c:url value="/resources/styles/print.css"                  />" type="text/css" media="print" />
	<script type="text/javascript" src="<c:url value="/resources/js/dojo/dojo.js"            />" djConfig="parseOnLoad: true, baseUrl: '../../resources/js/dojo/'"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/dojo/virgo-dojo.js"      />" djConfig="parseOnLoad: true, baseUrl: '../../resources/js/dojo/'"></script>

</head>

<body class="main tundra">
    <div id="page">
        <div id="header">
            <div id="name-and-company">
                <div id='site-name'>
                    <a href="<c:url value="/" />" title="Virgo Admin Console" rel="home"> </a>
                </div>
                <div id='company-name'>
                    <a href="http://wiki.eclipse.org/Virgo" title="Virgo from EclipseRT" target="_blank"> </a>
                </div>
            </div> <!-- /name-and-company -->
        </div> <!-- /header -->

    <div id="primary-navigation">
        <div id="primary-left">
            <ul>
                <li><a href="<c:url value="/web/artifact/overview.htm" />" title="Artifacts">Artifacts</a></li>
                <li><a href="<c:url value="/web/config/overview.htm"   />" title="Configuration">Configuration</a></li>
                <li><a href="<c:url value="/web/dump/inspector.htm"    />" title="Dump Inspector">Dump Inspector</a></li>
                <li><a href="<c:url value="/web/state/bundles.htm"     />" title="OSGi State">OSGi State</a></li>
            </ul>
        </div>
        <img id="left-curve" src="<c:url value="/images/menu-curve-left.png"/>"/>
        <div id="primary-right">
            <ul>
                <li><a href="<c:url value="/web/info/overview.htm"     />" title="Information">Information</a></li>
            </ul>
        </div>
        <img id="right-curve" src="<c:url value="/images/menu-curve-right.png"/>"/>
    </div><!-- /primary-navigation -->

    <div id="container">
        <div id="content-no-nav">

