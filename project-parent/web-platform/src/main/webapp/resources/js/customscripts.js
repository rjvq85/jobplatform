function fadeoutfunction() {
	setTimeout(function() {
		$('[id$=messages]').fadeOut();
		$('[class$=glyphicon-exclamation-sign]').fadeOut('slow');
	}, 3000);
};

$('.selectpicker').selectpicker({
	style : 'btn-info',
	size : 4
});

// Primefaces Locale: PT

PrimeFaces.locales['pt'] = {
	closeText : 'Fechar',
	prevText : 'Anterior',
	nextText : 'Próximo',
	currentText : 'Começo',
	monthNames : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
			'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro' ],
	monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago',
			'Set', 'Out', 'Nov', 'Dez' ],
	dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta',
			'Sábado' ],
	dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb' ],
	dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
	weekHeader : 'Semana',
	firstDay : 0,
	isRTL : false,
	showMonthAfterYear : false,
	yearSuffix : '',
	timeOnlyTitle : 'Só Horas',
	timeText : 'Tempo',
	hourText : 'Hora',
	minuteText : 'Minuto',
	secondText : 'Segundo',
	ampm : false,
	month : 'Mês',
	week : 'Semana',
	day : 'Dia',
	allDayText : 'Todo o Dia'
};

function fadeoutMsg() {
	setTimeout(function() {
		$('#editglobalmsgs').fadeOut();
	}, 2000);
};

function fadeoutMsgDeleteScript() {
	setTimeout(function() {
		$('.delscriptmsgs').fadeOut();
	}, 2000);
};

function redirect() {
	setTimeout(function() {
		window.location = '${pageContext.request.contextPath}';
	}, 5000);
};

function intredirect() {
	var answer = confirm('Tem a certeza?\nA entrevista será cancelada:\nTodos os dados serão perdidos!');
	if (answer) {
		return true;
	} else
		return false;
};

$(document).ready(function() {
	if ($('#flashglobalmsgs').length != 0) {
		setTimeout(function() {
			$('#flashglobalmsgs').fadeOut();
		}, 3000);
	}
});
