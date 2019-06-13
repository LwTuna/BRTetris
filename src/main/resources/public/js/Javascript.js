//Game
const rows = 20,columns=10;

var loggedIn = false;

function init(){
	if(shapes.length <=0){
		var request = {};
		request.tag = 'getShapes';
		sendRequest(request, loadShapes);
	}
	
}
//Renderer



function render(response){
	var canvas = document.getElementById('canvas');
	var ctx = canvas.getContext('2d');
	var width = canvas.width;
	var height = canvas.height;
	var cellHeight = height/rows,cellWidth = width/columns;
	ctx.clearRect(0,0,width,height);
	
	var arr = response.rows;
	
	 for ( var x = 0; x < columns; x++ ) {
	        for ( var y = 0; y < rows; y++ ) {
	        	if(arr[x][y] ==1){
	        		ctx.fillRect(x*cellWidth,y*cellHeight,cellWidth,cellHeight);
	        	}else{
	        	}
	        }
	 }
}

//Input
var sessionId = 1;



document.body.onkeydown = function( e ) {
	if(!loggedIn) return;
    var keys = {
        37: 'left',	//arrow left
        39: 'right', //Arrow Right
        40: 'down',   //Arrow Down
        38: 'rotate', //Arrow Up
        32: 'drop'   //Spacebar
    };
    
    if(keys[e.keyCode] != 'undefined' && (e.keyCode == 37 ||e.keyCode == 39 ||e.keyCode == 40 ||e.keyCode == 38 ||e.keyCode == 32)){
    	var obj = {};
    	obj.tag = "input";
    	obj.id = sessionId;
    	obj.key = keys[e.keyCode];
    	sendRequest(obj,function(obj){
    		var request = {};
    		request.tag = "getCurrentBoard";
    		request.id = sessionId;
    		sendRequest(request, render);
    	});
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

function login(){
    var usr = "test";//document.getElementById("usr").value;
    var pwd = "test";//document.getElementById("pwd").value;
    var req = {};
    req.tag = "login";
    req.email = "usr";
    req.password = "pwd";
    sendRequest(req, function(obj){
    	if (obj.succes) {
            loggedIn=true;
            $(".container.content").load("game.html");	
        } else {
            alert('Login Error');
        }
    });
   
}

$( document ).ready(function() {
	$(".container.content").load("login.html");	
});

setInterval(() => {
	if(!loggedIn) return;
	var request = {};
	request.tag = "getCurrentBoard";
	request.id = sessionId;
	sendRequest(request, render);
}, 50);