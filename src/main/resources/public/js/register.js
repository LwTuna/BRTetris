$(function()
      {	
        document.getElementById("registererr").style.display = 'none';
    });
function register(){
    var usr = document.getElementById("usr").value;
    var pwd = document.getElementById("pwd").value;
    var email = document.getElementById("email").value;
    var req = {};
    req.tag = "register";
    req.email = email;
    req.user = usr;
    req.password = pwd;
    sendRequest(req, function(obj){
    	if (obj.succes) {
            $(".container.content").load("login.html");	
        } else {
            alert('Login Error');
        }
    });
   
}

