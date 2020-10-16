<!DOCTYPE HTML>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<head>
      <meta charset="utf-8">
      <title>List</title>

      <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
      <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
      <style>
    .error 
    {
        color: #ff0000;
        font-weight: bold;
    }
    </style>
  </head>

<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
   <body>
      <h2>Real Estate Upload Data</h2>
      <form:form method = "POST" action = "${pageContext.request.contextPath}/upload" enctype="multipart/form-data"  onsubmit="return confirm('Are you sure?') ? true : false;">
      `<input type="date" id="start" name="dateVal"
       value="" pattern="yyyy-MM-dd"
       min="2020-01-01" max="2020-12-31">
            <br/> <br/>
            
         <input type="file" name="file" /><br/><br/>
    <input type="submit" value="Submit" />
      </form:form>
   </body>
   
   
</html>