var baseurl = "/rest/api/candidate/";
var currentUser;
var errorMsg;
var id = getId()["candidato"];

$("#pagebody").hide();
$("#errorbody").hide();
findCandidate();
function findCandidate() {
	$.ajax({
		type: "GET",
		url: baseurl + id,
		dataType: "json",
		cache: false,
		success: function(data) {
			$("#pagebody").show();
			currentUser = data;
			renderCandidate(currentUser);
		},
		error: function (data) {
			console.log(data);
			$("#errorbody").show();
			errorMsg = data;
			renderError(errorMsg);
		}
	});
}

function renderCandidate(currentUser) {
	var names = "";
	names += "<h1>" + currentUser.firstName + " " + currentUser.lastName + "</h1>";
	$("#usernames").html(names);
	var address = "";
	var address = "<p>" + currentUser.address + "</p>";
	$("#address").html(address);
	var cellphone = "<p>" + currentUser.mobilePhone + "</p>";
	$("#cellphone").html(cellphone);
	var cvs = "<p>";
	for (var i = 0; i < currentUser.course.length; i++) {
		var c = currentUser.course[i];
		cvs += c + "<br/>";
		}
	cvs += "</p>";
	$("#cv").html(cvs);
	$("a").prop("href",currentUser.cv);
	var filename = "CV_" + currentUser.firstName + "_" +  currentUser.lastName + ".pdf";
	$("a").prop("download",filename);
}

function renderError(errorMsg) {
	var htmlerror = "<p>" + errorMsg.responseText + "</p>";
	$("#onerror").html(htmlerror);
}




















function getId() {
	var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}