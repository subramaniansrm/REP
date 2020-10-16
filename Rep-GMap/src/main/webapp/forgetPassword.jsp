<!DOCTYPE HTML>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
      <meta charset="utf-8">
      <title>List</title>

      <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
      <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
  </head>

<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <body>
      <h2>Forget Password</h2>
      <form:form method = "GET" action = "${pageContext.request.contextPath}/forgetPasswordEmail ">
         <table>
           
            <tr>
            	<label for="start">User Name:</label>

		<input type="text" id="start" name="dateVal"
       value="" >
            </tr> 
            
            <tr>
               <td colspan = "2">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
         </table>  
      </form:form>
   </body>
</html>