package game;

public class Shape {

	private static ShapePrefab prefab;
	private int rotation;
	private int x,y;
	
	public Shape(ShapePrefab prefab) {
		this.prefab = prefab;
		this.x = prefab.getStartX();
		this.y = prefab.getStartY();
	}
	
	public int[][] move(String direction,int[][] board){
		switch(direction) {
		case "left": break;
		case "right" : break;
		case "down" : break;
		case "rotate" : break;
		case "drop" : break;
		}
		
		
		
	}
	
	public boolean canMove(String dir,int[][] board) {
		
	}
	
	public int[][] getBoardWithThisShape(int[][] board){
		
		
		return board;
	}
}
