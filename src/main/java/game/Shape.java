package game;

public class Shape {

	private ShapePrefab prefab;
	private int rotation;
	private int x,y;
	
	public Shape(ShapePrefab prefab) {
		this.prefab = prefab;
		this.x = prefab.getStartX();
		this.y = prefab.getStartY();
		rotation = prefab.getStartRotation();
	}
	
	public int[][] move(String direction,int[][] board){
		board = removeFromBoard(board);
		switch(direction) {
		case "left": x--;break;
		case "right" : x++;break;
		case "down" : y++;break;
		case "rotate" : rotation = increaseRotation();break;
		case "drop" : y++;
		}
		return createShapeInBoard(board);
		
		
	}
	
	private int increaseRotation() {
		int potRot = rotation;
		potRot++;
		if(potRot >=4) {
			potRot = 0;
		}
		return potRot;
	}
	
	public int[][] removeFromBoard(int[][] board) {
		
		for(int x=0;x<4;x++) {
			for(int y=0;y<4;y++) {
				if(prefab.getTables()[rotation][x][y] == 1) {
					board[x+this.x][y+this.y] = 0;
				}
			}
		}
		return board;
		
	}

	public boolean canMove(String dir,int[][] board) {
		board = removeFromBoard(board);
		int potX = x;
		int potY = y;
		
		int potRot = rotation;
		switch(dir) {
		case "left": potX--; break;
		case "right" : potX++;break;
		case "down" : potY++;break;
		case "rotate" : potRot = increaseRotation() ;break;
		case "drop" : potY++;break; 
		}
		
		return !isOutOfBounds(potX, potY, potRot, board)&&!intersects(potX, potY, potRot, board) ;
	}
	
	private boolean intersects(int potX,int potY,int potRotation,int[][] board) {
		for(int x=0;x<4;x++) {
			for(int y=0;y<4;y++) {
				if(prefab.getTables()[potRotation][x][y] !=0) {
					if(board[potX+x][potY+y] !=0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean isOutOfBounds(int potX,int potY,int potRotation,int[][] board) {
		for(int x=0;x<4;x++) {
			for(int y=0;y<4;y++) {
				if(prefab.getTables()[potRotation][x][y] !=0) {
					if(potX+x<0 || potX+x>=10 || potY+y<0 || potY+y>=20) {
						return true;
					}
						
				}
			}
		}
		return false;
	}
	
	public int[][] createShapeInBoard(int[][] board){
		for(int x=0;x<4;x++) {
			for(int y=0;y<4;y++) {
				if(prefab.getTables()[rotation][x][y] == 1) {
					board[x+this.x][y+this.y] = this.prefab.getColorId();
				}
			}
		}
		
		return board;
	}

	public boolean canBeCreated(int[][] board) {
		if(intersects(x, y, rotation, board)) {
			return false;
		}
		return true;
	}
	
}
