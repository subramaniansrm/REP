<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
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
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/bootstrap-datetimepicker.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/jquery.mCustomScrollbar.min.css" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/resources/assets/css/style.css" />  

	<!-- Font Awesome v5.0.13 JS -->
    <script src="${contextPath}/resources/assets/font/solid.js" ></script>
	<script src="${contextPath}/resources/assets/font/fontawesome.js" ></script>

	 <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
     <style type="text/css">

      #map_canvas {
          height: 1000px;
   		 	margin: 30px;
    		width: 1000px;
       }  
      /*  html, body {
           height: 100%;
           margin: 0;
           padding: 0;
       } */
       .label {
           color: #000;
           background-color: white;
           border: 1px solid #000;
           font-family: "Lucida Grande", "Arial", sans-serif;
           font-size: 12px;
           text-align: center;
           white-space: nowrap;
           padding: 2px;
       }
       .label.green {
           background-color: #58D400;
       }
       .label.red {
           background-color: #D80027;
           color: #fff;
       }
       .label.yellow {
           background-color: #FCCA00;
       }
       .label.turquoise {
           background-color: #00D9D2;
       }
       .label.brown {
           background-color: #BF5300;
           color: #fff;
       }
  
  /* #map-canvas { height: 100% } */
  </style>

       <script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=false"></script>
       <script type="text/javascript">   

function initialize(){         
    var clr;
    var markers = [
                      <c:forEach var="s" items="${fileParams}">                        
                       
               		  <c:forEach var="list" items="${s.listDetails}" varStatus="index">    
               		 
                      ['<c:out value="${list.address}"/>', <c:out value="${list.latitude}"/>,<c:out value="${list.longitude}"/>,'<c:out value="${s.colorCode}"/>'],
                       </c:forEach>   </c:forEach> ];   
           
    /*  var markers = [['Perambur Beach', 35.5457710, 139.4461238,'Red'],
                    ['Porur Beach', 35.5425261, 139.4499664,'Yellow']]   */  
    
    var bp = {lat: 36.204824, lng: 138.252924};
   
    var mapOptions={
        zoom: 6, 
        center: bp,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
    }

    var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
    var icons = {
    	     'Green': {
    	       url: "https://maps.google.com/mapfiles/ms/icons/green.png",
    	       color: '#58D400'
    	     },
    	     'Yellow': {
    	       url:"https://maps.google.com/mapfiles/ms/icons/yellow.png",
    	       color: '#FCCA00'
    	     },
    	     'Red': {
    	       url: "https://maps.google.com/mapfiles/ms/icons/red.png",
    	       color: '#D80027'
    	     },
    	     'Gray': {
    	       url: "https://maps.google.com/mapfiles/ms/icons/grey.png",
    	       color: '#00D9D2'
    	     },
    	     'Blue': {
    	       url: "https://maps.google.com/mapfiles/ms/icons/blue.png",
    	       color: '#BF5300'
    	     },
    	         	    
    	    'DarkGreen': {
      	       url: "https://maps.google.com/mapfiles/ms/icons/green.png",
      	       color: '#58D400'
      	     },
      	     'DarkYellow': {
      	       url: "https://maps.google.com/mapfiles/ms/icons/yellow.png",
      	       color: '#FCCA00'
      	     },
      	     'DarkRed': {
      	       url: "https://maps.google.com/mapfiles/ms/icons/red.png",
      	       color: '#D80027'
      	     },
      	     'Black': {
      	       url: "https://maps.google.com/mapfiles/ms/icons/grey.png",
      	       color: '#00D9D2'
      	     },
      	     'DarkBlue': {
      	       url: "https://maps.google.com/mapfiles/ms/icons/blue.png",
      	       color: '#BF5300'
      	     }    	         	         	        	         	     
    	     
    	   };
    
    var infowindow = new google.maps.InfoWindow(), marker, i;
    for (i = 0; i < markers.length; i++) {      	  
       marker = new google.maps.Marker({              
            position: new google.maps.LatLng(markers[i][1], markers[i][2]),
            map: map,
             icon: icons[ markers[i][3]].url,       
            labelClass: "label " +  markers[i][3],
            labelInBackground: true  
          });
        
        google.maps.event.addListener(marker, 'click', (function(marker, i) {
            return function() {
                infowindow.setContent(markers[i][0]);                
                infowindow.open(map, marker);
            }
        })(marker, i));

    }
}
  google.maps.event.addDomListener(window, 'load', initialize);
   </script>
	<style>
    .error 
    {
        color: #ff0000;
        font-weight: bold;
    }
    </style>
	
</head>



<body onload="initialize()">


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
				<a href="#">Upload</a>
			</li>
			<li>
				<a href="#">GeoMap</a>
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
			<div class="row">
				<div class="col-lg-6 col-md-6 col-sm-8 col-8 offset-lg-3 offset-md-3 offset-sm-2 offset-2">
					
					  <form:form method = "POST" action = "${pageContext.request.contextPath}/address/readAddress">
					<div class="row">
						<div class="col-lg-7 col-md-7 col-sm-6 col-12">
							<div class="form-group datePicker_secCCC">
								<div class="input-group date" id="datetimepicker1">
								 
								   <input type="text" name = "dateVal" placeholder="" class="form-control" required/>
								   <form:errors path="dateVal" cssClass="error" />  
								 	<div class="input-group-addon input-group-append">
										<div class="input-group-text">
											<i  class="glyphicon glyphicon-calendar fa fa-calendar"></i>
										</div>
									</div>  
									<%--  Date: <form:input path = "dateVal" /> --%>
									 
									 
								</div>
							</div>
						</div>
						<div class="col-lg-5 col-md-5 col-sm-6 col-12">
							<button type="submit" class="btn customBtn_2 align-middle">
								<span>地図を表示する</span>
							</button>
						</div>
					</div>
					
					  </form:form>
					  
				</div>
			</div>

			<div class="row">
			   <div class="col-md-12"  >    
					GeoMap Section
					<%--  
					 <div id="map_canvas"  ></div>
     
                     <p class="info">${ message }</p>  --%>   
     				
			  </div>   
			</div>
				<div class="row">
			      
					 
					 
					 <div id="map_canvas"  ></div>
     
                     <p class="info">${ message }</p>    
     				
			    
			</div>
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
<script src="${contextPath}/resources/assets/js/moment-with-locales.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/assets/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/assets/js/jquery.mCustomScrollbar.concat.min.js" type="text/javascript"></script>
<script src="${contextPath}/resources/assets/js/custom.js" type="text/javascript"></script>

<!-- <script type="text/javascript">
	$(document).ready(function(){
	});
</script> -->

</body>
</html>

<%-- <!DOCTYPE HTML>

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
      <h2>Real Estate Data</h2>
      <form:form method = "POST" action = "${pageContext.request.contextPath}/address/readAddress">
         <table>
           
            <tr>
            	<label for="start">Date:</label>

		<input type="date" id="start" name="dateVal"
       value="" pattern="yyyy-MM-dd"
       min="2020-01-01" max="2020-12-31">
            </tr> 
             <tr>
               <td><form:label path = "dateVal">Custom Date</form:label></td>
               <td><form:input path = "dateVal" /></td>
            </tr> 
            <tr></tr>   
            <tr>
               <td colspan = "2">
                  <input type = "submit" value = "Submit"/>
               </td>
            </tr>
         </table>  
      </form:form>
   </body>
</html> --%>