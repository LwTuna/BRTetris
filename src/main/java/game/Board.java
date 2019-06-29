package game;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class Board implements Runnable{

	private final int width = 10;
	private final int height = 20;
	
	
	private Shape currentShape;
	private boolean gameOver = false;
	
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
		for(int i=0;i<3;i++) {
			nextShapes.add(ShapePrefab.shapes.get((int) (Math.random()*ShapePrefab.shapes.size())));
		}
		createNewShape();
		
	}
	
	public void createNewShape() {
		
		currentShape = new Shape(nextShapes.get(0));
		nextShapes.remove(0);
		nextShapes.add(ShapePrefab.shapes.get((int) (Math.random()*ShapePrefab.shapes.size())));
		if(currentShape.canBeCreated(table)) {
			table = currentShape.createShapeInBoard(table);
		}else {
			gameOver();
		}
		
	}
	
	private void gameOver() {
		gameOver = true;
		stop();
		
	}
	
	public boolean isGameOver() {
		return gameOver;
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
		if(!running)
			return false;
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
			if(move("down")) {
				try {
					Thread.sleep(700);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				table=currentShape.createShapeInBoard(table);
				
				List<Integer> rowsCompleted = checkForRows();
				if(!rowsCompleted.isEmpty()) {
					clearRows(rowsCompleted);
					//TODO add rows add enemys
				}
				createNewShape();
			}
		}
		
	}

	private void clearRows(List<Integer> rowsCompleted) {
		for(Integer i:rowsCompleted) {
			clearRow(i);
			for(int y=i-1;y>=0;y--) {
				moveRowDown(y);
			}
		}
	}
	private void moveRowDown(int row) {
		for(int x=0;x<width;x++) {
			table[x][row+1] = table[x][row];
		}
		clearRow(row);
	}
	private void clearRow(int row) {
		for(int x=0;x<width;x++) {
			table[x][row] = 0;
		}
	}

	private List<Integer> checkForRows() {
		List<Integer> rows = new ArrayList<Integer>();
		outer:for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				if(table[x][y]==0)
					continue outer;
			}
			rows.add(y);
		}
		return rows;
	}

	public void win() {
		// TODO Auto-generated method stub
		
	}
	
}
