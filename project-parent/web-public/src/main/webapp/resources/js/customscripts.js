function fadeoutMsg() {
	setTimeout(function() {
		$('#editglobalmsgs').fadeOut();
	}, 2000);
};

function fadeoutMsgDlg() {
	setTimeout(function() {
		$('#editglobalmsgsdlg').fadeOut();
	}, 2000);
};

// Check terms and conditions
$(document).ready(
	    function() {
	        $('#accepttermsbox').change(function() {
	            $('#accepttermsbox').prop('checked') ? $('#registerbtn').prop('disabled', false) : $('#registerbtn').prop('disabled', true);
	        });
	        $('#terms').scroll(function() {
	            if ($(this).scrollTop() + $(this).innerHeight() + 2 >= $(this)[0].scrollHeight) {
	                $('#accepttermsbox').prop('disabled', false);
	            }
	        });
	    });

//