function fadeoutfunction() {
	setTimeout(function() {
		$('[id$=messages]').fadeOut();
		$('[class$=glyphicon-exclamation-sign]').fadeOut('slow');
	}, 3000);
}