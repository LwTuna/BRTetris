



//Server

function sendRequest(request,callback){
	var http = new XMLHttpRequest();
	http.open('POST', 'daten?' + encodeURI(JSON.stringify(request)));
	
	http.onreadystatechange = function () {  
		if (this.readyState == 4 && this.status == 200) {
			
			var response = JSON.parse(this.responseText);
			callback(response);
		}
		
	};
	http.timeout = 5000;
	http.ontimeout = function(){
		callback('{"tag":"timeout"}');
	}
	http.send();
}

function printAnsw(msg){
	console.log(msg);
}



$( document ).ready(function() {
	$(".container.content").load("login.html");	
});

