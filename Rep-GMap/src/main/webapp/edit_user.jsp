<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
      <meta charset="utf-8">
      <title>Edit User</title>

      <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
      <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
      
      <style>
h2 {text-align: right;}
</style>
  </head>

<body>
<div align="center">
        <h1>Edit Product</h1>
        <br />
        <form action="#" th:action="@{/save}" th:object="${user}"
            method="post">
 
            <table border="0" cellpadding="10">
                <tr>             
                    <td>Product ID:</td>
                    <td>
                        <input type="text" name="{id}" readonly="readonly" />
                    </td>
                </tr>        
                <tr>             
                    <td>Product Name:</td>
                    <td>
                        <input type="text" value="{username}" />
                    </td>
                </tr>
                                           
                <tr>
                    <td colspan="2"><button type="submit">Save</button> </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>