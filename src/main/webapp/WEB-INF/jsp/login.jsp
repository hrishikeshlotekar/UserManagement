<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="<s:url value='/styles/login.css'/>">
    <script src="<s:url value='/script/validation.js'/>"></script>
</head>
<body>
<s:form action="login" method="post" onsubmit="return validateLoginForm()" name="loginForm">
    <s:textfield name="username" label="Username" cssClass="input-field"/>
    <s:password name="password" label="Password" cssClass="input-field"/>
    <s:submit value="Login" cssClass="submit-button"/>
</s:form>
<s:if test="hasActionErrors()">
    <div class="error-messages">
        <s:actionerror/>
    </div>
</s:if>
</body>
</html>
