
<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1" --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
 <head>
 <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
 <style type="text/css">

  
    #map_canvas {
           height: 100%;
           width: 100%;
       }
       html, body {
           height: 100%;
           margin: 0;
           padding: 0;
       }
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
  
  #map-canvas { height: 100% }
  </style>

       <script type="text/javascript" src="https://maps.google.com/maps/api/js?sensor=false"></script>
       <script type="text/javascript">   

function initialize(){
   // var latlng = new google.maps.LatLng(markers[0][0],markers[0][1]);
   
    /*    var markers = [
           <c:forEach var="s" items="${locationList}">            
 ['<c:out value="${s.address}"/>',<c:out value="${s.latitude}"/>,<c:out value="${s.longitude}"/>,'<c:out value="${s.color}"/>'],
        </c:forEach> ];   */    
        console.log("Test");
      var clr;
      var markers = [
                      <c:forEach var="s" items="${fileParams}">                        
                       // clr ="${s.colorCode}" ;
               		  <c:forEach var="list" items="${s.listDetails}" varStatus="index">    
               		 // clr ="${list.address}" ;
                      ['<c:out value="${list.address}"/>', <c:out value="${list.latitude}"/>,<c:out value="${list.longitude}"/>,'<c:out value="${s.colorCode}"/>'],
                       </c:forEach>   </c:forEach> ];  
           
  /*   var markers = [['Perambur Beach', 35.5457710, 139.4461238,'Red'],
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
    	console.log("Hello world!"),
     /*    marker = new google.maps.Marker({        	        	
            position: new google.maps.LatLng(markers[i][1], markers[i][2]),
            map: map
        }); */

       marker = new google.maps.Marker({  
            //position: {lat: markers[i][1], lng: markers[i][2]},
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
   </head>
   <body onload="initialize()">
    <div id="map_canvas"></div>
     
    <p class="info">${ message }</p>
    </body>
   </html>