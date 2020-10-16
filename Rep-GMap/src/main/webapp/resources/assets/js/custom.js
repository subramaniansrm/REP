

$(document).ready(function(){  

  contentHeight(); // DIV Height Set to 100% 
  
  // $(".imgDiv").height( $(".cardBody_content_2").height()+20 );


//--- Side Navbar Collapse ----//
  $("#sidebar").mCustomScrollbar({
    theme: "minimal"
  });

  $('#sidebarCollapse').on('click', function () {
    $('#sidebar, #content').toggleClass('active');
    $('.collapse.in').toggleClass('in');
    $('a[aria-expanded=true]').attr('aria-expanded', 'false');
  });

//--- Side Navbar Collapse ----//





  //--- For page scroll to top ----//
	$(window).scroll(function () {
		if ($(this).scrollTop() > 50) {
			$('#back-to-top').fadeIn();
		} else {
			$('#back-to-top').fadeOut();
		}
  });
  
	// scroll body to 0px on click
	$('#back-to-top').click(function () {
		//$('#back-to-top').tooltip('hide');
		$('body,html').animate({
			scrollTop: 0
		}, 800);
		return false;
  });
  //--- For page scroll to top ----//

    

});  //--- End of Ready function ---//


 









//--- DIV Height Set to 100% ---//
function contentHeight() {
  var winH = $(window).outerHeight(),
    headerStickyNavHei = $(".navbar_sticky_sec").outerHeight(),
    footerHei = $(".footer_sec").outerHeight(),
    contentHei_1 = winH - headerStickyNavHei - footerHei;
    contentHei_2 = winH;
    $(".container_height, .containerFluid_height").css("min-height", contentHei_1);
    $(".container_height, .containerFluid_height").css("min-height", contentHei_2);
}

$(window).resize(function() {
  contentHeight();
});
//--- DIV Height Set to 100% ---//






  // function resizeDiv () {
  //   $(".cardBody_img_2").height( $(".cardBody_content_2").height() );
  // }

  // resizeDiv();
  
  // $(window).resize(function() { 
  //   resizeDiv(); 
  // });




// $(function() {
//   $('#datetimepicker1').datetimepicker();
// });


$(function(){
    $('#datetimepicker1').datetimepicker({
        "allowInputToggle": true,
        "showClose": true,
        "showClear": true,
        "showTodayButton": true,
        "format": "YYYY-MM-DD",
    });
});




    
    