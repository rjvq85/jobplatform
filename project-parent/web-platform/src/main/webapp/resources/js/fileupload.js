$(document).ready(function() {
	$('#fileName').html('Escolha o ficheiro para upload (formato: PDF, com tamanho máx. 2MB)');
	$('#selectFile').bind('click', function() {
		$('#fileUpload').click();
	});

	$('#fileUpload').bind('change', function() {
		var fullPath = $('#fileUpload').val();
		var fileName = fullPath.split(/(\\|\/)/g).pop(); // fetch the file
															// name
		$('#fileName').html(fileName); // display the file name
	});
});

function swapButtons() {
	$('#selectFile').bind('click', function() {
		$('#fileUpload').click();
	});

	$('#fileUpload').bind('change', function() {
		var fullPath = $('#fileUpload').val();
		var fileName = fullPath.split(/(\\|\/)/g).pop(); // fetch the file
															// name
		$('#fileName').html(fileName); // display the file name
	});
};