$(function()
      {	
        document.getElementById("loginerr").style.display = 'none';
    });

var sessionId;

function login(){
    var req = {"tag":"login","email":document.getElementById("usr").value,"password":document.getElementById("pwd").value};
    sendRequest(req, function(obj){
    	if (obj.succes) {
            $(".container.content").load("game.html");
            sessionId = obj.sessionId;
        } else {
            alert('Login Error');
        }
    });
   
} 
function openRegisterMenu(){
	$(".container.content").load("register.html");	
}