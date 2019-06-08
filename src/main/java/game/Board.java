package game;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class Board {

	private final int width = 10;
	private final int height = 20;
	
	private int[][] table;
	
	private List<Shape> nextShapes = new ArrayList<Shape>();
	
	public Board() {
		table = new int[height][width];
		for(int y=0;y<height;y++) {
			table[y] = new int[width];
			for(int x=0;x<width;x++) {
				table[y][x] = 0;
			}
		}
		for(int i=0;i<3;i++) {
			nextShapes.add(Shape.shapes.get((int) Math.round(Math.random()*(Shape.shapes.size()-1))));
		}
	}
	
	
	public JSONArray toJSON() {
		JSONArray rows = new JSONArray();
		for(int y=0;y<height;y++) {
			JSONArray row = new JSONArray();
			for(int x=0;x<width;x++) {
				row.put(table[y][x]);
			}
			rows.put(row);
		}
		return rows;
	}
}
