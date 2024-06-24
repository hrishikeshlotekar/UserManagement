<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>User Management</title>
    <link rel="stylesheet" type="text/css" href="<s:url value='/styles/userManagement.css'/>">
</head>
<body>
<div class="container">
    <h1> User Management </h1>
    <div class="navigation">
        <a href="listUsers">User Listing</a>
        <a href="addUser">Create User</a>
        <!-- Logout Link -->
       <s:if test="%{#session.user != null}">
          <a href="<s:url action='logout'/>">Logout</a>
       </s:if>
    </div>
</div>
</body>
</html>