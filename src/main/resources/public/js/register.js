$(function()
      {	
        document.getElementById("registererr").style.display = 'none';
    });
function register(){
    sendRequest({"tag":"register","email":document.getElementById("email").value,"user":document.getElementById("usr").value,"password":document.getElementById("pwd").value}, function(obj){
    	if (obj.succes) {
    		alert('Succesfull registered!')
            $(".container.content").load("login.html");	
        } else {
            alert('Register Error');
        }
    });
   
}

