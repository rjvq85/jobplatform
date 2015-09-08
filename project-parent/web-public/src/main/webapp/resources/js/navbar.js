$(window).on('scroll',function() {
	var wHeight = $(window).height;
	var sTop = $(window).scrollTop();
	if (sTop > 0) {
		$('.pageheader').addClass('navHide');
	} else {
		$('.pageheader').removeClass('navHide');
	}
	
});