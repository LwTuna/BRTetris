package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lobby implements Runnable{

	private final int size;
	private Map<Integer,Board> boards = new HashMap<Integer,Board>();
	
	private boolean running = false;
	private Thread thread;
	private int id;
	private static int nextId = 1;
	public Lobby(int size) {
		this.size = size;
		id = nextId++;
	}
	
	public void join(int sessionId) {
		boards.put(sessionId, new Board());
		if(boards.size() >= size) {
			start();
		}
	}
	public void remove(int sessionId) {
		boards.remove(sessionId);
		
	}
	
	public void start() {
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		for(Board board : boards.values()) {
			board.start();
		}
	}
	public void stop() {
		if(!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(running) {
			List<Board> notGameOver = new ArrayList<Board>();
			for(Board board : boards.values()) {
				if(!board.isGameOver()) {
					notGameOver.add(board);
				}
			}
			if(notGameOver.size() <=1) {
				notGameOver.get(0).win();
				stop();
			}else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
		}
		stop();
	}

	public int getId() {
		return id;
	}
	
}
