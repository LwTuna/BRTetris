$(function()
      {	
        document.getElementById("loginerr").style.display = 'none';
    });

function login(){
    var usr = document.getElementById("usr").value;
    var pwd = document.getElementById("pwd").value;
    var req = {};
    req.tag = "login";
    req.email = usr;
    req.password = pwd;
    sendRequest(req, function(obj){
    	if (obj.succes) {
            $(".container.content").load("game.html");
            
        } else {
            alert('Login Error');
        }
    });
   
}
function openRegisterMenu(){
	$(".container.content").load("register.html");	
}