$(function()
      {	
        document.getElementById("registererr").style.display = 'none';
    });
function register(){
    var req = {"tag":"register","email":document.getElementById("email").value,"user":document.getElementById("usr").value,"password":document.getElementById("pwd").value};
    sendRequest(req, function(obj){
    	if (obj.succes) {
    		alert('Succesfull registered!')
            $(".container.content").load("login.html");	
        } else {
            alert('Register Error');
        }
    });
   
}

