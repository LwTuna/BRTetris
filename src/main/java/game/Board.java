package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;

public class Board implements Runnable{

	public static final int width = 10;
	public static final int height = 20;
	
	
	private Shape currentShape;
	private boolean gameOver = false;
	
	private int[][] table;
	
	private Thread thread;
	private boolean running = false;
	
	private boolean won = false;
	
	private Lobby lobby;
	private List<ShapePrefab> nextShapes = new ArrayList<ShapePrefab>();
	
	public Board(Lobby lobby) {
		this.lobby = lobby;
		table = new int[width][height];
		for(int x=0;x<width;x++) {
			table[x] = new int[height];
			for(int y=0;y<height;y++) {
				table[x][y] = 0;
			}
		}
		
		nextShapes.add(ShapePrefab.shapes.get((int) (Math.random()*ShapePrefab.shapes.size())));
		
		
		
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
	
	public void gameOver() {
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
		createNewShape();
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
		try {
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
				int rowsToAppear=0;
				if(!rowsCompleted.isEmpty()) {
					
					outer:for(Integer y :rowsCompleted) {
						for(int x=0;x<width;x++) {
							System.out.println(x+" "+y+" "+table[x][y]);
							if(table[x][y] == 8) {
								continue outer;
							}
							
						}
						rowsToAppear++;
					}

				clearRows(rowsCompleted);
					lobby.addRows(rowsToAppear, this);
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
	public void clearRow(int row) {
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
		won = true;
	}

	public boolean isRunning() {
		return running;
	}

	public boolean isWon() {
		return won;
	}
	
	public void addLowerRow(int amt) {
		try {
			
			int[][] temp = new int[width][height];
			temp = currentShape.removeFromBoard(table);
			for(int y=0+amt;y<height;y++) {
				for(int x=0;x<width;x++) {
					temp[x][y-amt] = table[x][y];
				}
			}
			temp = currentShape.createShapeInBoard(temp);
			for(int y = height-amt;y<height;y++) {
				Random r = new Random();
				int spaceFree = r.nextInt(width);
				for(int x=0;x<width;x++) {
					if(spaceFree == x) continue;
					temp[x][y] = 8;
				}
			}
			table = temp;
		}catch(Exception e) {
			e.printStackTrace();
			gameOver();
		}
	}
	public int[][] getTable() {
		return table;
	}
	public Shape getCurrentShape() {
		return currentShape;
	}
}
