<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
  <head>
      <meta charset="utf-8">
      <title>List of Real Estate Data</title>

      <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
      <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
      
      <style>
h2 {text-align: right;}
</style>
  </head>

  <body>

    <div class="container">
        <form:form method="GET"  action="${pageContext.request.contextPath}/listUser" modelAttribute="listUser" class="form-signin">
            <table border="1">
	<tr><td>S.No</td><td>User Name</td></tr>
	<c:forEach var="user" items="${listUser}" >
		<tr>
			<td>${(index.index)+1}</td>
			<td>${user.username}</td>
			<td>
                    <a href="/edit/${user.id}">Edit</a>
                    &nbsp;&nbsp;&nbsp;
                    <a href="/@{'/delete/' + ${user.id}}">Delete</a>
                </td>
		</tr>
			
	</c:forEach>
</table>
        </form:form>

    </div>
    

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
  </body>
</html>