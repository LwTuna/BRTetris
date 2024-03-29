const rows = 20,columns=10;



function getRandomInt(max) {
	  return Math.floor(Math.random() * Math.floor(max));
}

var imgs = [];

$( document ).ready(function() {
	for(var i = 1;i<=8;i++){
		imgs[i] = new Image();
	}
	imgs[1].src = "res/blueTile.png";
	imgs[2].src = "res/cyanTile.png";
	imgs[3].src = "res/greenTile.png";
	imgs[4].src = "res/orangeTile.png";
	imgs[5].src = "res/purpleTile.png";
	imgs[6].src = "res/redTile.png";
	imgs[7].src = "res/yellowTile.png";
	imgs[8].src = "res/grayTile.png";
	
});

setInterval(() => {
	sendRequest({"tag" : "getCurrentBoard","id":sessionId}, render);
}, 50);

function render(response){
	var canvas = document.getElementById('canvas');
	var ctx = canvas.getContext('2d');
	var width = canvas.width;
	var height = canvas.height;
	var cellHeight = height/rows,cellWidth = width/columns;
	ctx.clearRect(0,0,width,height);
	var arr = response.rows;
	document.getElementById('playersAlive').innerHTML = "Spieler : "+response.playersAlive;
	 for ( var x = 0; x < columns; x++ ) {
	        for ( var y = 0; y < rows; y++ ) {
	        	if(arr[x][y] !=0)	ctx.drawImage(imgs[arr[x][y]],x*cellWidth,y*cellHeight,cellWidth,cellHeight);
	        }
	 }
	 if(response.gameOver){
			ctx.font="30px Verdana";
			ctx.fillText("Game Over!",0+width/10,height/2);
	 }
	 if(response.isWon){
			ctx.font="30px Verdana";
			ctx.fillText("You win!",0+width/10,height/2);
	 }
}

document.body.onkeydown = function( e ) {
    var keys = {37: 'left',39: 'right', 40: 'down', 38: 'rotate', 32: 'drop'};
    
    if(keys[e.keyCode] != 'undefined' && (e.keyCode == 37 ||e.keyCode == 39 ||e.keyCode == 40 ||e.keyCode == 38 ||e.keyCode == 32)){
    	var obj = {"tag":"input","id":sessionId,"key":keys[e.keyCode]};
    	sendRequest(obj,function(obj){
    		sendRequest({"tag":"getCurrentBoard","id":sessionId}, render);
    	});
    }
}
