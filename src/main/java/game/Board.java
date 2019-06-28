package game;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class Board implements Runnable{

	private final int width = 10;
	private final int height = 20;
	
	
	private Shape currentShape;
	
	
	private int[][] table;
	
	private Thread thread;
	private boolean running = false;
	
	
	private List<ShapePrefab> nextShapes = new ArrayList<ShapePrefab>();
	
	public Board() {
		table = new int[width][height];
		for(int x=0;x<width;x++) {
			table[x] = new int[height];
			for(int y=0;y<height;y++) {
				table[x][y] = 0;
			}
		}
		currentShape = new Shape(ShapePrefab.shapes.get(0));
		table = currentShape.createShapeInBoard(table);
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
		if(currentShape.canMove(direction, table)) {
			table = currentShape.move(direction, table);
			
			return true;
		}
		return false;
	}
	
	public synchronized void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	public synchronized void stop() {
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		while(running) {
			if(currentShape.canMove("down", table)) {
				table = currentShape.move("down", table);
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				//TODO create new shape
			}
		}
		
	}
	
}
