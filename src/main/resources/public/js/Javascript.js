//Game
const rows = 20,columns=10;


function init(){
	if(shapes.length <=0){
		var request = {};
		request.tag = 'getShapes';
		sendRequest(request, loadShapes);
	}
	
}
//Renderer
var canvas = document.getElementById('canvas');
var ctx = canvas.getContext('2d');


function render(response){
	var width = canvas.width;
	var height = canvas.height;
	var cellHeight = height/rows,cellWidth = width/columns;
	ctx.clearRect(0,0,width,height);
	
	var arr = response.rows;
	
	 for ( var x = 0; x < columns; x++ ) {
	        for ( var y = 0; y < rows; y++ ) {
	        	if(arr[y][x] ==1){
	        		ctx.fillRect(x*cellWidth,y*cellHeight,cellWidth,cellHeight);
	        	}else{
	        	}
	        }
	 }
}

var lastRequest = 0;
//Input
var sessionId = 1;

setInterval(() => {
	var request = {};
	request.tag = "getCurrentBoard";
	request.id = sessionId;
	sendRequest(request, render);
}, 50);

document.body.onkeydown = function( e ) {
    var keys = {
        37: 'left',	//arrow lefts
        39: 'right', //Arrow Right
        40: 'down',   //Arrow Down
        38: 'rotate', //Arrow Up
        32: 'drop'   //Spacebar
    };
    
    if(keys[e.keyCode] != 'undefined'){
    	var obj = {};
    	obj.tag = "input";
    	obj.key = keys[e.keyCode];
    	sendRequest(obj,printAnsw);
    }
}

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

