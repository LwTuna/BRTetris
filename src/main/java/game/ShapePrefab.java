package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class ShapePrefab {

	public static List<ShapePrefab> shapes = Arrays.asList(new ShapePrefab[] {new ShapePrefab("res/IShape.txt","I",5,0,1,0),
			new ShapePrefab("res/OShape.txt","O",5,0,2,0),new ShapePrefab("res/LShape.txt","L",5,0,3,0),new ShapePrefab("res/JShape.txt","J",5,0,4,0),
			new ShapePrefab("res/TShape.txt","T",5,0,5,0),new ShapePrefab("res/ZShape.txt","Z",5,0,6,0),new ShapePrefab("res/SShape.txt","S",5,0,7,0)});;
	
	private String name;
	private int startX,startY;
	private int colorId;
	private int startRotation;
	
	private int[][][] tables=new int[4][4][4];
	
	
	
	public ShapePrefab(String url,String name,int startX,int startY,int colorId,int startRotation) {
		this.name = name;
		this.startX = startX-2;
		this.startY = startY;
		this.colorId = colorId;
		this.startRotation = startRotation;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(url)));
			
			String line;
			int currentLine = 0;
			while((line = reader.readLine())!=null) {
				String[] split = line.split("\\s+");
				int currentShape = currentLine/4;
				for(int i = 0;i<4;i++) {
					
					this.tables[currentShape][i][currentLine-(currentShape*4)] = Integer.parseInt(split[i]);
					
				}
				currentLine++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public int getStartRotation() {
		return startRotation;
	}

	public String getName() {
		return name;
	}

	public int[][][] getTables() {
		return tables;
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getColorId() {
		return colorId;
	}
	
	
}
