<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>User Listing</title>
    <link rel="stylesheet" type="text/css" href="<s:url value='/styles/userListing.css'/>">
</head>
<body>
    <div class="container">

    <table border="1" width="100%">
        <thead>
            <tr>
                <th>User ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <s:iterator value="users" var="user">
                <tr>
                    <td><s:property value="#user.id"/></td>
                    <td><s:property value="#user.name"/></td>
                    <td><s:property value="#user.email"/></td>
                    <td>
                        <s:form action="deleteUser" method="post" style="display: inline;">
                            <s:hidden name="id" value="%{#user.id}"/>
                            <s:submit value="Delete" onclick="return confirm('Are you sure?');"/>
                        </s:form>
                    </td>
                </tr>
            </s:iterator>
        </tbody>
    </table>
    <!-- Pagination Controls -->
       <div class="pagination">
           <a href="<s:url action='listUsers'><s:param name='page' value='%{page - 1}'/></s:url>"
              class="<s:if test='%{page <= 0}'>active</s:if>">Previous</a>

           <a href="<s:url action='listUsers'><s:param name='page' value='%{page + 1}'/></s:url>"
              class="<s:if test='%{page >= totalPages - 1}'>active</s:if>">Next</a>

           <!-- Logout Link -->
           <s:if test="%{#session.user != null}">
                <a href="<s:url action='logout'/>">Logout</a>
           </s:if>
       </div>

    </div>

</body>
</html>
