<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>Add User</title>
    <link rel="stylesheet" type="text/css" href="<s:url value='/styles/addUser.css'/>">
    <script src="<s:url value='/script/validation.js'/>"></script>
</head>
<body>

<s:form action="addUser" method="post" onsubmit="return validateSignUpForm()" name="signUpForm" >
    <s:textfield name="name" label="Name" cssClass="input-field"/>
    <s:textfield name="email" label="Email" cssClass="input-field"/>
    <s:password name="password" label="Password" cssClass="input-field"/>
    <s:password name="confirmPassword" label="Confirm Password" cssClass="input-field"/>
    <s:submit value="Add User" cssClass="submit-button"/>
</s:form>
<s:if test="hasActionErrors()">
    <div class="error-messages">
        <s:actionerror/>
    </div>
</s:if>

</body>
</html>
