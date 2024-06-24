<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Error</h1>
    <p>${errorMessage}</p>
    <a href="<s:url action='login'/>">Login</a>
    <a href="<s:url action='register'/>">Register</a>
    <a href="<s:url action='index'/>">Home</a>
</body>
</html>
