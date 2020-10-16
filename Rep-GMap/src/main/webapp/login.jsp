<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- IE Edge Meta Tag -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="keywords" content="" />
	<meta name="description" content="" /> 
	<title>REGM</title>

	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/bootstrap.css" />
	<!-- <link rel="stylesheet" type="text/css" href="assets/font/font-awesome/font-awesome.css" /> -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/style.css" />
</head>



<body>



<section class="section-content">
	<div class="container">
 <form method="POST" action="${contextPath}/login" class="form-signin">
		<div class="d-table w-100 container_height">
			<div class="d-table-cell align-middle">
				<div class="loginForm_sec">
					<div class="row">
						<div class="col-md-12 text-center">

							<h3 class="text-center font-weight-bold">ログイン</h3>
							<p class="text-center"><small>会員登録は こちら から行えます。</small></p>

							
							<p class="text-center">メールアドレスでログイン</p>	
							<div class="form-group">
								<input type="text" name="username" class="form-control" placeholder="メールアドレス" required autofocus>
							</div>				  
							<div class="form-group">
								<input type="password" name="password" class="form-control" placeholder="パスワード" required>
							</div>
							 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>				  
							<div class="text-center">
								<button class="btn customBtn_2 align-middle" type="submit">
									<span class="fa fa-sign-in fa-lg mr-1"></span> 
									<span>ログイン</span>
								</button>
							</div>				  
							<p class="text-center">
								<a href="${contextPath}/forgetPassword" class="text-dark anchHover"><small>パスワードをお忘れの方はこちら</small></a>
							</p>
							
						</div>
					</div>
				</div>
			</div>
		</div>
</form>
	</div>
</section>











<!-- For page to top start -->
<a id="back-to-top" href="#" class="btn back-to-top" role="button"><span class="fa fa-arrow-up"></span></a>
<!-- For page to top end -->




<!-- jQuery -->
<!-- <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script> -->
<script src="${contextPath}/resources/assets/js/jquery.min.js" type="text/javascript"></script>


<!-- Bootstrap files (jQuery first, then Popper.js, then Bootstrap JS) -->
<script src="${contextPath}/resources/assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/assets/js/custom.js" type="text/javascript"></script>

<!-- <script type="text/javascript">
	$(document).ready(function(){
	});
</script> -->

</body>
</html>