<html>
	<head>
		<title>Register New User</title>
		<meta name="layout" content="main"/>
	</head>
	<body>
		<h1>Register New User</h1>
		<g:hasErrors>
			<div class="errors">
				<g:renderErrors bean="${user}" as="list"/>
			</div>
		</g:hasErrors>
		<g:form action="register">
			<dl>
				<dt>User Id</dt>
				<dd><g:textField name="userId" value="${user?.userId}"/></dd>
				<dt>Password</dt>
				<dd><g:passwordField name="password" value="${user?.password}"/></dd>
				<dt>Full Name</dt>
				<dd><g:textField name="profile.fullName" value="${user?.profile?.fullname}"/></dd>
				<dt>Bio</dt>
				<dd><g:textArea name="profile.bio" value="${user?.profile?.bio}"/></dd>
				<dt>Email</dt>
				<dd><g:textField name="profile.email" value="${user?.profile?.email}"/></dd>
				<dt><g:submitButton name="register" value="Register"/></dt>
			</dl>
		</g:form>
	</body>
</html>