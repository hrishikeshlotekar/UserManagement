<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="<s:url value='/styles/signup.css'/>">
    <script src="<s:url value='/script/validation.js'/>"></script>
</head>
<body>
<s:form action="signup" method="post" onsubmit="return validateSignUpForm()" name="signUpForm">
    <s:textfield name="name" label="Name" cssClass="input-field"/>
    <s:textfield name="email" label="Email" cssClass="input-field"/>
    <s:password name="password" label="Password" cssClass="input-field"/>
    <s:password name="confirmPassword" label="Confirm Password" cssClass="input-field"/>
    <s:submit value="Sign Up" cssClass="submit-button"/>
</s:form>
<s:if test="hasActionErrors()">
    <div class="error-messages">
        <s:actionerror/>
    </div>
</s:if>
</body>
</html>
