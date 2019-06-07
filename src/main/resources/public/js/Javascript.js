function sendRequest(request,callback){
		var http = new XMLHttpRequest();
		http.open('POST', 'daten?' + encodeURI(request));
		
		http.onreadystatechange = function () {  
			if (this.readyState == 4 && this.status == 200) {
				var http = new XMLHttpRequest();
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
	
	function printAnsw(response){
		window.alert(response.tag);		
	}
	
	sendRequest('{"tag":"test"}',printAnsw);