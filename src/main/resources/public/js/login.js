$(function()
      {	
        document.getElementById("loginerr").style.display = 'none';
    });

var sessionId;

function login(){
    sendRequest({"tag":"login","email":document.getElementById("usr").value,"password":document.getElementById("pwd").value}, function(obj){
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