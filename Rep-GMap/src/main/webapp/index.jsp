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
	<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/jquery.mCustomScrollbar.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/style.css" />

	<!-- Font Awesome v5.0.13 JS -->
    <script src="${contextPath}/resources/assets/font/solid.js" ></script>
	<script src="${contextPath}/resources/assets/font/fontawesome.js" ></script>
	
</head>



<body>


<!-- ========================= SECTION CONTENT ========================= -->


<div class="wrapper">
	<!-- Sidebar  -->
	<nav id="sidebar">
		<div class="sidebar-header">
			<a class="sdf" href="#">
				<img src="${contextPath}/resources/assets/images/logo.png" class="img-fluid">
			</a>
		</div>

		<ul class="list-unstyled components">
			<li class="active">
				<a href="#">User</a>
			</li>
			<li>
				<a href="/uploadAdd">Upload</a>
			</li>
			<li>
				<a href="/readAddress">GeoMap</a>
			</li>
			<li>
				<a href="${contextPath}/logout">Logout
				 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</a>
			</li>
			
		</ul>
	</nav>

	<!-- Page Content  -->
	<div id="content">

		<nav class="navbar navbar-expand-lg navbar-light bg-light sticky-top navbar_sticky_sec">
			<div class="container-fluid px-0">
				<button type="button" id="sidebarCollapse" class="btn btn-info">
					<i class="fas fa-align-left"></i>
					<!-- <span>Toggle Sidebar</span> -->
				</button>
				<!-- <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
					<i class="fas fa-align-justify"></i>
				</button> -->
				<button class="navbar-toggler navbar-toggler-right collapsed" type="button" data-toggle="collapse" data-target="#navbarSupportedContent">
					<span> </span>
					<span> </span>
					<span> </span>
				</button>

				<!-- <ul class="nav">
					<li class="nav-item">
					  <a class="nav-link active" href="#">Active</a>
					</li>
					<li class="nav-item">
					  <a class="nav-link" href="#">Link</a>
					</li>
				</ul> -->
				<div class="collapse navbar-collapse" id="navbarSupportedContent">
					<ul class="nav navbar-nav ml-auto">
						<li class="nav-item active">
							<a class="nav-link" href="#">Page</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="#">Page</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>


		<div class="container-fluid py-3 containerFluid_height">
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
		</div>

		
		<footer class="footer mt-auto footer_sec">
			<div class="container-fluid p-0">
				  <div class="text-center py-2 footer_copyright">
					<p>&copy Copyright 2020.</p>
				</div>	
			</div>
		</footer>

	</div>
</div>


<!-- For page to top start -->
<a id="back-to-top" href="#" class="btn back-to-top" role="button"><span class="fa fa-arrow-up"></span></a>
<!-- For page to top end -->




<!-- jQuery -->
<!-- <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script> -->
<script src="${contextPath}/resources/assets/js/jquery.min.js" type="text/javascript"></script>


<!-- Bootstrap files (jQuery first, then Popper.js, then Bootstrap JS) -->
<script src="${contextPath}/resources/assets/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/assets/js/jquery.mCustomScrollbar.concat.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/assets/js/custom.js" type="text/javascript"></script>

<!-- <script type="text/javascript">
	$(document).ready(function(){
	});
</script> -->

</body>
</html>