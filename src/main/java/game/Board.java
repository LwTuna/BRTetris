package game;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class Board {

	private final int width = 10;
	private final int height = 20;
	
	private int currentX = 5,currentY = 0;
	private ShapePrefab currentShape;
	
	
	private int[][] table;
	
	
	private List<ShapePrefab> nextShapes = new ArrayList<ShapePrefab>();
	
	public Board() {
		table = new int[width][height];
		for(int x=0;x<width;x++) {
			table[x] = new int[height];
			for(int y=0;y<height;y++) {
				table[x][y] = 0;
			}
		}
		table[currentX][currentY] = 1;
		currentShape = ShapePrefab.shapes.get(0);
	}
	
	
	public JSONArray toJSON() {
		JSONArray columns = new JSONArray();
		for(int x=0;x<width;x++) {
			JSONArray column = new JSONArray();
			for(int y=0;y<height;y++) {
				column.put(table[x][y]);
			}
			columns.put(column);
		}
		return columns;
	}
	
	public boolean move(String direction) {
		table[currentX][currentY] = 0;
		switch (direction){
		case "left": currentX -=1;break;
		case "right": currentX +=1;break;
		case "down": currentY +=1;break;
		case "rotate": ;break;
		case "drop": currentY = 19;;break;
		}
		table[currentX][currentY] = 1;
		return true;
	}
	
}
