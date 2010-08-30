<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %><%-- 
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%-- 
--%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<title>Virgo Server - Splash</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<link rel="shortcut icon" href="<c:url value="images/favicon.ico"/>" />
	<link rel="stylesheet" href="<c:url value="styles/main.css"/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value="styles/local.css"/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value="styles/print.css"/>" type="text/css" media="print" />
</head>

<body class="main tundra">
    <div id="page">
        <div id="mini-header">
			<div id="mini-header-left"></div>
			<div id="mini-header-right"></div>
        </div> <!-- /mini-header -->

    <div id="primary-navigation">
        <div id="primary-left">

        </div>
        <img id="left-curve" src="images/menu-curve-left.png"/>
        <div id="primary-right">
            <ul>
                <li><a href="/admin" title="Admin Console">Admin Console</a></li>
            </ul>
        </div>
        <img id="right-curve" src="images/menu-curve-right.png"/>
    </div><!-- /primary-navigation -->

    <div id="container">
      <div id="content-no-nav">

        <div id="splash-container"><img class="splash" src="images/server-splash.png" alt="EclipseRT Virgo Server" />
        	<div id="version-text">${version}</div>
        </div>
        
        <h1>Welcome</h1>
        <p>
          Congratulations on installing the Virgo Server.
          From this splash page you can access the web-based <a href="/admin" title="Admin Console">admin console</a>.
        </p>
        
        <h1>Documentation</h1>
        <p>
          Documentation is available on-line for the server and related products.
        </p>
        <ul>
          <li><a href="http://www.eclipse.org/virgo/documentation" target="_blank" title="Virgo Server - Documentation">Virgo Server - Documentation</a>, includes links to the User, Programmer and Getting Started Guides</li>
          <li><a href="http://www.springsource.org/documentation" target="_blank" title="Spring Framework">Spring Framework Documentation</a></li>
          <li><a href="http://static.springframework.org/osgi/docs/current/reference/html/" target="_blank" title="Spring Dynamic Modules">Spring Dynamic Modules Documentation</a></li>
        </ul>
      </div><!-- /content -->
    </div><!-- /container -->
    
    <div id="footer-wrapper">
      <div id="footer-left">&copy; Copyright 2008, 2010 VMware Inc. Licensed under the Eclipse Public License v1.0.</div>

      <div id="footer-right"></div> 
    </div>

  </div> <!-- /page-->

</body>
</html>









